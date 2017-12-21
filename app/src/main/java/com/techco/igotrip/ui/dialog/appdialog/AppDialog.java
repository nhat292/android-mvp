
package com.techco.igotrip.ui.dialog.appdialog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.dagger.component.ActivityComponent;
import com.techco.igotrip.ui.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nhat on 12/13/17.
 */


public class AppDialog extends BaseDialog {

    public static final String TAG = "AppDialog";

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtMessage)
    TextView txtMessage;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnOk)
    Button btnOk;

    public static AppDialog newInstance() {
        AppDialog fragment = new AppDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_app, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            setUnBinder(ButterKnife.bind(this, view));
        }

        return view;
    }

    private String title;
    private String message;
    private String okText;
    private String cancelText;
    private boolean showCancel;

    public void show(FragmentManager fragmentManager, String title, String message, boolean showCancel, String okText, String cancelText) {
        super.show(fragmentManager, TAG);
        this.title = title;
        this.message = message;
        this.showCancel = showCancel;
        this.okText = okText;
        this.cancelText = cancelText;
    }

    @Override
    protected void setUp(View view) {
        if (title != null) {
            txtTitle.setText(title);
        } else {
            txtTitle.setVisibility(View.GONE);
        }
        if (message != null) {
            txtMessage.setText(message);
        } else {
            txtMessage.setVisibility(View.GONE);
        }
        if (!showCancel) {
            btnCancel.setVisibility(View.GONE);
        }
        if(okText != null) {
            btnOk.setText(okText);
        }
        if(cancelText != null) {
            btnCancel.setText(cancelText);
        }
    }

    @OnClick(R.id.btnCancel)
    public void onCancelClick(View v) {
        if (callback != null) {
            callback.onNegative(this);
        }
    }

    @OnClick(R.id.btnOk)
    public void onOkClick(View v) {
        if (callback != null) {
            callback.onPositive(this);
        }
    }
}