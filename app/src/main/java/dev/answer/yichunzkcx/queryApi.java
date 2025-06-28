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
    	return "http://111.75.211.156:8080";
    }

    public static String getGkCxJxeduApi(){return "https://jxcf.jxeea.cn";}

    
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

    // gk
    // 查分
    /*
    * 请求
    * key1=IwNgDALATAHFUE4oHYg%3D //准考证
    * &key2=Gw5A //身份证后四位
    * &key3=M4TwJg7kA%3D%3D%3D // 验证码
    * MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    * RequestBody body = RequestBody.create(mediaType, "key1=IwNgDALATAHFUE4oHYg%3D&key2=Gw5A&key3=M4TwJg7kA%3D%3D%3D");
    * cookie
    * _cap_id: VcyZDrakgghNti%2FYTUSuh87z3RvfqTPU%2BjYfUvz5hAHmAT63quSVLA%3D%3D
    * SLBServerPool78:
    * */

    // code
    //https://jxcf.jxeea.cn/captcha/getcode?t=1751082354181
    /*
    * {
  "Code": 1,
  "Msg": "",
  "Data": {
    "Txt": "",
    "Img": "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAeAEADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD32TfsPllQ/bcOKUZwMgA9wDmqupalZ6Pp81/qE6wWsIy8jAnHboOSc9hXE+J/HkB0O8tIrbV9OurqBlsbmaAwiVzgLsbOQeR1Ax14rpw+Eq4hpQWjdr/12Jcktzu4bq3uHlSC4ileFtsio4Yo3ocdDUC31nPexRLdqJcMUj3gebjg4HVgMdRxXFapBP4fl0zXb2IuJRFbX1upLtIQvDk9C4OfY9Mmp4tMfUTcapqdxe2+pXL/ALlLK48sxxLkLGHHDZJBIXPOOMg0OjBe85afqdPso8vO3o/zO7qA3cRO2ImZ/wC7Fg4+p6D8SK870/S7zUPEGp26a7rX2CzRVaT+0HBSTGSDkAtj0wPTNa+nC9guDo1hm/ht4QzyXrnEbuxYbsH5htxgc4PcUq9GNKyUrvTp3JlTS2Z00dyyvJJcTptUYKRKSqHPdsct044+lXXYIjOxwqjJPtXMabqWqSteWFtZWYubeba8wZhAMj+6fmzxjA49/W/4ev7m+hvBdKvmwXLxM6MShIxnaDyAPQ1zkuDRznxDn8yDQVvFeDRn1BWvZSnzRbfuZzkBSeuR6Vl+MdX0rWdQ0fw/p00V493fxvcXKNu2Fc7fm6AnB4xjGcV6RJax3lr5N/BBOp+8jJuQ/gfaqqeH9IiWBYNNtIFgmE0YigRQrjjIwOD716WHxlOnGHMneF7W2u+vqv0Ri4tnP+JbLUNUuNNsLiOWS3ku1M5t422xqBnIb06ZJHHautS2hTpGCcglm+ZiR0yTyalorgc7xUexs53io9jkfAqLdW+r6nIo868vZBKo+58vAwPx5rT0G3lF5q95PC8Mk115aqVKqY4xtRgD6jv09K1be1t7OMx20EUCFixWJAoJPU4HepqqvUVSo5ocp3ba6mH4eikit769u43hmnuHd1ZSAAOAQDzjApvhe2l/ssXlwssNxdTy3EsRBUBmOOh5xgAjPrW9RWRLle5//9k=",
    "Ticket": ""
     }
    }
    *
    * Set-Cookie: SLBServerPool78=0000.66.78.184.1751085999; Expires=Sat, 28-Jun-; Path=/
    * Set-Cookie: _cap_id=9LyKRp1lbwELzZM12%2Bavu36oqR0kkaS9588DOUTSQSjmAT63quSVLA%3D%3D; expires=Sat, 28 Jun 2025 ; path=/
    *
    * */

}
