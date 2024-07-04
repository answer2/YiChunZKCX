package dev.answer.yichunzkcx.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment {

  private FragmentHomeBinding binding;
  
  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View parentView = binding.getRoot();
    initBinding(binding);
        
    try {
      initBar();

    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
      Log.d("HomeFragment", error.toString());
    }

    return parentView;
  }

  @Override
  public String getFragmentName() {
    // TODO: Implement this method
    return "主页";
  }

  @Override
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_home;
  }
}
