package dev.answer.yichunzkcx.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dev.answer.yichunzkcx.R;

import dev.answer.yichunzkcx.databinding.LayoutZKGradeQueryBinding;

public class ZKGradeQuery extends BaseActivity {

  private LayoutZKGradeQueryBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = LayoutZKGradeQueryBinding.inflate(getLayoutInflater());
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
