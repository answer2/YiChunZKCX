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

public class GradeActivity extends BaseActivity {

  private GradeResponse bean;

  // info
  private TextView grade_name;
  private TextView grade_number;
  private TextView grade_school;
  // grade
  private TextView grade_text_yw;
  private TextView grade_text_sx;
  private TextView grade_text_yy;
  private TextView grade_text_zs;
  private TextView grade_text_wl;
  private TextView grade_text_hx;
  private TextView grade_text_sd;
  private TextView grade_text_ty;
  private TextView grade_text_sy;
  private TextView grade_text_jf;
  private TextView grade_text_zf;

  public PropertiesUtil propertiesUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
      // history
      File history_file = new File(getDataDir().toString() + "/history");
      if (!history_file.exists()) history_file.mkdirs();
      File history_properties = new File(history_file.toString() + "/UserInfo.yichun");
      if (!history_properties.exists()) history_properties.createNewFile();

      propertiesUtil = new PropertiesUtil(history_properties);

      // init view

      // info
      grade_name = findViewById(R.id.grade_name);
      grade_number = findViewById(R.id.grade_number);
      grade_school = findViewById(R.id.grade_school);
      // grade
      grade_text_yw = findViewById(R.id.grade_text_yw);
      grade_text_sx = findViewById(R.id.grade_text_sx);
      grade_text_yy = findViewById(R.id.grade_text_yy);
      grade_text_zs = findViewById(R.id.grade_text_zs);
      grade_text_wl = findViewById(R.id.grade_text_wl);
      grade_text_hx = findViewById(R.id.grade_text_hx);
      grade_text_sd = findViewById(R.id.grade_text_sd);
      grade_text_ty = findViewById(R.id.grade_text_ty);
      grade_text_sy = findViewById(R.id.grade_text_sy);
      grade_text_jf = findViewById(R.id.grade_text_jf);
      grade_text_zf = findViewById(R.id.grade_text_zf);

      this.bean = (GradeResponse) getIntent().getSerializableExtra("bean");
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
          setData(grade_name, "学生姓名: " + data.getXm1());
          setData(grade_number, "准考证号: " + data.getZkzh());

          setData(grade_school, "学校名称: " + "");
          // grade
          setGrade(grade_text_yw, data.getYw());
          setGrade(grade_text_sx, data.getSx());
          setGrade(grade_text_yy, data.getYy());
          setGrade(grade_text_zs, data.getZs());
          setGrade(grade_text_wl, data.getWl());
          setGrade(grade_text_hx, data.getHx());
          setGrade(grade_text_sd, data.getSd());
          setGrade(grade_text_ty, data.getTy());
          setGrade(grade_text_sy, data.getSycz());
          setGrade(grade_text_jf, data.getJf());
          setGrade(grade_text_zf, data.getZf());

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
    return "中考成绩";
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
