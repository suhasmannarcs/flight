package com.airasia;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airasia.databinding.FlightDetailsListItemBinding;
import com.airasia.models.FlightData;
import com.airasia.models.FlightDetail;

/**
 * Created by techjini on 15/7/18.
 */

class FlightDetailsListAdapter extends RecyclerView.Adapter<FlightDetailsListAdapter.ItemHolder> {

    private boolean isOneWay;
    private FlightData mFlightData;

    public FlightDetailsListAdapter(FlightData flightData, boolean isOneWay) {
        mFlightData = flightData;
        this.isOneWay = isOneWay;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FlightDetailsListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.flight_details_list_item, parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        FlightDetail detail = mFlightData.getFlightDetails().get(position);
        holder.binding.setFlightDetails(detail);
        holder.binding.returnPv.setVisibility(isOneWay ? View.GONE: View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mFlightData.getFlightDetails().size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private FlightDetailsListItemBinding binding;

        public ItemHolder(FlightDetailsListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
