package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import cg.example.greenlife.R;
import cg.example.greenlife.controller.LoginAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;


public class IntroductoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        ImageView logo = findViewById(R.id.splash_screen_logo);
        ImageView whiteBackground = findViewById(R.id.registerAndLoginBackgroundImageView);
        ConstraintLayout loginRegisterBackground = findViewById(R.id.registerAndLoginBackground);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_earth_animation);
        float v = 0;

        loginRegisterBackground.setClipToOutline(true);

        logo.animate().translationY(-1500).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(-1900).setDuration(1000).setStartDelay(4000);
        whiteBackground.animate().scaleY(1150).setDuration(1000).setStartDelay(4000).withEndAction(new Runnable() {
            @Override
            public void run() {
                loginRegisterBackground.setVisibility(View.VISIBLE);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayoutId);
        ViewPager viewPager = findViewById(R.id.viewPagerId);
        FloatingActionButton googleButton = findViewById(R.id.googleButtonId);
        FloatingActionButton facebookButton = findViewById(R.id.facebookButtonId);
        FloatingActionButton twitterButton = findViewById(R.id.twitter);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        facebookButton.setTranslationY(300);
        googleButton.setTranslationY(300);
        twitterButton.setTranslationY(300);
        tabLayout.setTranslationX(300);

        facebookButton.setAlpha(v);
        googleButton.setAlpha(v);
        twitterButton.setAlpha(v);
        tabLayout.setAlpha(v);

        facebookButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(4300).start();
        googleButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(4600).start();
        twitterButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(4900).start();
        tabLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(4100).start();
    }
}

