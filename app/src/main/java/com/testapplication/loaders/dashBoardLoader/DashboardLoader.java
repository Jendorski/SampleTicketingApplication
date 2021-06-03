package com.testapplication.loaders.dashBoardLoader;

import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.testapplication.R;
import com.testapplication.adapter.vehicleModelAdapter.VehicleModelAdapter;
import com.testapplication.model.baseModel.BaseModel;
import com.testapplication.model.vehicleModel.VehicleModel;
import com.testapplication.retrofit.Client;
import com.testapplication.retrofit.endpoint.Endpoints;
import com.testapplication.toastUI.ToastUI;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardLoader {

    private static final String TAG = DashboardLoader.class.getSimpleName();

    private Builder builder;

    public DashboardLoader(Builder builder) {
        this.builder = builder;
    }

    public void getVehicleModels(){

        builder.progressBar.setVisibility(View.VISIBLE);

        Client.getClient()
                .create(Endpoints.class)
                .getVehicleModel()
                .takeWhile(BaseModel::isSuccess)
                .subscribeOn(Schedulers.io())
                .map(BaseModel::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VehicleModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        builder.compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<VehicleModel> models) {
                        ToastUI.showToast("Vehicle Models retrieved successfully", builder.appCompatActivity, R.color.green_500);

                        builder.progressBar.setVisibility(View.INVISIBLE);

                        System.out.print(TAG + "The total models: " + models.size());

                        VehicleModelAdapter adapter = new VehicleModelAdapter(models);
                        adapter.notifyDataSetChanged();

                        LinearLayoutManager layoutManager = new LinearLayoutManager(builder.appCompatActivity);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(builder.appCompatActivity,
                                layoutManager.getOrientation());

                        builder.recyclerView.addItemDecoration(dividerItemDecoration);
                        builder.recyclerView.setLayoutManager(layoutManager);
                        builder.recyclerView.setAdapter(adapter);
                        builder.recyclerView.setHasFixedSize(true);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        ToastUI.showToast(e.getMessage(), builder.appCompatActivity, R.color.color_error);
                        builder.progressBar.setVisibility(View.INVISIBLE);
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

        private AppCompatTextView errorText;

        private RecyclerView recyclerView;

        public Builder(AppCompatActivity appCompatActivity) {
            this.appCompatActivity = appCompatActivity;
        }

        public Builder withRecyclerView(RecyclerView recyclerView){
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder withProgressBar(ProgressBar progressBar){
            this.progressBar = progressBar;
            Sprite fadingCircle = new FadingCircle();
            this.progressBar.setIndeterminateDrawable(fadingCircle);
            return this;
        }

        public Builder withErrorText(AppCompatTextView appCompatTextView){
            this.errorText = appCompatTextView;
            return this;
        }

        public DashboardLoader build(){
            return new DashboardLoader(this);
        }

    }

}
