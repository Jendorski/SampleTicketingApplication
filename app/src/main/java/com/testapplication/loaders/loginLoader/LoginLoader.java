package com.testapplication.loaders.loginLoader;

import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.testapplication.R;
import com.testapplication.fragments.dashBoardFragment.DashboardFragment;
import com.testapplication.model.baseModel.BaseModel;
import com.testapplication.model.loginModel.LoginModel;
import com.testapplication.preferenceHelper.PreferenceHelper;
import com.testapplication.retrofit.Client;
import com.testapplication.retrofit.endpoint.Endpoints;
import com.testapplication.toastUI.ToastUI;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginLoader {

    private static final String TAG = LoginLoader.class.getSimpleName();

    private Builder builder;

    public LoginLoader(Builder builder) {
        this.builder = builder;
    }

    private boolean isEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidated(String email, String password){
        return !email.isEmpty() && !password.isEmpty() && isEmail(email);
    }

    public void prepareToLogin(String email, String password){
        boolean b = isValidated(email, password);
        if(b){
            login(email, password);
        }
        else{
            ToastUI.showToast("Validation Error", builder.appCompatActivity, R.color.color_error);
        }
    }

    private void login(String email, String password){

        builder.progressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password);

        JSONObject jsonObject = new JSONObject(hashMap);

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

        Client.getClient()
                .create(Endpoints.class)
                .login(body)
                .takeWhile(BaseModel::isSuccess)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(model -> {
                    builder.preferenceHelper.putJWTToken(model.getData().getAccessToken());
                    builder.preferenceHelper.putIsLoggedIn(true);
                    return model.getData();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        builder.compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull LoginModel model) {
                        ToastUI.showToast("Successfully logged in", builder.appCompatActivity, R.color.green_500);

                        builder.progressBar.setVisibility(View.INVISIBLE);

                        builder.appCompatActivity
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame_layout, new DashboardFragment(), DashboardFragment.class.getSimpleName())
                                .commit();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        builder.progressBar.setVisibility(View.INVISIBLE);
                        ToastUI.showToast(e.getMessage(), builder.appCompatActivity, R.color.color_error);
                    }

                    @Override
                    public void onComplete() {
                        builder.progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void clearDisposables(){
        builder.compositeDisposable.clear();
        builder.compositeDisposable.dispose();
    }

    public static class Builder{

        private AppCompatActivity appCompatActivity;

        private CompositeDisposable compositeDisposable = new CompositeDisposable();

        private ProgressBar progressBar;

        private PreferenceHelper preferenceHelper;

        public Builder(AppCompatActivity appCompatActivity) {
            this.appCompatActivity = appCompatActivity;
            this.preferenceHelper = new PreferenceHelper(appCompatActivity);
        }

        public Builder withProgressBar(ProgressBar progressBar){
            this.progressBar = progressBar;
            Sprite fadingCircle = new FadingCircle();
            progressBar.setIndeterminateDrawable(fadingCircle);
            return this;
        }

        public LoginLoader build(){
            return new LoginLoader(this);
        }

    }

}
