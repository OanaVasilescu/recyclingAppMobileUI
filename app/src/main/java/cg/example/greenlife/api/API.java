package cg.example.greenlife.api;

import cg.example.greenlife.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @POST("users")
    Call<ResponseBody> createUser(
            @Body User user
    );

    @Multipart
    @POST("login")
    Call<ResponseBody> checkUser(
            @Part("emailOrUsername") String emailOrUsername,
            @Part("password") String password
    );
}
