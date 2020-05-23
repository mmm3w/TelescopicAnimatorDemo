package com.satou.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView imageView;
    private TextView searchView;
    private FrameLayout frameLayout;

    private TelescopicAnimator2 telescopicAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersive();
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.tv_search);
        frameLayout = findViewById(R.id.fl_bg);
        imageView = findViewById(R.id.iv_);
        scrollView = findViewById(R.id.sl_view);

        frameLayout.getBackground().mutate().setAlpha(0);


//
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            changeToolbarAlpha();
            if (scrollView.getScrollY() >= (imageView.getHeight() - frameLayout.getHeight())) {
                animator().expand();
            }
            //滚动距离<=0时 即滚动到顶部时  且当前伸展状态 进行收缩操作
            else if (scrollView.getScrollY() <= 0) {
                animator().reduce();
            }
        });

    }

    private void immersive(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void changeToolbarAlpha() {
        int scrollY = scrollView.getScrollY();
        if (scrollY < 0) {
            frameLayout.getBackground().mutate().setAlpha(0);
            return;
        }
        float radio = Math.min(1, scrollY / (imageView.getHeight() - frameLayout.getHeight() * 1f));
        frameLayout.getBackground().mutate().setAlpha((int) (radio * 0xFF));
    }

    private TelescopicAnimator2 animator(){
        if (null == telescopicAnimator){
            telescopicAnimator = new TelescopicAnimator2(searchView,frameLayout.getWidth(),searchView.getWidth());
        }
        return telescopicAnimator;
    }
}
