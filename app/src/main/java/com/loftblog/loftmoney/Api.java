package com.loftblog.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    @GET("items")
    Call<List<Item>> getItems(@Query("type") String type,@Query("auth-token") String token);

    @GET("auth")


    // auth?social_user_id=<user_id>
    //{"status":"success","id": id_юзера, “auth-token”: auth-token}
    //в случае ошибки {"status":"Текст_ошибки"}

}
