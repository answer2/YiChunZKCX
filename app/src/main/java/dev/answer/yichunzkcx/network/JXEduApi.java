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
import dev.answer.yichunzkcx.queryApi;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JXEduApi {
    private OkHttpClient client;
    private Activity mActivity;
    
    private String jxeduCookie;
    private String jxeduCookie_new;
    private Bitmap jxeduCodeImage;
    private ImageView JxeduImageView;
    private String enrollSchool;
    private int retryCount = 0;
    
    public JXEduApi(Activity activity) {
        this.mActivity = activity;
        this.client = new OkHttpClient();
    }
    
    public String getEnrollSchool() {
        return this.enrollSchool;
    }

    public void setJXEduImageView(ImageView view) {
        this.JxeduImageView = view;
    }
    
    public void QueryJXEduCookie() {
    new Thread(
            () -> {
              try {
                Request request = new Request.Builder().url(queryApi.getBaseJXeduApi()).build();

                try (Response response = client.newCall(request).execute()) {
                  // 获取响应的 cookie
                  jxeduCookie = response.header("Set-Cookie");
                  toast(jxeduCookie);
                }
              } catch (Exception e) {
                toast(e.toString());
                e.printStackTrace();
              }
            })
        .start();
  }

  public void QueryJXEduCode() {
    new Thread(() -> {
              Request request_code =
                  new Request.Builder()
                      .url(queryApi.getJxeduCodeApi() + "?d=" + new Date().getTime())
                      .build();

              client
                  .newCall(request_code)
                  .enqueue(
                      new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                          // 处理请求失败情况
                          toast("请求失败-JXEdu-" + e.toString());
                        }
                        
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                          if (response.isSuccessful()) {
                            Headers headers_ = response.headers();
                            List<Cookie> cookies_ = Cookie.parseAll(request_code.url(), headers_);
                            List<String> cookies = headers_.values("Set-Cookie");
                            String content = "";
                            if (cookies.size() > 0) {
                              for (String cookieStr : cookies) {
                                String session = cookieStr;
                                if (!TextUtils.isEmpty(session)) {
                                  int size = session.length();
                                  int i = session.indexOf(";");
                                  if (i < size && i >= 0) {
                                    String result = session.substring(0, i);
                                    content += result + "; ";
                                  }
                                }
                              }
                            }

                            jxeduCookie = content;
                                
                                
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
            })
        .start();
  }

  public void QueryJXEduLogin(String username, String password, String code) {
    new Thread(
            () -> {
              try {
                RequestBody requestBody =
                    new FormBody.Builder()
                        .add("object.username", username)
                        .add("password", "")
                        .add("object.amendpwd", password)
                        .add("object.remark", "")
                        .add("validateCode", code)
                        .add(
                            "loginUrl",
                            "https%253A%252F%252Fzkzz.jxedu.gov.cn%252Flogin%2521init.action")
                        .build();

                Request request =
                    new Request.Builder()
                        .url(queryApi.getJxeduLoginApi() + "?rand=" + Math.random())
                        .post(requestBody)
                        .addHeader("Cookie", jxeduCookie)
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                  String responseText = response.body().string();

                  if ("OK".equals(responseText)) {
                    toast("获取成功，请稍等...");
                    QueryEnrollSchool();
                  } else {
                    toast(responseText);
                  }

                } else {
                  // 请求失败，处理错误情况
                  toast("失败");
                }
                QueryJXEduCode();
              } catch (Exception e) {
                toast(e.toString());
                e.printStackTrace();
              }
            })
        .start();
  }

  public void QueryEnrollSchool() {
    new Thread(
            () -> {
              try {
                Request request =
                    new Request.Builder()
                        .url(queryApi.getJxeduEnrollQuery())
                        .addHeader("Cookie", jxeduCookie)
                        .build();

                Response response = client.newCall(request).execute();

                // 处理响应结果
                if (response.isSuccessful()) {
                  String responseText = response.body().string();
                  enrollSchool = responseText;

                  Intent intent = new Intent(MainApplication.getContext(), EnrollSchoolActivity.class);
                  intent.putExtra("enroll", enrollSchool);
                  MainApplication.getContext().startActivity(intent);

                  System.out.println(enrollSchool);
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
            })
        .start();
  }
    
    private void toast(String message) {
    	Toast.makeText(MainApplication.getContext(), message, Toast.LENGTH_LONG);
    }
    
}
