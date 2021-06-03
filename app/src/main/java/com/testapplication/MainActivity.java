package com.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.testapplication.fragments.dashBoardFragment.DashboardFragment;
import com.testapplication.fragments.loginFragment.LoginFragment;
import com.testapplication.preferenceHelper.PreferenceHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);

        boolean b = preferenceHelper.getIsLoggedIn();

        System.out.print(TAG + " b is: " + b);

        if(b){
            toLogin();
        }
        else{
            toDashBoard();
        }
    }

    private void toDashBoard() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new DashboardFragment(), DashboardFragment.class.getSimpleName())
                .commit();
    }

    private void toLogin(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new LoginFragment(), LoginFragment.class.getSimpleName())
                .addToBackStack(LoginFragment.class.getSimpleName())
                .commit();
    }

}