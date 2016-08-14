package com.app.autometicimageslideshow;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Saurabh on 22-06-2016.
 */
public interface ApiInterface {
    public static final String BASE_URL = "http://saurabh.ueuo.com/ci/index.php/";

    @GET("Pictures/Category/Birds")
    public Call<ImageItemModel> GetAllPictures();



    public class ApiClient {
        public static ApiInterface apiInterface;


        public static ApiInterface getApiInterface() {
            if (apiInterface == null) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(ApiInterface.class);
                return apiInterface;
            } else {
                return apiInterface;
            }
        }

    }


}
