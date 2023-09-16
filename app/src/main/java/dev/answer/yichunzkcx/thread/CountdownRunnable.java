package dev.answer.yichunzkcx.thread;

import dev.answer.yichunzkcx.AppConfig;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.util.Locale;
import java.util.Date;

public class CountdownRunnable implements Runnable {
    
    private final Date endTime = null;
    
    private final Handler handler = new Handler(Looper.getMainLooper());

    private TextView countdownTextView;
    
    public CountdownRunnable(TextView countdownTextView){
        this.countdownTextView=countdownTextView;
    }
    
        @Override
        public void run() {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            
            //set end time
            try {
                endTime = dateFormat.parse(AppConfig.endTime_str);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (endTime != null) {
                while (true) {
                    long now = System.currentTimeMillis();
                    long leftTime = endTime.getTime() - now;

                    if (leftTime >= 0) {
                        long day = leftTime / (1000 * 60 * 60 * 24);
                        long hour = (leftTime / (1000 * 60 * 60)) % 24;
                        long min = (leftTime / (1000 * 60)) % 60;
                        long sec = (leftTime / 1000) % 60;

                        String dayText = formatNumber(day) + "天";
                        String hourText = formatNumber(hour) + "时";
                        String minText = formatNumber(min) + "分";
                        String secText = formatNumber(sec) + "秒";

                        // 更新UI
                        final String countdownText =
                                "剩余 " + dayText + " " + hourText + " " + minText + " " + secText;
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        countdownTextView.setText(countdownText);
                                    }
                                });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 倒计时结束，进行相关操作
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        // 移除视图或者进行其他操作
                                         countdownTextView.setText("倒计时结束，您可以正常查分");
                                    }
                                });
                        break;
                    }
                }
            }
        }
    
    private String formatNumber(long num) {
            return num < 10 ? "0" + num : String.valueOf(num);
        }

}