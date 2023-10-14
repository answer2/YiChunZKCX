package dev.answer.yichunzkcx.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import dev.answer.yichunzkcx.activity.EnrollSchoolActivity;
import dev.answer.yichunzkcx.activity.GradeActivity;
import dev.answer.yichunzkcx.bean.CaptchaResponse;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.queryApi;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class HttpUtil {

  private OkHttpClient client;
  private Gson gson;
  private Activity mActivity;

  public HttpUtil(Activity activity) {
    // init
    this.mActivity = activity;
    // 创建自定义的TrustManager

    TrustManager[] trustAllCerts =
        new TrustManager[] {
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[0];
            }
          }
        };

    try {

      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

      HostnameVerifier hostnameVerifier = (hostname, session) -> true;

      this.client =
          new OkHttpClient.Builder()
              .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
              .hostnameVerifier(hostnameVerifier)
              .build();
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.gson = new Gson();
  }

  private String jxeduCookie;
  private String jxeduCookie_new;
  private Bitmap jxeduCodeImage;
  private ImageView JxeduImageView;
  private String enrollSchool;

  

  // 请求验证码
  private Drawable codeImage;
  private ImageView imageView;
  private String currentCaptchaId = "";
  private CaptchaResponse captchaResponse;
  private GradeResponse gradeResponse;
  private String responseData;

  private Runnable upDataRunnable;
  private Runnable failRunnable;

  public void QueryCode() {
    currentCaptchaId = "";
    Request request =
        new Request.Builder()
            .url(queryApi.getCaptchaAPI())
            .post(RequestBody.create(MediaType.parse("application/json"), ""))
            .build();

    client
        .newCall(request)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                toast(e.toString());
              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                  String responseData = response.body().string();
                  captchaResponse = gson.fromJson(responseData, CaptchaResponse.class);
                  currentCaptchaId = captchaResponse.getUuid();
                  // 在主线程中更新 UI
                  mActivity.runOnUiThread(
                      () -> {
                        codeImage =
                            ImageUtil.base64ToDrawable(captchaResponse.getImage(), mActivity);
                        imageView.setImageDrawable(codeImage);
                      });
                }
              }
            });
  }

  // 获取成绩
  public void QueryGrade(String name, String number, String code) {
    new Thread(
            () -> {
              // 创建请求参数的对象
              RequestBody requestBody =
                  new FormBody.Builder()
                      .add("xm1", name)
                      .add("zkzh", number)
                      .add("captchaId", currentCaptchaId)
                      .add("captchaContent", code)
                      .build();

              // 创建请求对象
              Request request =
                  new Request.Builder().url(queryApi.getGradeApi()).post(requestBody).build();

              // 发送请求
              try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                  responseData = response.body().string();
                  gradeResponse = gson.fromJson(responseData, GradeResponse.class);
                  if (gradeResponse.getCode() != 200) {
                    toast(gradeResponse.getMsg());
                    if (upDataRunnable != null) mActivity.runOnUiThread(failRunnable);
                  } else {
                    if (upDataRunnable == null) {
                      toast("正在加载，请稍等");
                      Intent intent = new Intent(mActivity, GradeActivity.class);
                      intent.putExtra("bean", (Serializable) gradeResponse);
                      mActivity.startActivity(intent);
                    } else {
                      mActivity.runOnUiThread(upDataRunnable);
                    }
                  }
                } else {
                  toast("Request failed");
                }
              } catch (Exception e) {
                toast(e.toString());
                e.printStackTrace();
              }
            })
        .start();
  }

  public void setUpDataRunnable(Runnable able) {
    this.upDataRunnable = able;
  }

  public void setFailRunnable(Runnable able) {
    this.failRunnable = able;
  }

  public String getResponseData() {
    return this.responseData;
  }

  public GradeResponse getGradeResponse() {
    return this.gradeResponse;
  }

  public void setImageView(ImageView view) {
    this.imageView = view;
  }

  public CaptchaResponse getCaptchaResponse() {
    return this.captchaResponse;
  }

  public Drawable getDrawable() {
    return this.codeImage;
  }

  private void toast(String massage) {
    mActivity.runOnUiThread(
        () -> {
          Toast.makeText(mActivity, massage, Toast.LENGTH_LONG).show();
        });
  }
}
