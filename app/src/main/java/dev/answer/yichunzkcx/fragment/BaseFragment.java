package dev.answer.yichunzkcx.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.appbar.MaterialToolbar;
import dev.answer.yichunzkcx.AppConfig;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.util.ThemeUtils;

public abstract class BaseFragment extends Fragment {

  private View mainView;

  public BaseFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return loadRootView(inflater, container, savedInstanceState);
  }

  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    int resID = getRootViewResID();
    mainView  = inflater.inflate(resID, container, false);
    return mainView;
  }

  public void initBar() {
    boolean isDarkMode = ThemeUtils.isSystemInDarkTheme(getActivity());
    WindowCompat.setDecorFitsSystemWindows(getActivity().getWindow(), false);
    ThemeUtils.statusBarColor(getActivity(), Color.TRANSPARENT, isDarkMode);
    MaterialToolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(getFragmentName());
    ((AppCompatActivity) (getActivity())).setSupportActionBar(toolbar);
  }

  public <T extends View> T findViewById(int id) {
    return mainView.findViewById(id);
  }

  public void toast(String message) {
    getActivity()
        .runOnUiThread(
            () -> {
              Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            });
  }

  public void delayed(Runnable able, int time) {
    new Handler(Looper.getMainLooper()).postDelayed(able, time);
  }

  public String getFragmentName() {
    return AppConfig.app_name;
  }
    
    public void initBinding(ViewBinding binding){
        mainView = binding.getRoot();
    }

  protected abstract int getRootViewResID();
}
