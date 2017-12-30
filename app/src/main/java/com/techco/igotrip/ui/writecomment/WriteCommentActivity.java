
package com.techco.igotrip.ui.writecomment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class WriteCommentActivity extends BaseActivity implements WriteCommentBaseView {

    @Inject
    WriteCommentMvpPresenter<WriteCommentBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WriteCommentActivity.class);
        return intent;
    }

    @BindView(R.id.editContent)
    EditText editContent;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(WriteCommentActivity.this);

        setUp();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.write_comment));
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_send);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick(View v) {
        String content = editContent.getText().toString();
        if (content.isEmpty()) {
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("CONTENT", content);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
