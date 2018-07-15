package com.airasia;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airasia.databinding.FragmentFlightListDetailsBinding;
import com.airasia.models.FlightData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by techjini on 14/7/18.
 */

public class FlightDetailsListFragment extends Fragment{

    private FragmentFlightListDetailsBinding mBinding;
    private FlightData mFlightData;
    private boolean isOneWay;

    public static FlightDetailsListFragment getInstance(Bundle bundle) {
        FlightDetailsListFragment flightDetailsListFragment = new FlightDetailsListFragment();
        flightDetailsListFragment.setArguments(bundle);
        return flightDetailsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String json = getArguments().getString("flight_details");
        isOneWay = getArguments().getBoolean("isOneWay");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mFlightData = mapper.readValue(json, FlightData.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_list_details,container,false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        mBinding.flightDetailsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.flightDetailsList.setAdapter(new FlightDetailsListAdapter(mFlightData, isOneWay));
    }
}
