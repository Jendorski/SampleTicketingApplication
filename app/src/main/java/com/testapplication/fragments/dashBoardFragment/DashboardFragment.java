package com.testapplication.fragments.dashBoardFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.testapplication.R;
import com.testapplication.databinding.RecyclerViewLayoutBinding;
import com.testapplication.fragments.baseFragment.BaseFragment;
import com.testapplication.loaders.dashBoardLoader.DashboardLoader;
import com.testapplication.loaders.loginLoader.LoginLoader;

public class DashboardFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardLoader dashboardLoader;

    private AppCompatActivity appCompatActivity;

    private RecyclerViewLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appCompatActivity = (AppCompatActivity) getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_view_layout, container, false);

        binding.dashboardToolbar.setTitle("Dashboard");

        binding.swipeRefreshLayoutView.setOnRefreshListener(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        dashboardLoader = new DashboardLoader.Builder(appCompatActivity)
                .withErrorText(binding.errorTextView)
                .withProgressBar(binding.recyclerProgress)
                .withRecyclerView(binding.recyclerLayoutView)
                .build();

        dashboardLoader.getVehicleModels();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dashboardLoader.clearDisposables();
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public void onRefresh() {
        binding.swipeRefreshLayoutView.setRefreshing(false);
        dashboardLoader.getVehicleModels();
    }
}
