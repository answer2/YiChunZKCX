package dev.answer.yichunzkcx;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dev.answer.yichunzkcx.activity.BaseActivity;
import dev.answer.yichunzkcx.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

  private BottomNavigationView bottomNavigationView;

  private HomeFragment homeFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    bottomNavigationView = findViewById(R.id.navigation_bar_view);

    homeFragment = new HomeFragment();

    setCurrentFragment(homeFragment);
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
          .append("由宜春市政府提供网站\n")
          .append("“初中学考成绩查询”获取的Api\n\n")
          .append("数据提供：宜春市教育体育局\n")
          .append("技术支持：宜春市大数据发展管理局\n\n")
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
          "初中学考成绩查询",
          primaryColor,
          "https://zkchaxun.yichun.gov.cn/");

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

  public void setCurrentFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.content_frame, fragment);
    fragmentTransaction.commit();
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
}
