package com.loftblog.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    @GET("items")
//    @Headers("auth-token: $2y$10$MI9aJHOPZNR1WLHMPoRkx.6geJcwuzU/JxArRxeOoK9KXyPs3DzfG")
    Call<List<Item>> getItems(@Query("type") String type,@Query("auth-token") String token);

}
