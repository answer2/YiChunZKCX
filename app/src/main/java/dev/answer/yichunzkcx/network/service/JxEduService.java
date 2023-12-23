package dev.answer.yichunzkcx.network.service;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JxEduService {
    
    @GET("login!img.action")
    public Call<ResponseBody> queryCode(@Query("d") String time);
    
    @FormUrlEncoded
    @POST("login!doLogin.action")
    public Call<ResponseBody> login(
        @Header("Cookie") String cookie,
        @Query("rand") String rand,
        @Field("object.username") String username,
        @Field("password") String password,
        @Field("object.amendpwd") String amendPwd,
        @Field("object.remark") String remark,
        @Field("validateCode") String validateCode,
        @Field("loginUrl") String loginUrl
    );
    
    @GET("pages/main/stumain/studentEnrollQueryAction!query.action")
    public Call<ResponseBody> enrollQuery( @Header("Cookie") String cookie);
} 