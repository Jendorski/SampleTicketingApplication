package com.testapplication.fragments.loginFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;

import com.testapplication.R;
import com.testapplication.databinding.LoginLayoutBinding;
import com.testapplication.fragments.baseFragment.BaseFragment;
import com.testapplication.loaders.loginLoader.LoginLoader;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginLayoutBinding binding;

    private AppCompatActivity appCompatActivity;

    private LoginLoader loginLoader;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        appCompatActivity = (AppCompatActivity) getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.login_layout, container, false);

        binding.loginButton.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginLoader = new LoginLoader.Builder(appCompatActivity)
                .withProgressBar(binding.loginProgressBar)
                .build();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginLoader.clearDisposables();
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        loginLoader.prepareToLogin(binding.etEmail.getEditableText().toString(), binding.etPwd.getEditableText().toString());
    }
}
