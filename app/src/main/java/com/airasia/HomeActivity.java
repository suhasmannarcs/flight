package com.airasia;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.airasia.databinding.ActivityHomeBinding;
import com.airasia.utils.AppConstants;
import com.airasia.utils.CommonUtils;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        navigateToFlightSearchFragment();
    }

    private void navigateToFlightSearchFragment() {
        String json = CommonUtils.fetchFileFromAssets(this,"locations.json");
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BundleConstants.LOCATION, json);
        FlightSearchFragment flightSearchFragment = FlightSearchFragment.getInstance(bundle);
        addFragment(flightSearchFragment);
    }

    protected void addFragment(Fragment fragment) {
        FragmentManager fragment1 = getSupportFragmentManager();
        FragmentTransaction transaction = fragment1.beginTransaction();
        transaction.add(mBinding.fragContainer.getId(),fragment,fragment.getClass().getSimpleName());
        transaction.commitAllowingStateLoss();
    }

    protected void removeFragment(Fragment fragment) {
        FragmentManager fragment1 = getSupportFragmentManager();
        FragmentTransaction transaction = fragment1.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }
}
