package com.example.manvish.scrollingactivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by manvish on 1/17/18.
 */

public interface ApiInterface {

    @FormUrlEncoded // annotation used in POST type requests
    @POST("/retrofit/register.php")
        // API's endpoints
    Call<SignUpResponse> registration(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("logintype") String logintype);

// for GET request

    @GET("/retrofit/getuser.php")
        // API's endpoints
    Call<List<UserListResponse>> getUsersList();



// UserListResponse is POJO class to get the data from API,
// we use List<UserListResponse> in callback because the data
// in our API is starting from JSONArray
}
