package com.testapplication.retrofit.endpoint;

import com.testapplication.model.baseModel.BaseModel;
import com.testapplication.model.loginModel.LoginModel;
import com.testapplication.model.userModel.UserModel;
import com.testapplication.model.vehicleModel.VehicleModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Endpoints {

    @POST("login")
    Observable<BaseModel<LoginModel>> login(@Body RequestBody requestBody);

    @GET("vehicle_models")
    Observable<BaseModel<List<VehicleModel>>> getVehicleModel();

}
