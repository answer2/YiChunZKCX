package dev.answer.yichunzkcx.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import dev.answer.yichunzkcx.R;

import dev.answer.yichunzkcx.databinding.LayoutZKGradeQueryBinding;
import dev.answer.yichunzkcx.network.YiChunZkApi;
import dev.answer.yichunzkcx.thread.CountdownRunnable;
import dev.answer.yichunzkcx.util.PropertiesUtil;

public class ZKGradeQuery extends BaseActivity {

  private LayoutZKGradeQueryBinding binding;

  private YiChunZkApi util;

  public PropertiesUtil propertiesUtil;
    
    /*
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
                intent.putExtra("bean", gradeResponse);
                startActivity(intent);
              });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = LayoutZKGradeQueryBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    try {
      propertiesUtil = new PropertiesUtil(this, "/history", "/UserInfo.yichun");
            
      CountdownRunnable countdownRunnable = new CountdownRunnable(binding.Countdown);
      new Thread(countdownRunnable).start(); // 在后台线程中开始倒计时任务

      // init http util
      util = new YiChunZkApi(this);
      util.setImageView(binding.codeImage);
      util.QueryCode();

    } catch (Exception e) {

    }
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
      String name = removeSpaces(binding.nameTextInput);
      String number = removeSpaces(binding.numberTextInput);
      String code = removeSpaces(binding.codeTextInput);

      if (TextUtils.isEmpty(binding.nameTextInput.getText().toString())) {
        binding.nameTextInput.setError("请输入姓名");
      } else if (TextUtils.isEmpty(binding.numberTextInput.getText().toString())) {
        binding.numberTextInput.setError("请输入准考证号");
      } else if (TextUtils.isEmpty(binding.codeTextInput.getText().toString())) {
        binding.codeTextInput.setError("请输入验证码");
      } else {
        util.QueryGrade(name, number, code);
      }
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  private void initHistory() {}

  public String removeSpaces(TextInputEditText input) {
    return removeSpaces(input.getText().toString());
  }

  private String removeSpaces(String input) {
    if (input == null) {
      return null;
    }

    return input.replaceAll("\\s", "");
  }

  @Override
  public boolean isHasToolbar() {
    // TODO: Implement this method
    return true;
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return "中考成绩查询";
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.layout_z_k_grade_query;
  }
}
