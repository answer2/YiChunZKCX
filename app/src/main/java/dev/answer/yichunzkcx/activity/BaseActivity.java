package dev.answer.yichunzkcx.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import com.google.android.material.appbar.MaterialToolbar;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.util.ThemeUtils;

public class BaseActivity extends AppCompatActivity {
    
    
    public Activity mActivity;

    public MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        try {
            setContentView(getActivityLayout());
            if(isHasToolbar()) initBar();
            if (getShowBackButton()) initBackButton();
        } catch (Throwable error) {
            error.printStackTrace();
            toast(error.toString());
            Log.d("MainActivity", error.toString());
        }
    }

    private void initBar() {
        boolean isDarkMode = ThemeUtils.isSystemInDarkTheme(mActivity);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ThemeUtils.statusBarColor(mActivity, Color.TRANSPARENT, isDarkMode);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getActivityName());
        setSupportActionBar(toolbar);
    }

    private void initBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }
    
    public boolean isHasToolbar() {
    	return true;
    }

    public boolean getShowBackButton() {
        return false;
    }

    public int getActivityLayout() {
        return 0;
    }

    public String getActivityName() {
        return "";
    }
    
    public void delayed(Runnable able, int time){
        new Handler(Looper.getMainLooper()).postDelayed(able,time); 
    }
    
    public void log(String massage) {
        Log.d(getClass().getSimpleName(),massage);
    }

    public void toast_log(String massage) {
        Log.d(getClass().getSimpleName(),massage);
        toast(massage);
    }

    public void toast(String massage) {
        Toast.makeText(mActivity, massage, Toast.LENGTH_LONG).show();
    }
}
