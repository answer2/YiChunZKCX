package dev.answer.yichunzkcx.network;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import dev.answer.yichunzkcx.network.networkManager;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class networkManager {
    
    private static networkManager manager;
    private static Retrofit retrofit;
    
    private Gson gson;
    
    private networkManager(){
        this.gson = new Gson();
        
    }
    
    public static networkManager get(){
                if (manager == null) {
            synchronized (networkManager.class) {
                if (manager == null) {
                    manager = new networkManager();
                }
            }
        }
        return manager;
    }
    
    
    public Gson getGson() {
    	return this.gson;
    }
    
    //双重检查法获取实例对象
    public static <T> T getService(String baseUrl,Class<T> serviceClass){
            if(retrofit == null){
                synchronized (networkManager.class){
                    if(retrofit == null){
                    
                    // 创建OkHttpClient实例
OkHttpClient.Builder builder = new OkHttpClient.Builder();
// 添加HttpLoggingInterceptor拦截器
HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
    @Override
    public void log(String message) {
                                
        Log.d("Retrofit", message);
    }
});
loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
builder.addInterceptor(loggingInterceptor);
                    
                        retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .client(builder.build())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }
                }
            }
            return retrofit.create(serviceClass);
    }
    
    
    public static String getCookies(Response response){
         if (response.isSuccessful()) {
                            Headers headers_ = response.headers();
                            //List<Cookie> cookies_ = Cookie.parseAll(request_code.url(), headers_);
                            List<String> cookies = headers_.values("Set-Cookie");
                            String content = "";
                            if (cookies.size() > 0) {
                              for (String cookieStr : cookies) {
                                String session = cookieStr;
                                if (!TextUtils.isEmpty(session)) {
                                  int size = session.length();
                                  int i = session.indexOf(";");
                                  if (i < size && i >= 0) {
                                    String result = session.substring(0, i);
                                    content += result + "; ";
                                  }
                                }
                              }
                            }
            return content;
            }
        return "";
    }
}
