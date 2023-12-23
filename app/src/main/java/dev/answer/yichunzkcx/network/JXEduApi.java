package dev.answer.yichunzkcx.network;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;
import dev.answer.yichunzkcx.MainApplication;
import dev.answer.yichunzkcx.activity.EnrollSchoolActivity;
import dev.answer.yichunzkcx.network.networkManager;
import dev.answer.yichunzkcx.network.service.JxEduService;
import dev.answer.yichunzkcx.queryApi;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JXEduApi {
    private Activity mActivity;
    
    private String jxeduCookie;
    private Bitmap jxeduCodeImage;
    private ImageView JxeduImageView;
    private String enrollSchool;
    private int retryCount = 0;
    
    private JxEduService service;
    
    public JXEduApi(Activity activity) {
        this.mActivity = activity;
        this.service = networkManager.getService("https://zkzz.jxedu.gov.cn/", JxEduService.class);
    }
    
    public String getEnrollSchool() {
        return this.enrollSchool;
    }

    public void setJXEduImageView(ImageView view) {
        this.JxeduImageView = view;
    }
    
  public void QueryJXEduCode() {
        
              Call<ResponseBody> call = service.queryCode(""+new Date().getTime());

              call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable e) {
                          // 处理请求失败情况
                          toast("请求失败-JXEdu-" + e.toString());
                        }
                        
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          if (response.isSuccessful()) {
                        
                            
                            jxeduCookie = networkManager.getCookies(response);
                            InputStream inputStream = response.body().byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            jxeduCodeImage = bitmap;
                            
                            mActivity.runOnUiThread(()->{
                                JxeduImageView.setImageBitmap(bitmap);
                            });
                                
                          } else {
                            // 请求失败，处理错误情况
                            toast("请求失败-JXEdu");
                          }
                        }
                      });
  }

  public void QueryJXEduLogin(String username, String password, String code) {
                Call<ResponseBody> call = service.login(jxeduCookie, Math.random()+"", username, "", password,"", code, "https%253A%252F%252Fzkzz.jxedu.gov.cn%252Flogin%2521init.action");

                call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable e) {
                          // 处理请求失败情况
                          toast("请求失败-JXEdu-" + e.toString());
                        }
                        
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.isSuccessful()) {
                                  String responseText = response.body().string();
                                  if ("OK".equals(responseText)) {
                                        toast("获取成功，请稍等...");
                                        QueryEnrollSchool();
                                  } else {
                                        toast(responseText);
                                  }
                        
                                } else {
                                  toast("失败");
                                }
                                QueryJXEduCode();
              } catch (Exception e) {
                toast(e.toString());
                e.printStackTrace();
              }
        }
        });

                
               
         
  }

  public void QueryEnrollSchool() {
              try {
                
            Call<ResponseBody> call = service.enrollQuery(jxeduCookie);
            
                Response response = call.execute();
            String responseText = response.raw().body().string();
            
            System.out.println(responseText);
                // 处理响应结果
                if (response.isSuccessful()) {
                  
                  enrollSchool = responseText;

                  Intent intent = new Intent(MainApplication.getContext(), EnrollSchoolActivity.class);
                  intent.putExtra("enroll", enrollSchool);
                  MainApplication.getContext().startActivity(intent);
                }

              } catch (Exception e) {
                if (enrollSchool == null && retryCount < 3) {
                  QueryEnrollSchool();
                  toast("正在重试");
                  retryCount++;
                } else {
                  toast("重试失败-" + e.toString());
                }
                e.printStackTrace();
              }
           
  }
    
    private void toast(String message) {
    	Toast.makeText(MainApplication.getContext(), message, Toast.LENGTH_LONG);
    }
    
}
