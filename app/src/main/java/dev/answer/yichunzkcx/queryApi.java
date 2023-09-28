package dev.answer.yichunzkcx;

public class queryApi {
    
    public static String getYiChunBaseApi() {
    	return "https://zkchaxun.yichun.gov.cn/zkcx";
    }
    
    public static String getBaseJXeduApi() {
    	return "https://zkzz.jxedu.gov.cn";
    }
    
    public static String getCaptchaAPI() {
    	return getYiChunBaseApi() + "/captcha/gen";
    }
    
    public static String getGradeApi(){
        return getYiChunBaseApi() + "/cj/get";
    }
    
    public static String getJXeduCodeApi() {
    	return getBaseJXeduApi()+ "/login!img.action";
    }
    
    public static String getJxeduLoginApi() {
    	return getBaseJXeduApi() + "login!doLogin.action";
    }
}
