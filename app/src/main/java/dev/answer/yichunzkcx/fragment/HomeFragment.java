package dev.answer.yichunzkcx.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.activity.GradeActivity;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.network.YiChunZkApi;
import dev.answer.yichunzkcx.thread.CountdownRunnable;
import dev.answer.yichunzkcx.util.PropertiesUtil;
import java.io.File;
import java.io.Serializable;
import java.util.Set;
import org.json.JSONObject;

public class HomeFragment extends BaseFragment {

  private View parentView;

  private MaterialToolbar toolbar;
  public TextView countdownTextView;

  private TextInputEditText nameEdit;
  private TextInputEditText numberEdit;
  private TextInputEditText codeEdit;

  private ImageView codeImage;

  private MaterialButton query_button;
  private MaterialButton dev_button;

  private LinearLayout history_list;

  private YiChunZkApi util;

  public PropertiesUtil propertiesUtil;

  
  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    parentView = super.loadRootView(inflater, container, savedInstanceState);

    try {
      initBar();

      // init View
      countdownTextView = findViewById(R.id.Countdown);
      nameEdit = findViewById(R.id.name_textInput);
      numberEdit = findViewById(R.id.number_textInput);
      codeEdit = findViewById(R.id.code_textInput);
      codeImage = findViewById(R.id.code_image);
      query_button = findViewById(R.id.query_button);
      history_list = findViewById(R.id.history_list);

      

      // history
      File history_file = new File(getActivity().getDataDir().toString() + "/history");
      if (!history_file.exists()) history_file.mkdirs();
      File history_properties = new File(history_file.toString() + "/UserInfo.yichun");
      if (!history_properties.exists()) history_properties.createNewFile();

      propertiesUtil = new PropertiesUtil(history_properties);

      try {
        Set<String> keys = propertiesUtil.keySet();
        Activity activity = getActivity();
        Gson gson = new Gson();
                
        for (String key : keys) {
          View history_layout = View.inflate(getActivity(), R.layout.layout_history, null);
          TextView name = history_layout.findViewById(R.id.grade_name);
          TextView number = history_layout.findViewById(R.id.grade_number);
                    MaterialCardView card = history_layout.findViewById(R.id.card_info);
          String json = propertiesUtil.getString(key);
          
          GradeResponse gradeResponse = gson.fromJson(json, GradeResponse.class);

          name.setText(key);
          number.setText(gradeResponse.getData().getXm1());
          history_list.addView(history_layout);
          card.setOnClickListener(
              v -> {
                toast("正在加载，请稍等");
                Intent intent = new Intent(activity, GradeActivity.class);
                intent.putExtra("bean", (Serializable) gradeResponse);
                startActivity(intent);
              });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      CountdownRunnable countdownRunnable = new CountdownRunnable(countdownTextView);
      new Thread(countdownRunnable).start(); // 在后台线程中开始倒计时任务

      codeImage.setOnClickListener(view -> renewed());
      query_button.setOnClickListener(view -> query());

      // init http util
      util = new YiChunZkApi(getActivity());
      util.setImageView(codeImage);
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
      Log.d("HomeFragment", error.toString());
    }

    return parentView;
  }

  public void renewed() {
    try {
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  public void query() {
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
  public String getFragmentName() {
    // TODO: Implement this method
    return "成绩查询";
  }

  @Override
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_query;
  }
}
