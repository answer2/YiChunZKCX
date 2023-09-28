package dev.answer.yichunzkcx.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import android.view.Gravity;

public abstract class BaseFragment extends Fragment {
    
    private View mainView;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return loadRootView(inflater,container,savedInstanceState);
    }

    public View loadRootView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState){
        int resID = getRootViewResID();
        mainView = inflater.inflate(resID, container, false);
        return mainView;
    }
    
    public <T extends View> T findViewById(int id) {
    	return mainView.findViewById(id);
    }
    
    public void toast(String message) {
    	Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    protected abstract int getRootViewResID();
}
