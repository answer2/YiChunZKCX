package dev.answer.yichunzkcx.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.network.JXEduApi;
import dev.answer.yichunzkcx.util.HttpUtil;

public class EnrollQueryFragment extends BaseFragment {

  private JXEduApi api;

  private TextInputEditText nameEdit;
  private TextInputEditText numberEdit;
  private TextInputEditText codeEdit;

  private ImageView codeImage;

  private MaterialButton login_button;

  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    View parentView = super.loadRootView(inflater, container, savedInstanceState);
    try {
      initBar();

      nameEdit = findViewById(R.id.name_textInput);
      numberEdit = findViewById(R.id.number_textInput);
      codeEdit = findViewById(R.id.code_textInput);
      codeImage = findViewById(R.id.code_image);
      login_button = findViewById(R.id.login_button);

      // init util
      api = new JXEduApi(getActivity());
      api.setJXEduImageView(codeImage);
      api.QueryJXEduCode();

      codeImage.setOnClickListener(view -> renewed());

      login_button.setOnClickListener(view -> login());

    } catch (Exception error) {
      error.printStackTrace();
      toast(error.toString());
    }
    return parentView;
  }

  public void login() {
    try {
      String name = removeSpaces(nameEdit);
      String number = removeSpaces(numberEdit);
      String code = removeSpaces(codeEdit);

      if (TextUtils.isEmpty(nameEdit.getText().toString())) {
        nameEdit.setError("请输入报考号");
      } else if (TextUtils.isEmpty(numberEdit.getText().toString())) {
        numberEdit.setError("请输入密码");
      } else if (TextUtils.isEmpty(codeEdit.getText().toString())) {
        codeEdit.setError("请输入验证码");
      } else {
        api.QueryJXEduLogin(name, number, codeEdit.getText().toString());
      }

      codeEdit.setText("");
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  public void renewed() {
    try {
      api.QueryJXEduCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
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
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_enroll;
  }

  @Override
  public String getFragmentName() {
    // TODO: Implement this method
    return "录取查询";
  }
}
