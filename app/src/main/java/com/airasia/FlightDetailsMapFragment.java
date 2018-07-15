package com.airasia;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airasia.databinding.FragmentFlightDetailsMapBinding;
import com.airasia.models.Departure;
import com.airasia.models.FlightData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by techjini on 14/7/18.
 */

public class FlightDetailsMapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentFlightDetailsMapBinding mBinding;
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private FlightData mFlightData;

    public static Fragment getInstance(Bundle bundle) {
        FlightDetailsMapFragment flightDetailsMapFragment = new FlightDetailsMapFragment();
        flightDetailsMapFragment.setArguments(bundle);
        return flightDetailsMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String json = getArguments().getString("flight_details");
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_details_map,container,false);
//        setupMapView();
        return mBinding.getRoot();
    }

    private void setupMapView() {
        mapFragment = SupportMapFragment.newInstance();
        replaceMapFragment(R.id.map_view_flight_detail, mapFragment, getChildFragmentManager());
        mapFragment.getMapAsync(this);
    }

    /**
     * Method to replace map fragment
     *
     * @param view            the fragment container to replace the view with
     * @param fragment        fragment that has to be replaced
     * @param fragmentManager the fragment manager
     */
    private void replaceMapFragment(int view, Fragment fragment, @NonNull FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(view, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null)
            mGoogleMap = googleMap;
        Departure departure = mFlightData.getFlightDetails().get(0).getOnward().getDeparture();
        LatLng latLng = new LatLng(departure.getLat(),departure.getLong());
        addMarkerToMap(latLng);
    }

    private void addMarkerToMap(LatLng latLng) {
        /*Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                .title("Bangalore")
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));*/
    }
}
