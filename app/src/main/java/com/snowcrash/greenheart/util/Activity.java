package com.snowcrash.greenheart.util;

import com.snowcrash.greenheart.R;
import com.snowcrash.greenheart.image.MarterialDetectionActivity;

public class Activity<T extends MarterialDetectionActivity> {
    public int iconResourceId = R.drawable.ic_launcher_foreground;
    public String activityText = "";
    public Class<T> activityClazz;

    public Activity(int iconResourceId, String activityText, Class<T> activityClazz) {
        this.iconResourceId = iconResourceId;
        this.activityText = activityText;
        this.activityClazz = activityClazz;
    }
}
