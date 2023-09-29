package dev.answer.yichunzkcx.fragment;

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
import dev.answer.yichunzkcx.util.HttpUtil;

public class EnrollQueryFragment extends BaseFragment {

  private HttpUtil util;

  private TextInputEditText nameEdit;
  private TextInputEditText numberEdit;
  private TextInputEditText codeEdit;

  private ImageView codeImage;

  private MaterialButton login_button;

  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    try {
      MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("录取查询");

      nameEdit = findViewById(R.id.name_textInput);
      numberEdit = findViewById(R.id.number_textInput);
      codeEdit = findViewById(R.id.code_textInput);
      codeImage = findViewById(R.id.code_image);
      login_button = findViewById(R.id.login_button);

      // init util
      util = new HttpUtil(getActivity());
      util.setJXEduImageView(codeImage);
      util.QueryJXEduCookie();
      util.QueryJXEduCode();
            
      codeImage.setOnClickListener( view -> renewed());
            

    } catch (Exception error) {
      error.printStackTrace();
      toast(error.toString());
      StackTraceElement[] stackTrace = error.getStackTrace();
      if (stackTrace.length > 0) {
        // 获取第一个堆栈元素的行数
        int lineNumber = stackTrace[0].getLineNumber();
        toast("错误发生在第 " + lineNumber + " 行");
      }
      Log.d("Batch_Query", error.toString());
    }
    return super.loadRootView(inflater, container, savedInstanceState);
  }

  public void renewed() {
    try {
      util.QueryJXEduCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
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
