
package com.techco.igotrip.ui.mytrip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.adapter.JourneyAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.createtrip.CreateTripActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.journey.JourneyActivity;
import com.techco.igotrip.ui.viewholder.JourneyViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class MyTripActivity extends BaseActivity implements MyTripBaseView, SwipeRefreshLayout.OnRefreshListener {

    private static final int REQUEST_CREATE_TRIP = 100;

    @Inject
    MyTripMvpPresenter<MyTripBaseView> mPresenter;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyTripActivity.class);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;
    @BindView(R.id.txtNoData)
    TextView txtNoData;

    private JourneyAdapter adapter;
    private List<Journey> journeys = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MyTripActivity.this);

        setUp();

        mPresenter.getJourneys(true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.my_trip));
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_plus);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new JourneyAdapter(journeys, ((object, position) -> {
            short type = (short) object;
            if (type == JourneyViewHolder.TYPE_ITEM) {
                startActivity(JourneyActivity.getStartIntent(MyTripActivity.this, journeys.get(position)));
            } else {
                showConfirmDialog(
                        getString(R.string.confirm_title),
                        getString(R.string.message_confirm_delete),
                        getString(R.string.delete),
                        getString(android.R.string.cancel),
                        new DialogCallback<AppDialog>() {
                            @Override
                            public void onNegative(AppDialog dialog) {
                                dialog.dismissDialog(AppDialog.TAG);
                            }

                            @Override
                            public void onPositive(AppDialog dialog, Object o) {
                                dialog.dismissDialog(AppDialog.TAG);
                                MyTripActivity.this.position = position;
                                mPresenter.deleteJourney(journeys.get(MyTripActivity.this.position).getId());
                            }
                        }
                );
            }
        }));
        recyclerList.setAdapter(adapter);

    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick() {
        startActivityForResult(CreateTripActivity.getStartIntent(this, -1), REQUEST_CREATE_TRIP);
    }

    @Override
    public void onRefresh() {
        mPresenter.getJourneys(false);
    }

    @Override
    public void onShowRefreshLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideRefreshLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetJourneysSuccess(List<Journey> journeys) {
        this.journeys.clear();
        this.journeys.addAll(journeys);
        adapter.notifyDataSetChanged();
        updateUI();
    }

    @Override
    public void onDeleteJourneySuccess() {
        journeys.remove(position);
        adapter.notifyItemRemoved(position);
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CREATE_TRIP) {
            mPresenter.getJourneys(false);
        }
    }

    private void updateUI() {
        if (this.journeys.size() > 0) {
            recyclerList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.INVISIBLE);
        } else {
            recyclerList.setVisibility(View.INVISIBLE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }
}
