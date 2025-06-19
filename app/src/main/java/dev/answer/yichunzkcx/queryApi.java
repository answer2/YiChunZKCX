package dev.answer.yichunzkcx;

public class queryApi {
    
    //宜春初中学考成绩查询 接口
    public static String getYiChunBaseApi() {
    	return "http://zkchaxun.yichun.gov.cn/zkcx";
    }
    
    //江西省高中阶段学校招生电子化管理平台 接口
    public static String getBaseJXeduApi() {
    	return "https://zkzz.jxedu.gov.cn";
    }
    
    //江西省普通高中学业水平考试 接口
    public static String AcademicLevelBaseApi() {
    	return "http://111.75.211.156:803";
    }
    
    public static String getCaptchaAPI() {
    	return getYiChunBaseApi() + "/captcha/gen";
    }
    
    public static String getGradeApi(){
        return getYiChunBaseApi() + "/cj/get";
    }
    
    public static String getJxeduCodeApi() {
    	return getBaseJXeduApi()+ "/login!img.action";
    }
    
    public static String getJxeduLoginApi() {
    	return getBaseJXeduApi() + "/login!doLogin.action";
    }
    
    public static String getJxeduEnrollQuery() {
    	return getBaseJXeduApi() + "/pages/main/stumain/studentEnrollQueryAction!query.action";
    }
    
    public static String getJxeduStudentInfo(){
        return getBaseJXeduApi() + "/studentSignUpAction!showh5.action";
    }
    
    public static String getAcademicLevelLogin() {
    	return AcademicLevelBaseApi() + "/MeaeApi/Common.ashx?action=GetTipReadState";
    }
    
    public static String getAcademicLevelCaptcha() {
    	return AcademicLevelBaseApi() + "/App/WebVerifyImage.aspx";
    }
    
    public static String getAcademicLevelGrade() {
    	return AcademicLevelBaseApi() + "/MyAccount/Examinee/ExamineeResultInfo.aspx";
    }
}
