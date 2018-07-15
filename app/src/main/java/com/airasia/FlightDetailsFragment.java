package com.airasia;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airasia.databinding.FragmentFlightSearchDetailsBinding;
import com.airasia.models.FlightData;
import com.airasia.utils.AppConstants;
import com.airasia.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by techjini on 14/7/18.
 */

public class FlightDetailsFragment extends Fragment {

    private FragmentFlightSearchDetailsBinding mBinding;
    private String mSource;
    private String mDestination;
    private String mDepartureDate;
    private String mReturnDate;
    private String json;
    private boolean isOneWay;

    public static FlightDetailsFragment getInstance(Bundle bundle) {
        FlightDetailsFragment flightDetailsFragment = new FlightDetailsFragment();
        flightDetailsFragment.setArguments(bundle);
        return flightDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_search_details,container,false);
        init();
        return mBinding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mSource = bundle.getString(AppConstants.BundleConstants.SOURCE);
        mDestination = bundle.getString(AppConstants.BundleConstants.DESTINATION);
        mDepartureDate = bundle.getString(AppConstants.BundleConstants.DEPARTURE_DATE);
        mReturnDate = bundle.getString(AppConstants.BundleConstants.RETURN_DATE);
        isOneWay = bundle.getBoolean(AppConstants.BundleConstants.ONE_WAY_JOURNEY);
        json = CommonUtils.fetchFileFromAssets(getActivity(),"flight_details.json");
    }

    private void init() {
        mBinding.txtSource.setText(mSource);
        mBinding.txtDestination.setText(mDestination);
        mBinding.departureDate.setText(mDepartureDate);
        mBinding.returndatePv.setVisibility(TextUtils.isEmpty(mReturnDate) ? View.GONE : View.VISIBLE);
        mBinding.returnDate.setText(mReturnDate);
        mBinding.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).removeFragment(FlightDetailsFragment.this);
            }
        });
        setViewPager();
    }

    private void setViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("flight_details",json);
        bundle.putBoolean("isOneWay",isOneWay);
        adapter.addFragment(FlightDetailsListFragment.getInstance(bundle), "List");
        adapter.addFragment(FlightDetailsMapFragment.getInstance(bundle), "Map");
        mBinding.viewPager.setAdapter(adapter);
    }
}
