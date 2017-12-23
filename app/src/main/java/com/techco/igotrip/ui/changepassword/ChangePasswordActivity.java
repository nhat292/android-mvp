
package com.techco.igotrip.ui.changepassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class ChangePasswordActivity extends BaseActivity implements ChangePasswordBaseView {

    @Inject
    ChangePasswordMvpPresenter<ChangePasswordBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.editCurrentPassword)
    EditText editCurrentPassword;
    @BindView(R.id.editNewPassword)
    EditText editNewPassword;
    @BindView(R.id.editRePassword)
    EditText editRePassword;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ChangePasswordActivity.this);

        setUp();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.change_password));
    }

    @Override
    public void onChangePasswordSuccess() {
        showConfirmDialog(getString(R.string.success), getString(R.string.message_change_password_success), null, null, new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                dialog.dismissDialog(AppDialog.TAG);
                ChangePasswordActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick(View v) {
        mPresenter.changePassword(editCurrentPassword.getText().toString().trim(),
                editNewPassword.getText().toString().trim(),
                editRePassword.getText().toString().trim());
    }

}
