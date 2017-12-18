
package com.myproject.framework.mvp.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproject.framework.mvp.R;
import com.myproject.framework.mvp.dagger.component.ActivityComponent;
import com.myproject.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class AboutFragment extends BaseFragment implements AboutBaseView {

    public static final String TAG = "AboutFragment";

    @Inject
    AboutMvpPresenter<AboutBaseView> mPresenter;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
