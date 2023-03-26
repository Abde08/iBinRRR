package com.snowcrash.greenheart.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snowcrash.greenheart.R;
import com.snowcrash.greenheart.service.ActivityListener;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView iconImageView;
    private TextView algoTextView;
    private ActivityListener activityListener;
    private Activity activity;

    public CustomViewHolder(@NonNull View itemView, ActivityListener activityListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.activityListener = activityListener;

        iconImageView = itemView.findViewById(R.id.iconImageView);
        algoTextView = itemView.findViewById(R.id.activityTextView);
    }

    public void onActivityListener(Activity activity) {
        this.activity = activity;
        iconImageView.setImageResource(activity.iconResourceId);
        algoTextView.setText(activity.activityText);
    }

    @Override
    public void onClick(View v) {
        if (activityListener != null) {
            activityListener.onActivitySelected(activity);
        }
    }
}

