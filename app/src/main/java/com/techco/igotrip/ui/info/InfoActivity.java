
package com.techco.igotrip.ui.info;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.common.CommonUtils;
import com.techco.common.RegularUtil;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.changepassword.ChangePasswordActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.utils.permission.ErrorPermissionRequestListener;
import com.techco.igotrip.utils.permission.PermissionResultListener;
import com.techco.igotrip.utils.permission.SinglePermissionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class InfoActivity extends BaseActivity implements InfoBaseView, PermissionResultListener {

    private static final String TAG = "InfoActivity";
    public static final int REQUEST_IMAGE_CAPTURE = 10;
    public static final int REQUEST_IMAGE_PICK_UP = 11;

    private static final int TYPE_PHONE = 0;
    private static final int TYPE_EMAIL = 1;
    private static final int TYPE_FULL_NAME = 2;
    private static final int TYPE_ADDRESS = 3;

    @Inject
    InfoMvpPresenter<InfoBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.llPhone)
    LinearLayout llPhone;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.llEmail)
    LinearLayout llEmail;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.llFullname)
    LinearLayout llFullname;
    @BindView(R.id.txtFullname)
    TextView txtFullname;
    @BindView(R.id.llBirthday)
    LinearLayout llBirthday;
    @BindView(R.id.txtBirthday)
    TextView txtBirthday;
    @BindView(R.id.llAddress)
    LinearLayout llAddress;
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
    private PermissionListener cameraPermissionListener, readExternalStoragePermissionListener;
    private Bitmap bitmap;

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

        createPermissionListener();
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
        txtAccountType.setText(this.user.getAccountType() == User.FACEBOOK ? "FACEBOOK" : this.user.getAccountType() == User.TWITTER ? "TWITTER" : "APP");
        if (this.user.getGender() == User.MALE) {
            selectedGender(true);
        } else {
            selectedGender(false);
        }
        Glide.with(this)
                .load(this.user.getImage())
                .into(imgAvatar);
    }

    @Override
    public void onUpdateInfoSuccess() {
        bitmap = null;
        hideUpdateButton();
        showSimpleDialog(getString(R.string.success), getString(R.string.message_account_update_success));
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.btnUpdate)
    public void onUpdateClick(View v) {
        String phone = txtPhone.getText().toString().equals(getString(R.string.add_your_phone_number)) ? "" : txtPhone.getText().toString();
        String email = txtEmail.getText().toString().equals(getString(R.string.add_your_email_address)) ? "" : txtEmail.getText().toString();
        String fullname = txtFullname.getText().toString().equals(getString(R.string.add_your_full_name)) ? "" : txtFullname.getText().toString();
        String birthday = txtBirthday.getText().toString().equals(getString(R.string.add_your_birthday)) ? "" : txtBirthday.getText().toString();
        String address = txtAddress.getText().toString().equals(getString(R.string.add_your_address)) ? "" : txtAddress.getText().toString();
        int gender = this.user.getGender();
        String image64 = null;
        if (bitmap != null) {
            image64 = Utils.getEncoded64ImageStringFromBitmap(bitmap);
        }
        mPresenter.updateInfo(
                this.user.getId(),
                phone,
                email,
                fullname,
                birthday,
                address,
                gender,
                image64);
    }

    @OnClick(R.id.btnChangePassword)
    public void onChangePasswordClick(View v) {
        startActivity(ChangePasswordActivity.getStartIntent(this));
    }

    @OnClick(R.id.llPhone)
    public void onPhoneClick(View v) {
        showEditPopup(getString(R.string.edit_your_phone_number), getString(R.string.enter_your_phone_number), TYPE_PHONE);
    }

    @OnClick(R.id.llEmail)
    public void onEmailClick(View v) {
        showEditPopup(getString(R.string.edit_your_email), getString(R.string.enter_your_email), TYPE_EMAIL);
    }

    @OnClick(R.id.llFullname)
    public void onFullnameClick(View v) {
        showEditPopup(getString(R.string.edit_your_name), getString(R.string.enter_your_name), TYPE_FULL_NAME);
    }

    @OnClick(R.id.llBirthday)
    public void onBirthdayClick(View v) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String strYear = String.valueOf(year);
            month += 1;
            String strMonth = month < 10 ? "0" + month : String.valueOf(month);
            String strDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = strDayOfMonth + "/" + strMonth + "/" + strYear;
            txtBirthday.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.llAddress)
    public void onAddressClick(View v) {
        showEditPopup(getString(R.string.edit_your_address), getString(R.string.enter_your_address), TYPE_ADDRESS);
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

    boolean hasView = false;

    @OnClick(R.id.imgAvatar)
    public void onAvatarClick(View v) {
        if (this.user.getAccountType() == User.NORMAL) {
            List<String> datas = new ArrayList<>();
            hasView = false;
            if (!this.user.getImage().isEmpty()) {
                datas.add(getString(R.string.view_image));
                hasView = true;
            }
            datas.add(getString(R.string.choose_from_library));
            datas.add(getString(R.string.take_a_picture));
            SimpleListDialog dialog = SimpleListDialog.newInstance();
            dialog.show(getSupportFragmentManager(), getString(R.string.options), datas);
            dialog.setCallback(new DialogCallback<SimpleListDialog>() {
                @Override
                public void onNegative(SimpleListDialog dialog) {
                    dialog.dismissDialog(SimpleListDialog.TAG);
                }

                @Override
                public void onPositive(SimpleListDialog dialog, Object o) {
                    dialog.dismissDialog(SimpleListDialog.TAG);
                    int position = (int) o;
                    if (position == 0) {
                        if (hasView) {
                            //View image
                        } else {
                            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readExternalStoragePermissionListener);
                        }
                    } else if (position == 1) {
                        if (hasView) {
                            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readExternalStoragePermissionListener);
                        } else {
                            checkPermission(Manifest.permission.CAMERA, cameraPermissionListener);
                        }
                    } else {
                        checkPermission(Manifest.permission.CAMERA, cameraPermissionListener);
                    }
                }
            });
        } else {
            //View image
        }
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

    private void hideUpdateButton() {
        btnUpdate.setVisibility(View.GONE);
    }


    private TextView textView = null;
    private LinearLayout linearLayout = null;

    private void showEditPopup(String message, String hint, int type) {
        int inputType = InputType.TYPE_NULL;
        switch (type) {
            case TYPE_PHONE:
                textView = txtPhone;
                linearLayout = llPhone;
                inputType = InputType.TYPE_CLASS_PHONE;
                break;

            case TYPE_EMAIL:
                textView = txtEmail;
                linearLayout = llEmail;
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                break;

            case TYPE_FULL_NAME:
                textView = txtFullname;
                linearLayout = llFullname;
                inputType = InputType.TYPE_CLASS_TEXT;
                break;

            case TYPE_ADDRESS:
                textView = txtAddress;
                linearLayout = llAddress;
                inputType = InputType.TYPE_CLASS_TEXT;
                break;
        }
        linearLayout.setBackgroundResource(R.drawable.bg_info_red);
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), null, message, null, getString(android.R.string.cancel), hint, inputType);
        dialog.setCallback(new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
                linearLayout.setBackgroundResource(R.drawable.bg_info_blue);
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                linearLayout.setBackgroundResource(R.drawable.bg_info_blue);
                String content = (String) o;
                if (!content.isEmpty()) {
                    if (type == TYPE_EMAIL) {
                        if (!RegularUtil.isValidEmail(content)) {
                            showMessage(getString(R.string.message_email_invalid));
                            return;
                        }
                    }
                    textView.setText(content);
                    showUpdateButton();
                }
                dialog.dismissDialog(AppDialog.TAG);
            }
        });
    }

    private void checkPermission(String permission, PermissionListener permissionListener) {
        Dexter.withActivity(this)
                .withPermission(permission)
                .withListener(permissionListener)
                .withErrorListener(new ErrorPermissionRequestListener(this))
                .check();
    }

    private void createPermissionListener() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        PermissionListener feedbackViewPermissionListener = new SinglePermissionListener(this);
        cameraPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                SnackbarOnDeniedPermissionListener.Builder.with(viewGroup,
                        R.string.message_camera_permission_denied)
                        .withOpenSettingsButton(R.string.setting)
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                            }
                        })
                        .build());
        readExternalStoragePermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                SnackbarOnDeniedPermissionListener.Builder.with(viewGroup,
                        R.string.message_external_storage_permission_denied)
                        .withOpenSettingsButton(R.string.setting)
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                            }
                        })
                        .build());
    }

    @Override
    public void onPermissionError(String error) {
        AppLogger.d(TAG, "Permission requested error: " + error);
    }

    @Override
    public void onPermissionGranted(String permissionName) {
        AppLogger.d(TAG, "Permission granted: " + permissionName);
        if (permissionName.equals(Manifest.permission.CAMERA)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                AppLogger.d(TAG, "Could not open the camera for capture");
            }
        } else if (permissionName.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_PICK_UP);
        }
    }

    @Override
    public void onPermissionDenied(String permissionName, boolean isPermanentDenied) {
        AppLogger.d(TAG, "Permission denied: " + permissionName, ", Permanent denied: " + isPermanentDenied);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onPermissionRationale(PermissionToken token) {
        showConfirmDialog(getString(R.string.permission), getString(R.string.message_permission_rational), null, getString(android.R.string.cancel), new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
                token.cancelPermissionRequest();
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                dialog.dismissDialog(AppDialog.TAG);
                token.continuePermissionRequest();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            Glide.with(this)
                    .load(CommonUtils.bitmapToByte(bitmap))
                    .asBitmap()
                    .into(imgAvatar);
        }
        if (requestCode == REQUEST_IMAGE_PICK_UP) {
            Uri imageUri = data.getData();
            String picturePath = getPath(this, imageUri);
            bitmap = BitmapFactory.decodeFile(picturePath);
            Glide.with(this)
                    .load(CommonUtils.bitmapToByte(bitmap))
                    .asBitmap()
                    .into(imgAvatar);
        }
        showUpdateButton();
    }

    private String getPath(Context context, Uri uri) {
        Cursor cursor = null;
        String path = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        proj, sel, new String[]{id}, null);
            } else {
                cursor = context.getContentResolver().query(uri, proj, null, null, null);
            }
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                path = cursor.getString(column_index);
            }
        } finally {
            cursor.close();
        }
        return path;
    }
}
