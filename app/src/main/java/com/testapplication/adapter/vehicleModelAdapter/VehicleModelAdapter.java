package com.testapplication.adapter.vehicleModelAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.testapplication.R;
import com.testapplication.model.vehicleModel.VehicleModel;

import java.util.List;

public class VehicleModelAdapter extends RecyclerView.Adapter<VehicleModelVH> {

    private List<VehicleModel> models;

    public VehicleModelAdapter(List<VehicleModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public VehicleModelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_model_item, parent, false);

        return new VehicleModelVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleModelVH holder, int position) {
        VehicleModel item = models.get(position);
        holder.titleText.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
