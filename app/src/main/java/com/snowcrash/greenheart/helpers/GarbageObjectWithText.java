package com.snowcrash.greenheart.helpers;

import android.graphics.Rect;
import android.graphics.RectF;

public class GarbageObjectWithText {
    public String objectName;
    public Rect objectRect;

    public GarbageObjectWithText(String ObjectName, Rect objectRect) {
        this.objectName = ObjectName;
        this.objectRect = objectRect;
    }

    public GarbageObjectWithText(String displayName, RectF boundingBox) {
        this.objectName = displayName;
        this.objectRect = new Rect();
        boundingBox.round(objectRect);
    }
}
