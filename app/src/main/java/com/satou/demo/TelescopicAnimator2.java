package com.satou.demo;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

public class TelescopicAnimator2 {

    public TelescopicAnimator2(TextView view, int maxValue, int minValue) {
        this.view = view;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    private TextView view;
    private int maxValue;
    private int minValue;
    private long duration = 300;
    private boolean isExpand = false;

    public void expand() {

        if (null != view && !isExpand) {
            ObjectAnimator.ofInt(view, "width", view.getWidth(), maxValue).setDuration(duration).start();
        }
        isExpand = true;
    }

    public void reduce() {
        if (null != view && isExpand) {
            ObjectAnimator.ofInt(view, "width", view.getWidth(), minValue).setDuration(duration).start();
        }
        isExpand = false;
    }
}
