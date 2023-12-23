package dev.answer.yichunzkcx.network.service;
import dev.answer.yichunzkcx.bean.CaptchaResponse;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.queryApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface YiChunZkService {
    
    @POST("captcha/gen")
    public Call<CaptchaResponse> queryCode();
	
    @FormUrlEncoded
    @POST("cj/get")
    public Call<GradeResponse> queryGrade(
        @Field("xm1") String name,
         @Field("zkzh") String zk_number,
         @Field("captchaId") String codeId,
         @Field("captchaContent") String code
        );
} 