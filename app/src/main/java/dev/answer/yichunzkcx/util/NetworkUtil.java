package dev.answer.yichunzkcx.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import dev.answer.yichunzkcx.MainApplication;

public class NetworkUtil {
   public boolean isNetworkAvailable() {
        Context context = MainApplication.getContext();
   if(Build.VERSION.SDK_INT < 29){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }else{
        return isNetworkAvailable_SDK29(context);
    }
}

    public boolean isNetworkAvailable_SDK29(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    Network network = connectivityManager.getActiveNetwork();
    if (network == null) {
        return false;
    }

    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
    return networkCapabilities != null &&
            (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
             networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
             networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
}


}
