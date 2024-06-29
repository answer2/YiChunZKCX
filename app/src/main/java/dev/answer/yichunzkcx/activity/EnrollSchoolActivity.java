package dev.answer.yichunzkcx.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import dev.answer.yichunzkcx.R;

public class EnrollSchoolActivity extends BaseActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView enrollschool_text = findViewById(R.id.enrollschool_text);

    if (getIntent() != null && enrollschool_text != null) {
      String message = getIntent().getStringExtra("enroll");
      if (!TextUtils.isEmpty(message)) {
        enrollschool_text.setText(message);
      }
    }
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return "录取查询";
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.activity_enroll_school;
  }

  @Override
  public boolean getShowBackButton() {
    // TODO: Implement this method
    return true;
  }
}
