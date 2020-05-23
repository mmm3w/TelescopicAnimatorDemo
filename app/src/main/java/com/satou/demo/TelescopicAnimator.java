package com.satou.demo;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

/**
 * Created by Mitsuki on 2018/3/16.
 */
@Deprecated
public class TelescopicAnimator {
    private final String tag = "TelescopicAnimator";

    private View subView;
    private View parentView;
    private int subViewWidth;
    private int subViewHeight;
    private int parentViewWidth;
    private int parentViewHeight;
    private boolean isSubPost = false;
    private boolean isParentPost = false;
    private ValueAnimator openAnimator;
    private ValueAnimator closeAnimator;
    private boolean isExpand = false;

    //默认动画时间
    private long duration = 300;
    //左右边距和
    private int margin = 0;

    public TelescopicAnimator(View sub) {
        this.subView = sub;
        this.parentView = (View) subView.getParent();
        init();
    }

    private void init() {
        subView.post(() -> {
            isSubPost = true;
            subViewWidth = subView.getWidth();
        });
        parentView.post(() -> {
            isParentPost = true;
            margin = parentView.getPaddingLeft() + parentView.getPaddingRight();
            parentViewWidth = parentView.getWidth();
        });
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void expand() {
        isExpand = true;
        boolean c;
        if (closeAnimator == null) {
            c = false;
        } else {
            c = closeAnimator.isRunning();
        }

        if (subView.getWidth() == subViewWidth || c) {
            if (openAnimator != null && openAnimator.isRunning()) return;
            if (closeAnimator != null) closeAnimator.cancel();
            openAnimator = ValueAnimator.ofFloat(subView.getWidth(), parentViewWidth - margin);
            openAnimator.setDuration(getDuration(duration, true));
            openAnimator.addUpdateListener(animator -> updata(animator));
            openAnimator.start();
        } else if (openAnimator != null || openAnimator.isRunning()) {

        }
    }

    public void reduce() {
//        Log.i(tag, "reduce");
        isExpand = false;
        boolean o;
        if (openAnimator == null) {
            o = false;
        } else {
            o = openAnimator.isRunning();
        }

        if (subView.getWidth() == (parentViewWidth - margin) || o) {
            if (closeAnimator != null && closeAnimator.isRunning()) return;
            if (openAnimator != null) openAnimator.cancel();
            closeAnimator = ValueAnimator.ofFloat(subView.getWidth(), subViewWidth);
            closeAnimator.setDuration(getDuration(duration, false));
            closeAnimator.addUpdateListener(animator -> updata(animator));
            closeAnimator.start();
        } else if (closeAnimator != null || closeAnimator.isRunning()) {

        }
    }

    public void doAnimat() {
        Log.i(tag, "doAnimat");
        if (subView.getWidth() == subViewWidth) {
            Log.i(tag, "doAnimat expand");
            expand();
        } else if (subView.getWidth() == parentViewWidth - margin) {
            Log.i(tag, "doAnimat reduce");
            reduce();
        }
    }

    private long getDuration(long duration, boolean isReverse) {
        if (isReverse)
            return duration * (parentViewWidth - margin - subView.getWidth()) / (parentViewWidth - margin - subViewWidth);
        else
            return duration * (subView.getWidth() - subViewWidth) / (parentViewWidth - margin - subViewWidth);

    }

    private void updata(ValueAnimator animator) {
        float currentValue = (float) animator.getAnimatedValue();
        subView.getLayoutParams().width = (int) currentValue;
        subView.requestLayout();
    }

}
