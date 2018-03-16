package com.satou.demo;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TelescopicAnimator telescopicAnimator;
    private ScrollView scrollView;
    private RelativeLayout relativeLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            ViewCompat.setFitsSystemWindows(rootView, false);
            rootView.setClipToPadding(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        linearLayout = findViewById(R.id.ll_bg);
        relativeLayout = findViewById(R.id.rl_bg);
        relativeLayout.getBackground().mutate().setAlpha(0);
        imageView = findViewById(R.id.iv_);
        telescopicAnimator = new TelescopicAnimator(findViewById(R.id.ll_bg));

        scrollView = findViewById(R.id.sl_view);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            changeToolbarAlpha();
            if (scrollView.getScrollY() >= (imageView.getHeight() - relativeLayout.getHeight())) {
                telescopicAnimator.expand();
            }
            //滚动距离<=0时 即滚动到顶部时  且当前伸展状态 进行收缩操作
            else if (scrollView.getScrollY() <= 0) {
                telescopicAnimator.reduce();
            }
        });
    }

    private void changeToolbarAlpha() {
        int scrollY = scrollView.getScrollY();
        if (scrollY < 0) {
            relativeLayout.getBackground().mutate().setAlpha(0);
            return;
        }
        float radio = Math.min(1, scrollY / (imageView.getHeight() - relativeLayout.getHeight() * 1f));
        relativeLayout.getBackground().mutate().setAlpha((int) (radio * 0xFF));
    }
}
