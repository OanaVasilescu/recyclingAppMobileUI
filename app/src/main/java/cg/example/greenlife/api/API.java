package cg.example.greenlife.api;

import android.database.Observable;

import cg.example.greenlife.model.Tip;
import cg.example.greenlife.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @POST("users")
    Call<ResponseBody> createUser(
            @Body User user
    );

    @POST("users/login")
    Call<ResponseBody> checkUser(
           @Body User user
    );

    @GET("tips/random")
    Call<Tip> getRandomTip();
}
