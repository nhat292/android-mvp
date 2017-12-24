
package com.techco.igotrip.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.data.network.model.object.Nation;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.SelectNationResponse;
import com.techco.igotrip.ui.adapter.ContinentAdapter;
import com.techco.igotrip.ui.adapter.NationAdapter;
import com.techco.igotrip.ui.adapter.ProvinceMainAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.info.InfoActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailActivity;
import com.techco.igotrip.ui.viewholder.ProvinceMainViewHolder;
import com.techco.igotrip.utils.NationComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Nhat on 12/13/17.
 */


public class MainActivity extends BaseActivity implements MainBaseView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    MainMvpPresenter<MainBaseView> mPresenter;

    //Main
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.txtNationName)
    TextView txtNationName;
    @BindView(R.id.swipeRefreshLayoutMain)
    SwipeRefreshLayout swipeRefreshLayoutMain;
    @BindView(R.id.recyclerMainList)
    RecyclerView recyclerMainList;

    //Right Menu
    @BindView(R.id.recyclerContinent)
    RecyclerView recyclerContinent;
    @BindView(R.id.recyclerNation)
    RecyclerView recyclerNation;
    @BindView(R.id.txtNationEmpty)
    TextView txtNationEmpty;
    @BindView(R.id.rlRightMenu)
    RelativeLayout rlRightMenu;
    @BindView(R.id.rlMenuNation)
    RelativeLayout rlMenuNation;
    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    //LeftMenu
    @BindView(R.id.imgUser)
    CircleImageView imgUser;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnMenuLogin)
    Button btnMenuLogin;

    private ContinentAdapter continentAdapter;
    private List<Continent> continents = new ArrayList<>();
    private List<Nation> allNations = new ArrayList<>();
    private List<Nation> nations = new ArrayList<>();
    private int nationId = 0;
    private boolean isUserLogged = false;
    private List<Province> provinces = new ArrayList<>();
    private ProvinceMainAdapter provinceMainAdapter;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MainActivity.this);

        setUp();

        mPresenter.getFirstData();
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
        swipeRefreshLayoutMain.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayoutMain.setOnRefreshListener(this);

        provinceMainAdapter = new ProvinceMainAdapter(provinces, (object, position) -> {
            short type = (short) object;
            if (type == ProvinceMainViewHolder.CLICK_TYPE_ITEM) {
                startActivity(ProvinceDetailActivity.getStartIntent(this, this.provinces.get(position)));
            } else {

            }
        });
        recyclerMainList.setAdapter(provinceMainAdapter);
    }

    @OnClick(R.id.imgMenu)
    public void onMenuClick() {
        drawer.openDrawer(Gravity.START);
    }

    @OnClick(R.id.imgLogo)
    public void onLogoClick() {
        finish();
    }

    @OnClick(R.id.llCountry)
    public void onCountryClick() {
        rlMenuNation.setVisibility(View.GONE);
        drawer.openDrawer(Gravity.END);
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

            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuExperiences)
    public void onMenuExperiencesClick() {
        if (isUserLogged()) {

            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuFavorites)
    public void onMenuFavoritesClick() {
        if (isUserLogged()) {

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

    @OnClick(R.id.btnChangeNation)
    public void onChangeNationClick() {
        onCountryClick();
    }

    @Override
    public void onRefresh() {
        mPresenter.selectNation(nationId, false, false);
    }

    @Override
    public void onGetFirstDataSuccess(FirstDataResponse response) {
        continents.clear();
        allNations.clear();

        continents.addAll(response.getData().getContinents());
        allNations.addAll(response.getData().getNations());

        continents.get(0).setSelected(true);
        rlRightMenu.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        continentAdapter = new ContinentAdapter(continents, (object, position) -> {
            if (rlMenuNation.getVisibility() == View.GONE) {
                rlMenuNation.setVisibility(View.VISIBLE);
                Animation slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
                rlMenuNation.startAnimation(slideInRight);
            }
            nations.clear();
            Continent continent = (Continent) object;
            for (int i = 0; i < allNations.size(); i++) {
                Nation n = allNations.get(i);
                if (n.getContinentId() == continent.getId()) {
                    nations.add(n);
                }
            }
            if (nations.size() == 0) {
                txtNationEmpty.setVisibility(View.VISIBLE);
            } else {
                txtNationEmpty.setVisibility(View.GONE);
            }
            Collections.sort(nations, new NationComparator());

            NationAdapter nationAdapter = new NationAdapter(nations, (o, n) -> {
                drawer.closeDrawer(Gravity.END);
                nationId = ((Nation) o).getId();
                txtNationName.setText(((Nation) o).getName());
                mPresenter.selectNation(nationId, true, false);
            });
            recyclerNation.setAdapter(nationAdapter);
        });
        recyclerContinent.setAdapter(continentAdapter);

        for (int i = 0; i < allNations.size(); i++) {
            Nation n = allNations.get(i);
            if (n.getCountryCode().toLowerCase().equals("vn")) {
                nationId = n.getId();
                txtNationName.setText(n.getName());
            }
        }
        if (nationId == 0) {
            txtNationName.setText("Select country");
        } else {
            mPresenter.selectNation(nationId, true, true);
        }
    }

    @Override
    public void onSelectNationSuccess(SelectNationResponse response) {
        swipeRefreshLayoutMain.setRefreshing(false);
        this.provinces.clear();
        this.provinces.addAll(response.getProvinces());
        if (provinces.size() > 0) {
            llEmpty.setVisibility(View.GONE);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
        }
        provinceMainAdapter.notifyDataSetChanged();
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
}
