package dev.answer.yichunzkcx.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import com.google.android.material.appbar.MaterialToolbar;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.util.PropertiesUtil;
import dev.answer.yichunzkcx.util.ThemeUtils;
import dev.answer.yichunzkcx.R;
import java.io.File;
import java.util.Optional;
import dev.answer.yichunzkcx.AppConfig;
import dev.answer.yichunzkcx.databinding.ActivityGradeBinding;

public class GradeActivity extends BaseActivity {
    
  private ActivityGradeBinding binding;
  private GradeResponse bean;
  public PropertiesUtil propertiesUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        
        binding = ActivityGradeBinding.inflate(getLayoutInflater());
        
    try {
            
      // history
      File history_file = new File(getDataDir().toString() + "/history");
      if (!history_file.exists()) history_file.mkdirs();
      File history_properties = new File(history_file.toString() + "/UserInfo.yichun");
      if (!history_properties.exists()) history_properties.createNewFile();

      propertiesUtil = new PropertiesUtil(history_properties);

      // init view
      this.bean =  getIntent().getParcelableExtra("bean", GradeResponse.class);
      if (bean != null) {
        if (bean.getCode() == 200) {
          GradeResponse.Data data = bean.getData();

          if (propertiesUtil.properties().containsKey(data.getZkzh())) {
            propertiesUtil.remove(data.getZkzh());
            propertiesUtil.setProperty(data.getZkzh(), bean.toString());
          } else {
            propertiesUtil.setProperty(data.getZkzh(), bean.toString());
          }
          propertiesUtil.store("updata");
          propertiesUtil.closeFileStream();

          // info
          setData(binding.gradeName, "学生姓名: " + data.getXm1());
          setData(binding.gradeNumber, "准考证号: " + data.getZkzh());

          setData(binding.gradeSchool, "学校名称: " + ( data.getXxmc() != null ? data.getXxmc() : "") );
          // grade
          setGrade(binding.gradeTextYw, data.getYw());
          setGrade(binding.gradeTextSx, data.getSx());
          setGrade(binding.gradeTextYy, data.getYy());
          setGrade(binding.gradeTextZs, data.getZs());
          setGrade(binding.gradeTextWl, data.getWl());
          setGrade(binding.gradeTextHx, data.getHx());
          setGrade(binding.gradeTextSd, data.getSd());
          setGrade(binding.gradeTextTy, data.getTy());
          setGrade(binding.gradeTextSy, data.getSycz());
          setGrade(binding.gradeTextJf, data.getJf());
          setGrade(binding.gradeTextZf, data.getZf());

        } else {
          toast("获取失败");
        }
      }

    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
      Log.d("MainActivity", error.toString());
    }
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return AppConfig.year +"中考成绩";
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.activity_grade;
  }

  @Override
  public boolean getShowBackButton() {
    // TODO: Implement this method
    return true;
  }

  private void setData(TextView view, String data) {
    if (isNotNull(data)) {
      view.setText(data);
    } else {
      view.setText("--");
    }
  }

  private void setGrade(TextView view, String data) {
    if (isNotNull(data)) {
      if (data.contains("强基") || data.contains("A")) {
        view.setText(data);
      } else {
        view.setText(data + "分");
      }
    } else {
      view.setText("--");
    }
  }

  public static <T> boolean isNotNull(T value) {
    return Optional.ofNullable(value).isPresent();
  }
}
