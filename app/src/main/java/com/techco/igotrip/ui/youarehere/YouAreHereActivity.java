
package com.techco.igotrip.ui.youarehere;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.experience.ExperienceActivity;
import com.techco.igotrip.ui.favorite.FavoriteActivity;
import com.techco.igotrip.ui.info.InfoActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.mytrip.MyTripActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Nhat on 12/13/17.
 */


public class YouAreHereActivity extends BaseActivity implements YouAreHereBaseView, OnMapReadyCallback {

    @Inject
    YouAreHereMvpPresenter<YouAreHereBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, YouAreHereActivity.class);
        return intent;
    }

    //Main
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //LeftMenu
    @BindView(R.id.imgUser)
    CircleImageView imgUser;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnMenuLogin)
    Button btnMenuLogin;

    @BindView(R.id.recyclerSubType)
    RecyclerView recyclerSubType;
    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;

    private List<Type> mTypes = new ArrayList<>();
    private List<SubType> mSubTypes = new ArrayList<>();
    private Type mCurrentType;
    private SubType mCurrentSubType;
    private TypeAdapter mTypeAdapter;
    private SubTypeAdapter mSubTypeAdapter;

    private boolean isUserLogged = false;

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_are_here);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(YouAreHereActivity.this);

        setUp();

        mPresenter.getExploreData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUserStatus();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    protected void setUp() {
        mTypeAdapter = new TypeAdapter(this.mTypes, (object, position) -> {
            this.mCurrentType = (Type) object;

        });
        mSubTypeAdapter = new SubTypeAdapter(this.mSubTypes, ((object, position) -> {
            this.mCurrentSubType = (SubType) object;
        }));

        recyclerType.setAdapter(mTypeAdapter);
        recyclerSubType.setAdapter(mSubTypeAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.imgMenu)
    public void onMenuClick() {
        drawer.openDrawer(Gravity.START);
    }

    @OnClick(R.id.imgLogo)
    public void onLogoClick() {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick() {

    }

    @OnClick(R.id.btnMenuInfo)
    public void onMenuInfoClick() {
        if (isUserLogged()) {
            startActivity(InfoActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuMyTrip)
    public void onMenuMyTripClick() {
        if (isUserLogged()) {
            startActivity(MyTripActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuExperiences)
    public void onMenuExperiencesClick() {
        if (isUserLogged()) {
            startActivity(ExperienceActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuFavorites)
    public void onMenuFavoritesClick() {
        if (isUserLogged()) {
            startActivity(FavoriteActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuSupport)
    public void onMenuSupportClick() {
        startActivity(com.techco.igotrip.ui.support.SupportActivity.getStartIntent(this));
        closeLeftMenu();
    }

    @OnClick(R.id.btnMenuLogin)
    public void onMenuLoginClick() {
        if (isUserLogged()) {
            showConfirmDialog(getString(R.string.confirm_title), getString(R.string.message_ask_logout), null, getString(android.R.string.cancel), new DialogCallback<AppDialog>() {
                @Override
                public void onNegative(AppDialog dialog) {
                    dialog.dismissDialog(AppDialog.TAG);
                }

                @Override
                public void onPositive(AppDialog dialog, Object o) {
                    dialog.dismissDialog(AppDialog.TAG);
                    mPresenter.logout();
                    closeLeftMenu();
                }
            });
        }
    }

    @Override
    public void onCheckUserStatusSuccess(User user) {
        if (user != null) {
            isUserLogged = true;
            txtUser.setText(user.getFullName());
            btnMenuLogin.setText(getString(R.string.menu_logout));
            if (!user.getImage().isEmpty()) {
                Glide.with(this)
                        .load(user.getImage())
                        .into(imgUser);
            }
        } else {
            isUserLogged = false;
            txtUser.setText(getString(R.string.guest_text));
            btnMenuLogin.setText(getString(R.string.menu_login));
            imgUser.setImageResource(0);
        }
    }

    @Override
    public void onGetExploreDataSuccess(ExploreDataResponse response) {
        this.mTypes.clear();
        this.mSubTypes.clear();

        this.mTypes.addAll(response.getData().getTypes());
        this.mSubTypes.addAll(response.getData().getSubTypes());

        if (this.mTypes.size() > 0) {
            this.mTypes.get(0).setSelected(true);
            mCurrentType = this.mTypes.get(0);
        }
        if (this.mSubTypes.size() > 0) {
            this.mSubTypes.get(0).setSelected(true);
            mCurrentSubType = this.mSubTypes.get(0);
        }

        mTypeAdapter.notifyDataSetChanged();
        mSubTypeAdapter.notifyDataSetChanged();
    }

    private boolean isUserLogged() {
        if (isUserLogged) {
            return true;
        }
        startActivity(LoginActivity.getStartIntent(this));
        return false;
    }

    private void closeLeftMenu() {
        drawer.closeDrawer(Gravity.START);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
