package dev.answer.yichunzkcx;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import dev.answer.yichunzkcx.AppConfig;
import dev.answer.yichunzkcx.activity.BaseActivity;
import dev.answer.yichunzkcx.activity.DevActivity;
import dev.answer.yichunzkcx.thread.CountdownRunnable;
import dev.answer.yichunzkcx.util.HttpUtil;

public class MainActivity extends BaseActivity {

  private MaterialToolbar toolbar;
  private TextView countdownTextView;

  private TextInputEditText nameEdit;
  private TextInputEditText numberEdit;
  private TextInputEditText codeEdit;

  private ImageView codeImage;

  private HttpUtil util;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      // init View
      countdownTextView = findViewById(R.id.Countdown);
      nameEdit = findViewById(R.id.name_textInput);
      numberEdit = findViewById(R.id.number_textInput);
      codeEdit = findViewById(R.id.code_textInput);
      codeImage = findViewById(R.id.code_image);

      // init http util
      util = new HttpUtil(this);
      util.setImageView(codeImage);
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
      Log.d("MainActivity", error.toString());
    }
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return AppConfig.app_name;
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.activity_main;
  }

  public void renewed(View view) {
    try {
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  public void query(View view) {
    try {
      String name = removeSpaces(nameEdit);
      String number = removeSpaces(numberEdit);
      String code = removeSpaces(codeEdit);

      if (TextUtils.isEmpty(nameEdit.getText().toString())) {
        nameEdit.setError("请输入姓名");
      } else if (TextUtils.isEmpty(numberEdit.getText().toString())) {
        numberEdit.setError("请输入准考证号");
      } else if (TextUtils.isEmpty(codeEdit.getText().toString())) {
        codeEdit.setError("请输入验证码");
      } else {
        util.QueryGrade(name, number, code);
      }
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  public void dev(View view) {
    Intent intent = new Intent(mActivity, DevActivity.class);
    startActivity(intent);
  }

  public String removeSpaces(TextInputEditText input) {
    return removeSpaces(input.getText().toString());
  }

  public String removeSpaces(String input) {
    if (input == null) {
      return null;
    }

    return input.replaceAll("\\s", "");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.about) {
      TypedValue typedValue = new TypedValue();
      getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
      int primaryColor = typedValue.data;

      SpannableString spannableString =
          new SpannableString(
              "本软件是由AnswerDev(2903536884)开发\n由宜春市政府提供网站\n“初中学考成绩查询”获取的Api\n\n数据提供：宜春市教育体育局\n技术支持：宜春市大数据发展管理局\n\n仅用于学习讨论并不涉及商业和违法行为\n如有侵权我将会停用应用");
      ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(primaryColor);
      ClickableSpan clickableSpan1 =
          new ClickableSpan() {
            @Override
            public void onClick(View widget) {
              try {
                Uri uri = Uri.parse("https://github.com/answer2");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
              super.updateDrawState(ds);
              ds.setUnderlineText(true);
              ds.setColor(primaryColor);
            }
          };
      spannableString.setSpan(colorSpan1, 5, 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      spannableString.setSpan(clickableSpan1, 5, 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      UnderlineSpan underlineSpan = new UnderlineSpan();
      spannableString.setSpan(underlineSpan, 5, 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

      ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(primaryColor);
      ClickableSpan clickableSpan2 =
          new ClickableSpan() {
            @Override
            public void onClick(View widget) {
              Uri uri = Uri.parse("https://zkchaxun.yichun.gov.cn/");
              Intent intent = new Intent(Intent.ACTION_VIEW, uri);
              startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
              super.updateDrawState(ds);
              ds.setUnderlineText(true);
              ds.setColor(primaryColor);
            }
          };

      spannableString.setSpan(colorSpan2, 40, 50, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      spannableString.setSpan(clickableSpan2, 40, 50, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      UnderlineSpan underlineSpan2 = new UnderlineSpan();
      spannableString.setSpan(underlineSpan2, 41, 48, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

      LayoutInflater inflater = LayoutInflater.from(mActivity);
      View view = inflater.inflate(R.layout.dialog_about, null);

      TextView massage_Text = view.findViewById(R.id.dialog_app_massage);
      massage_Text.setText(spannableString);
      massage_Text.setMovementMethod(LinkMovementMethod.getInstance());
      massage_Text.setHighlightColor(Color.TRANSPARENT);

      MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(mActivity);
      dialog.setView(view).create().show();

      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  // 倒计时处理
  @Override
  protected void onResume() {
    super.onResume();

    CountdownRunnable countdownRunnable = new CountdownRunnable(countdownTextView);
    new Thread(countdownRunnable).start(); // 在后台线程中开始倒计时任务
  }
}
