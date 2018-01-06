
package com.techco.igotrip.ui.addpictures;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.ui.adapter.ImageAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.custom.views.SpacesItemDecoration;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.post.PostActivity;
import com.techco.igotrip.utils.permission.ErrorPermissionRequestListener;
import com.techco.igotrip.utils.permission.PermissionResultListener;
import com.techco.igotrip.utils.permission.SinglePermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class AddPicturesActivity extends BaseActivity implements PermissionResultListener {

    private static final String TAG = "AddPicturesActivity";

    public static final int REQUEST_IMAGE_CAPTURE = 10;
    public static final int REQUEST_IMAGE_PICK_UP = 11;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddPicturesActivity.class);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.recyclerPictures)
    RecyclerView recyclerPictures;

    private ImageAdapter adapter;
    private PermissionListener cameraPermissionListener, readExternalStoragePermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures);

        setUnBinder(ButterKnife.bind(this));

        setUp();

        createPermissionListener();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.add_pictures));

        recyclerPictures.setLayoutManager(new GridLayoutManager(this, 2));
        int spacingInPixels = (int) getResources().getDimension(R.dimen.item_spacing);
        recyclerPictures.addItemDecoration(new SpacesItemDecoration(spacingInPixels, true));
        adapter = new ImageAdapter(PostActivity.bitmaps, ((object, position) -> {

        }));
        recyclerPictures.setAdapter(adapter);

    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.imgCapture)
    public void onCaptureClick() {
        checkPermission(Manifest.permission.CAMERA, cameraPermissionListener);
    }

    @OnClick(R.id.imgGallery)
    public void onGalleryClick() {
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readExternalStoragePermissionListener);
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
            Bitmap bitmap = (Bitmap) extras.get("data");
            PostActivity.bitmaps.add(bitmap);
            adapter.notifyItemInserted(PostActivity.bitmaps.size() - 1);
        }
        if (requestCode == REQUEST_IMAGE_PICK_UP) {
            Uri imageUri = data.getData();
            String picturePath = getPath(this, imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            PostActivity.bitmaps.add(bitmap);
            adapter.notifyItemInserted(PostActivity.bitmaps.size() - 1);
        }
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
