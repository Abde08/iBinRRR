package com.snowcrash.greenheart.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snowcrash.greenheart.R;
import com.snowcrash.greenheart.service.ActivityListener;

import java.util.List;

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private List<Activity> activityList;
    private ActivityListener activityListener;

    public CustomViewAdapter(List<Activity> activityList, ActivityListener listener) {
        this.activityList = activityList;
        this.activityListener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icons, parent, false);
        return new CustomViewHolder(view, activityListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.onActivityListener(activityList.get(position));
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}

