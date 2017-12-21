
package com.techco.igotrip.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.techco.common.KeyboardUtils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class SignUpActivity extends BaseActivity implements SignUpBaseView {

    @Inject
    SignUpMvpPresenter<SignUpBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.editUsername)
    EditText editUsername;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editFullname)
    EditText editFullname;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editRePassword)
    EditText editRePassword;
    @BindView(R.id.txtMale)
    TextView txtMale;
    @BindView(R.id.txtFemale)
    TextView txtFemale;

    private int gender = User.MALE;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SignUpActivity.this);

        setUp();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.register_text));
        editRePassword.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            KeyboardUtils.hideSoftInput(SignUpActivity.this);
            return false;
        });
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick(View v) {
        mPresenter.onSubmitClick(
                editUsername.getText().toString().trim(),
                editEmail.getText().toString().trim(),
                editFullname.getText().toString().trim(),
                editPassword.getText().toString().trim(),
                editRePassword.getText().toString().trim(),
                gender
        );
    }

    @OnClick(R.id.txtMale)
    public void onMaleClick() {
        gender = User.MALE;
        txtMale.setBackgroundResource(R.drawable.bg_gender_male);
        txtMale.setTextColor(getResources().getColor(R.color.accent_brown));
        txtFemale.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        txtFemale.setTextColor(getResources().getColor(R.color.accent_white));
    }

    @OnClick(R.id.txtFemale)
    public void onFemaleClick() {
        gender = User.FEMALE;
        txtFemale.setBackgroundResource(R.drawable.bg_gender_female);
        txtFemale.setTextColor(getResources().getColor(R.color.accent_brown));
        txtMale.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        txtMale.setTextColor(getResources().getColor(R.color.accent_white));
    }

    @Override
    public void onRegisterSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
