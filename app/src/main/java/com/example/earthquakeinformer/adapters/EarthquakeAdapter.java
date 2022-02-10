package com.example.earthquakeinformer.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquakeinformer.databinding.RowInformBinding;
import com.example.earthquakeinformer.models.Properties;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {
    private RowInformBinding binding;
    private List<Properties> properties = new ArrayList<>();

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= RowInformBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new EarthquakeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        final Properties item = properties.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
    public void submitList(List<Properties> items) {
        this.properties = items;
        notifyDataSetChanged();
    }


    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        RowInformBinding binding;

        public EarthquakeViewHolder(RowInformBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }


        public void bind(Properties item) {
            binding.rowMagTV.setText((int) item.getMag());
            binding.rowPlaceTV.setText(item.getPlace());
        }
    }

}
