package dev.answer.yichunzkcx.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.activity.GradeActivity;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.databinding.FragmentHomeBinding;
import dev.answer.yichunzkcx.network.YiChunZkApi;
import dev.answer.yichunzkcx.thread.CountdownRunnable;
import dev.answer.yichunzkcx.util.PropertiesUtil;

public class HomeFragment extends BaseFragment {

  private FragmentHomeBinding binding;
  private PropertiesUtil propertiesUtil;
  private YiChunZkApi util;


  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View parentView = binding.getRoot();
    initBinding(binding);
        
    try {
      initBar();

      // history
      File history_file = new File(getActivity().getDataDir().toString() + "/history");
      if (!history_file.exists()) history_file.mkdirs();
      File history_properties = new File(history_file.toString() + "/UserInfo.yichun");
      if (!history_properties.exists()) history_properties.createNewFile();

      propertiesUtil = new PropertiesUtil(history_properties);

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
          binding.historyList.addView(history_layout);
          card.setOnClickListener(
                  v -> {
                    toast("正在加载，请稍等");
                    Intent intent = new Intent(activity, GradeActivity.class);
                    intent.putExtra("bean", (Serializable) gradeResponse);
                    startActivity(intent);
                  });
        }

      CountdownRunnable countdownRunnable = new CountdownRunnable(binding.Countdown);
      new Thread(countdownRunnable).start(); // 在后台线程中开始倒计时任务

      binding.codeImage.setOnClickListener(view -> renewed());
      binding.queryButton.setOnClickListener(view -> query());

      // init http util
      util = new YiChunZkApi(getActivity());
      util.setImageView(binding.codeImage);
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
      TextInputEditText nameEdit = binding.nameTextInput;
      String name = removeSpaces(nameEdit);
      TextInputEditText numberEdit = binding.numberTextInput;
      String number = removeSpaces(numberEdit);
      TextInputEditText codeEdit = binding.codeTextInput;
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
    return "主页";
  }

  @Override
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_home;
  }
}
