package dev.answer.yichunzkcx.network;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import dev.answer.yichunzkcx.MainApplication;
import dev.answer.yichunzkcx.activity.GradeActivity;
import dev.answer.yichunzkcx.bean.CaptchaResponse;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.queryApi;
import dev.answer.yichunzkcx.util.ImageUtil;
import java.io.IOException;
import java.io.Serializable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class YiChunZkApi {
  private OkHttpClient client;
  private Activity mActivity;
  private Gson gson;

  public YiChunZkApi(Activity activity) {
    this.mActivity = activity;
    this.gson = new Gson();
    this.client = new OkHttpClient();
  }

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
                  new Request.Builder()
                .url(queryApi.getGradeApi())
                .post(requestBody)
                .build();

              // 发送请求

              try {
                Response response = client.newCall(request).execute();

                mActivity.runOnUiThread(
                    () -> {
                      try {
                        if (response.isSuccessful()) {
                          responseData = response.body().string();
                          gradeResponse = gson.fromJson(responseData, GradeResponse.class);
                          if (gradeResponse.getCode() != 200) {
                            toast(gradeResponse.getMsg());
                            if (failRunnable != null) mActivity.runOnUiThread(failRunnable);
                          } else {
                            if (upDataRunnable == null) {
                              toast("正在加载，请稍等");
                              Intent intent = new Intent(mActivity, GradeActivity.class);
                              intent.putExtra("bean", gradeResponse);
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
                    });
              } catch (Exception e) {

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

  private void toast(String message) {
        mActivity.runOnUiThread(()->{
    Toast.makeText(MainApplication.getContext(), message, Toast.LENGTH_LONG).show();
            });
  }
}
