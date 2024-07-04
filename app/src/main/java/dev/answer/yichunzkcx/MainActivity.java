package dev.answer.yichunzkcx;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.os.Environment;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import dev.answer.yichunzkcx.activity.BaseActivity;
import dev.answer.yichunzkcx.fragment.BatchQueryFragment;
import dev.answer.yichunzkcx.fragment.EnrollQueryFragment;
import dev.answer.yichunzkcx.fragment.HomeFragment;
import dev.answer.yichunzkcx.thread.CountdownRunnable;

public class MainActivity extends BaseActivity {

  private BottomNavigationView bottomNavigationView;

  private HomeFragment homeFragment;
  private EnrollQueryFragment enrollQueryFragment;
  private BatchQueryFragment batchQueryFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {

      // 检查是否已经获得外部存储读取权限
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1024);
      }
      requestmanageexternalstorage_Permission();

      bottomNavigationView = findViewById(R.id.navigation_bar_view);

      homeFragment = new HomeFragment();
      enrollQueryFragment = new EnrollQueryFragment();
      batchQueryFragment = new BatchQueryFragment();

      setCurrentFragment(homeFragment);

      bottomNavigationView.setOnItemSelectedListener(
          item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_achievement) {
              setCurrentFragment(homeFragment);
              return true;
            } else if (itemId == R.id.menu_enroll) {
              setCurrentFragment(enrollQueryFragment);
              return true;
            } else if (itemId == R.id.menu_excel) {
              setCurrentFragment(batchQueryFragment);
              return true;
            }
            return false;
          });

    } catch (Exception error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  @Override
  public boolean isHasToolbar() {
    // TODO: Implement this method
    return false;
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.about) {

      // getColor
      TypedValue typedValue = new TypedValue();
      getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
      int primaryColor = typedValue.data;

      StringBuilder content = new StringBuilder();
      content
          .append("本软件是由AnswerDev(2903536884)开发\n")
          .append("感谢“江西省高中阶段学校招生电子化管理平台”和")
          .append("“初中学考成绩查询”政府网站的Api\n\n")
          .append("数据提供：宜春市教育体育局\n")
          .append("技术支持：宜春市大数据发展管理局 和 AnswerDev\n\n")
          .append("开源网站: github，gitee\n")
          .append("社交网站：QQ, Bilibili\n")
          .append("仅用于学习讨论并不涉及商业和违法行为\n")
          .append("如有侵权我将会停用应用");

      SpannableString spannableString = new SpannableString(content.toString());

      drawLink(
          spannableString,
          content.toString(),
          "AnswerDev",
          primaryColor,
          "https://github.com/answer2");

      drawLink(
          spannableString,
          content.toString(),
          "江西省高中阶段学校招生电子化管理平台",
          primaryColor,
          "https://zkzz.jxedu.gov.cn/");

      drawLink(
          spannableString,
          content.toString(),
          "初中学考成绩查询",
          primaryColor,
          "https://zkchaxun.yichun.gov.cn/");

      drawLink(
          spannableString,
          content.toString(),
          "github",
          primaryColor,
          "https://github.com/answer2/YiChunZKCX");

      drawLink(
          spannableString,
          content.toString(),
          "gitee",
          primaryColor,
          "https://gitee.com/hvfcc/YiChunZKCX");

      drawLink(
          spannableString,
          content.toString(),
          "QQ",
          primaryColor,
          "mqqwpa://im/chat?chat_type=wpa&uin=2903536884");

      drawLink(
          spannableString,
          content.toString(),
          "Bilibili",
          primaryColor,
          "https://space.bilibili.com/503749880");

      LayoutInflater inflater = LayoutInflater.from(mActivity);
      View view = inflater.inflate(R.layout.dialog_about, null);

      TextView massage_Text = view.findViewById(R.id.dialog_app_massage);
      massage_Text.setText(spannableString);
      massage_Text.setMovementMethod(LinkMovementMethod.getInstance());
      massage_Text.setHighlightColor(Color.TRANSPARENT);

      TextView version = view.findViewById(R.id.dialog_app_version);
      version.setText(AppConfig.app_version);

      MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(mActivity);
      dialog.setView(view).create().show();

      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void setCurrentFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.content_frame, fragment);
    fragmentTransaction.commit();
  }

  private void initAnimation() {
    // home
    homeFragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

    // forum
    enrollQueryFragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

    // plugin
    batchQueryFragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
  }

  public void drawLink(
      SpannableString spannableString,
      String content,
      String target,
      final int primaryColor,
      final String url) {
    int[] linkIndex = getIndex(content, target);

    ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(primaryColor);
    ClickableSpan clickableSpan1 =
        new ClickableSpan() {
          @Override
          public void onClick(View widget) {
            try {
              Uri uri = Uri.parse(url);
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
    spannableString.setSpan(
        colorSpan1, linkIndex[0], linkIndex[1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(
        clickableSpan1, linkIndex[0], linkIndex[1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    UnderlineSpan underlineSpan = new UnderlineSpan();
    spannableString.setSpan(
        underlineSpan, linkIndex[0], linkIndex[1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
  }

  public static int[] getIndex(String origin, String target) {
    int index = origin.lastIndexOf(target);
    return new int[] {index, index + target.length()};
  }

  private void requestmanageexternalstorage_Permission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      // 先判断有没有权限
      if (!Environment.isExternalStorageManager()) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
      }
    }
  }
}
