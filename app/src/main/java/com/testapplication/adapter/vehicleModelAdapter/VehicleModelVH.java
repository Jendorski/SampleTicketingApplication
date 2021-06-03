package com.testapplication.adapter.vehicleModelAdapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.testapplication.R;

public class VehicleModelVH extends RecyclerView.ViewHolder {

    public AppCompatTextView titleText;

    public VehicleModelVH(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.vehicle_model_title_text);
    }
}
