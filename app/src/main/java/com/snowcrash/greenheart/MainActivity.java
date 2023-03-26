package com.snowcrash.greenheart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snowcrash.greenheart.image.MarterialDetectionActivity;
import com.snowcrash.greenheart.object.GarbageDetectionActivity;
import com.snowcrash.greenheart.service.ActivityListener;
import com.snowcrash.greenheart.util.Activity;
import com.snowcrash.greenheart.util.CustomViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Activity> arrayList = new ArrayList<>();
        arrayList.add(new Activity(R.drawable.baseline_image_black_48, "Material Detection", MarterialDetectionActivity.class));
        arrayList.add(new Activity(R.drawable.baseline_center_focus_strong_black_48, "Garbage Detection", GarbageDetectionActivity.class));
        CustomViewAdapter customViewAdapter = new CustomViewAdapter(arrayList, this);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(customViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onActivitySelected(Activity activity) {
        Intent intent = new Intent(this, activity.activityClazz);
        intent.putExtra("name", activity.activityText);
        startActivity(intent);
    }
}
