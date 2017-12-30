
package com.techco.igotrip.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Comment;
import com.techco.igotrip.data.network.model.response.CommentListResponse;
import com.techco.igotrip.data.network.model.response.CommentResponse;
import com.techco.igotrip.ui.adapter.CommentAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.writecomment.WriteCommentActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class CommentActivity extends BaseActivity implements CommentBaseView {


    public static final int REQUEST_WRITE_COMMENT = 100;

    private static final String TAG = "CommentActivity";
    private static final String EXTRA_DATA = "DATA";

    @Inject
    CommentMvpPresenter<CommentBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.recyclerComment)
    RecyclerView recyclerComment;
    @BindView(R.id.txtNoComments)
    TextView txtNoComments;

    private Article article;
    private CommentAdapter adapter;
    private List<Comment> comments = new ArrayList<>();
    private int position;

    public static Intent getStartIntent(Context context, Article article) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(CommentActivity.EXTRA_DATA, article);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CommentActivity.this);

        setUp();

        mPresenter.getComment(article.getId());
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        article = getIntent().getParcelableExtra(EXTRA_DATA);
        txtTitle.setText(article.getTitle());
        imgRight.setVisibility(View.VISIBLE);

        adapter = new CommentAdapter(comments, (object, position) -> {
            this.position = position;
            mPresenter.checkComment(comments.get(position).getUserId());
        });
        recyclerComment.setAdapter(adapter);
    }

    @Override
    public void onGetCommentsSuccess(CommentListResponse response) {
        AppLogger.d(TAG, "Comments: " + response.getComments().size());
        comments.clear();
        comments.addAll(response.getComments());
        adapter.notifyDataSetChanged();
        recyclerComment.scrollToPosition(comments.size() - 1);
        updateList();
    }

    @Override
    public void onDeleteCommentSuccess() {
        comments.remove(position);
        adapter.notifyItemRemoved(position);
        updateList();
    }

    @Override
    public void onUpdateCommentSuccess(CommentResponse response) {
        if (response.getStatus() == 0) {
            comments.get(position).setContent(response.getComment().getContent());
            comments.get(position).setUpdatedAt(response.getComment().getUpdatedAt());
            comments.get(position).setEdited(Comment.EDITED);
            adapter.notifyItemChanged(position);
        } else {
            showSimpleDialog(getString(R.string.error_title), getString(R.string.message_unknown_error));
        }
    }

    @Override
    public void onCreateCommentSuccess(Comment comment) {
        comments.add(comment);
        adapter.notifyItemInserted(comments.size() - 1);
        recyclerComment.scrollToPosition(comments.size() - 1);
    }

    @Override
    public void onShowOptions() {
        showDialog();
    }

    @Override
    public void openWriteComment() {
        startActivityForResult(WriteCommentActivity.getStartIntent(this), REQUEST_WRITE_COMMENT);
    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick(View v) {
        mPresenter.checkLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_WRITE_COMMENT) {
            String content = data.getStringExtra("CONTENT");
            mPresenter.createComment(content, article.getId());
        }
    }

    private void showDialog() {
        List<String> datas = new ArrayList<>();
        datas.add(getString(R.string.edit));
        datas.add(getString(R.string.delete));
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.options), datas);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                int position = (int) o;
                dialog.dismissDialog(SimpleListDialog.TAG);
                new Handler().postDelayed(() -> {
                    if (position == 0) {
                        showEditCommentDialog();
                    } else {
                        mPresenter.deleteComment(comments.get(CommentActivity.this.position).getId());
                    }
                }, 300);
            }
        });
    }

    private void showEditCommentDialog() {
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), null, getString(R.string.edit_your_comment), null,
                getString(android.R.string.cancel), getString(R.string.enter_new_comment), InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        dialog.setCallback(new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                dialog.dismissDialog(AppDialog.TAG);
                String content = (String) o;
                mPresenter.updateComment(comments.get(position).getId(), content);
            }
        });
    }

    private void updateList() {
        if (comments.size() == 0) {
            txtNoComments.setVisibility(View.VISIBLE);
            recyclerComment.setVisibility(View.GONE);
        } else {
            txtNoComments.setVisibility(View.GONE);
            recyclerComment.setVisibility(View.VISIBLE);
        }
    }

}
