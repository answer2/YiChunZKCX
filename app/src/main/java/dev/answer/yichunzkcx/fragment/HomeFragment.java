package dev.answer.yichunzkcx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.activity.DevActivity;
import dev.answer.yichunzkcx.thread.CountdownRunnable;
import dev.answer.yichunzkcx.util.HttpUtil;

public class HomeFragment extends BaseFragment {

  private View parentView;

  private MaterialToolbar toolbar;
  private TextView countdownTextView;

  private TextInputEditText nameEdit;
  private TextInputEditText numberEdit;
  private TextInputEditText codeEdit;

  private ImageView codeImage;

  private HttpUtil util;

  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method

    initBar();

    parentView = super.loadRootView(inflater, container, savedInstanceState);

    try {

      // init View
      countdownTextView = findViewById(R.id.Countdown);
      nameEdit = findViewById(R.id.name_textInput);
      numberEdit = findViewById(R.id.number_textInput);
      codeEdit = findViewById(R.id.code_textInput);
      codeImage = findViewById(R.id.code_image);

      // init http util
      util = new HttpUtil(getActivity());
      util.setImageView(codeImage);
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
      Log.d("HomeFragment", error.toString());
    }

    return parentView;
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
    Intent intent = new Intent(getActivity(), DevActivity.class);
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
  public void onDestroy() {
    super.onDestroy();
    // TODO: Implement this method
    CountdownRunnable countdownRunnable = new CountdownRunnable(countdownTextView);
    new Thread(countdownRunnable).start(); // 在后台线程中开始倒计时任务
  }

  @Override
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_query;
  }
}
