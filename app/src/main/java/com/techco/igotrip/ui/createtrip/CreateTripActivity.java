
package com.techco.igotrip.ui.createtrip;

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


public class CreateTripActivity extends BaseActivity implements CreateTripBaseView {

    private static final String EXTRA_ARTICLE_ID = "ARTICLE_ID";

    @Inject
    CreateTripMvpPresenter<CreateTripBaseView> mPresenter;

    public static Intent getStartIntent(Context context, int articleId) {
        Intent intent = new Intent(context, CreateTripActivity.class);
        intent.putExtra(CreateTripActivity.EXTRA_ARTICLE_ID, articleId);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editDescription)
    EditText editDescription;

    private int articleId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CreateTripActivity.this);

        setUp();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.create_trip));
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_send);

        articleId = getIntent().getIntExtra(EXTRA_ARTICLE_ID, -1);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick(View v) {
        mPresenter.createAndAddTrip(
                editTitle.getText().toString(),
                editDescription.getText().toString(),
                articleId
        );
    }

    @Override
    public void onCreateJourneySuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
