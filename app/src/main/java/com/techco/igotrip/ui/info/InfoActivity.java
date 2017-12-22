
package com.techco.igotrip.ui.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.changepassword.ChangePasswordActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class InfoActivity extends BaseActivity implements InfoBaseView {

    @Inject
    InfoMvpPresenter<InfoBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtFullname)
    TextView txtFullname;
    @BindView(R.id.txtBirthday)
    TextView txtBirthday;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtMale)
    TextView txtMale;
    @BindView(R.id.txtFemale)
    TextView txtFemale;
    @BindView(R.id.txtAccountType)
    TextView txtAccountType;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    private User user;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, InfoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(InfoActivity.this);

        setUp();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.info));
        mPresenter.getUserInfo();
    }

    @Override
    public void onGetUserInfoSuccess(User user) {
        this.user = user;
        txtUsername.setText(this.user.getUsername());
        txtPhone.setText(this.user.getPhone().isEmpty() ? getString(R.string.add_your_phone_number) : this.user.getPhone());
        txtEmail.setText(this.user.getEmail().isEmpty() ? getString(R.string.add_your_email_address) : this.user.getEmail());
        txtFullname.setText(this.user.getFullName().isEmpty() ? getString(R.string.add_your_full_name) : this.user.getFullName());
        txtBirthday.setText(this.user.getBirthday().isEmpty() ? getString(R.string.add_your_birthday) : this.user.getBirthday());
        txtAddress.setText(this.user.getAddress().isEmpty() ? getString(R.string.add_your_address) : this.user.getAddress());
        txtAccountType.setText(this.user.getAccountType() == User.FACEBOOK ? "FACEBOOK" : "TWITTER");
        if (this.user.getGender() == User.MALE) {
            selectedGender(true);
        } else {
            selectedGender(false);
        }
        Glide.with(this)
                .load(this.user.getImage())
                .into(imgAvatar);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.btnUpdate)
    public void onUpdateClick(View v) {

    }

    @OnClick(R.id.btnChangePassword)
    public void onChangePasswordClick(View v) {
        startActivity(ChangePasswordActivity.getStartIntent(this));
    }

    @OnClick(R.id.llPhone)
    public void onPhoneClick(View v) {

    }

    @OnClick(R.id.llEmail)
    public void onEmailClick(View v) {

    }

    @OnClick(R.id.llFullname)
    public void onFullnameClick(View v) {

    }

    @OnClick(R.id.llBirthday)
    public void onBirthdayClick(View v) {

    }

    @OnClick(R.id.llAddress)
    public void onAddressClick(View v) {

    }

    @OnClick(R.id.txtMale)
    public void onMaleClick(View v) {
        selectedGender(true);
        showUpdateButton();
        this.user.setGender(User.MALE);
    }

    @OnClick(R.id.txtFemale)
    public void onFemaleClick(View v) {
        selectedGender(false);
        showUpdateButton();
        this.user.setGender(User.FEMALE);
    }

    private void selectedGender(boolean isMale) {
        if (isMale) {
            txtMale.setBackgroundResource(R.drawable.bg_gender_male);
            txtMale.setTextColor(getResources().getColor(R.color.accent_brown));
            txtFemale.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            txtFemale.setTextColor(getResources().getColor(R.color.accent_white));
        } else {
            txtFemale.setBackgroundResource(R.drawable.bg_gender_female);
            txtFemale.setTextColor(getResources().getColor(R.color.accent_brown));
            txtMale.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            txtMale.setTextColor(getResources().getColor(R.color.accent_white));
        }
    }

    private void showUpdateButton() {
        btnUpdate.setVisibility(View.VISIBLE);
    }
}
