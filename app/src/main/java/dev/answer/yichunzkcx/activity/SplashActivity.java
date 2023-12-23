package dev.answer.yichunzkcx.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.textview.MaterialTextView;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.util.ProtocolUtil;

public class SplashActivity extends BaseActivity{
    
    private LinearLayout botton_background;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MaterialTextView text = findViewById(R.id.splash_protocol_text);
        botton_background = findViewById(R.id.botton_background);
        
        String context = ProtocolUtil.readTextFromAssets(this,"protocol/zh/PrivacyPolicy.txt");
        
        text.setText(ProtocolUtil.parseProtocol(context, this));
        
    }
    
    
    
    
  @Override
  public boolean isHasToolbar() {
    // TODO: Implement this method
    return true;
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return "隐私协议";
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.activity_splash;
  }
}
