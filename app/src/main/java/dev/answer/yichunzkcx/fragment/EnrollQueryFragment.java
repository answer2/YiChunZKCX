package dev.answer.yichunzkcx.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

import dev.answer.yichunzkcx.R;

public class EnrollQueryFragment extends BaseFragment{
    
    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        
        return super.loadRootView(inflater, container, savedInstanceState);
    }
    
    @Override
    protected int getRootViewResID() {
        // TODO: Implement this method
        return R.layout.fragment_enroll;
    }
    
    @Override
    public String getFragmentName() {
        // TODO: Implement this method
        return "录取查询";
    }
    
    
}
