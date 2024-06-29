package dev.answer.yichunzkcx;
import android.os.Build;

public class AppConfig {
    
    public static final boolean isSupport = Build.VERSION.SDK_INT < 34;
    
    public static final int year = 2024;
    
    public static final String endTime_str = "2024-07-02 09:00:00";
    
    public static final String app_name = year + "宜春中考成绩查询";
    
    public static final String app_version = "1.0.3-bata";
}
