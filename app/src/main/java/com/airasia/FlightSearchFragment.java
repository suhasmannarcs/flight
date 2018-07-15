package com.airasia;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import com.airasia.databinding.FragmentFlightSearchBinding;
import com.airasia.utils.AppConstants;
import com.airasia.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by techjini on 14/7/18.
 */

public class FlightSearchFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private FragmentFlightSearchBinding mBinding;
    private String[] mLocations;

    Calendar myCalendar = Calendar.getInstance();

    public static FlightSearchFragment getInstance(Bundle bundle) {
        FlightSearchFragment flightSearchFragment = new FlightSearchFragment();
        flightSearchFragment.setArguments(bundle);
        return flightSearchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String locations = getArguments().getString(AppConstants.BundleConstants.LOCATION);
        try {
            JSONObject jsonObject = new JSONObject(locations);
            JSONArray jsonArray = jsonObject.getJSONArray("locations");
            mLocations = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                mLocations[i]= jsonArray.get(i).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flight_search, container, false);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        mBinding.setHandler(this);
        mBinding.oneWayJourneyCheckbox.setOnCheckedChangeListener(this);

        ArrayAdapter<String> sourceArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item,mLocations);
        mBinding.source.setThreshold(1);
        mBinding.source.setAdapter(sourceArrayAdapter);

        ArrayAdapter<String> destinationArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item,mLocations);
        mBinding.destination.setThreshold(1);
        mBinding.destination.setAdapter(destinationArrayAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnDate:
            case R.id.departureDate:
                showDatePicker(v);
                break;
            case R.id.btn_done:
                if(isValid()) {
                    navigateToFlightDetailsFragment();
                }
                break;
            default:
                break;
        }
    }

    private void navigateToFlightDetailsFragment() {
        String source = mBinding.source.getText().toString();
        String destination = mBinding.destination.getText().toString();
        String departure = mBinding.departureDate.getText().toString();
        String returnDate = mBinding.returnDate.getText().toString();
        boolean isOneWayChecked = mBinding.oneWayJourneyCheckbox.isChecked();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BundleConstants.SOURCE, source);
        bundle.putString(AppConstants.BundleConstants.DESTINATION, destination);
        bundle.putString(AppConstants.BundleConstants.DEPARTURE_DATE, departure);
        bundle.putString(AppConstants.BundleConstants.RETURN_DATE, returnDate);
        bundle.putBoolean(AppConstants.BundleConstants.ONE_WAY_JOURNEY,isOneWayChecked);
        FlightDetailsFragment flightDetailsFragment = FlightDetailsFragment.getInstance(bundle);
        ((HomeActivity)getActivity()).addFragment(flightDetailsFragment);
    }

    private boolean isValid() {
        boolean isValid = true;
        String source = mBinding.source.getText().toString();
        String destination = mBinding.destination.getText().toString();
        String departure = mBinding.departureDate.getText().toString();
        String returnDate = mBinding.returnDate.getText().toString();
        if(TextUtils.isEmpty(source)) {
            mBinding.source.setError(getString(R.string.source_error));
            isValid = false;
        } else if(TextUtils.isEmpty(destination)) {
            mBinding.destination.setError(getString(R.string.destination_error));
            isValid = false;
        } else if(TextUtils.isEmpty(departure)) {
            mBinding.departureDate.setError(getString(R.string.departure_error));
            isValid = false;
        } else if(!mBinding.oneWayJourneyCheckbox.isChecked()) {
            if(TextUtils.isEmpty(returnDate)) {
                mBinding.returnDate.setError(getString(R.string.return_date_error));
                isValid = false;
            }
        }
        return isValid;
    }

    private void showDatePicker(final View v) {
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((EditText)v).setText(CommonUtils.getDateInUserFormat(myCalendar));
            }
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.one_way_journey_checkbox:
                mBinding.returnDate.setVisibility(isChecked ? View.GONE : View.VISIBLE);
                break;
            default:
                break;
        }
    }
}