package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import cg.example.greenlife.R;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;


public class IntroductoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        ImageView logo = findViewById(R.id.splash_screen_logo);
        ImageView loginRegisterBackground = findViewById(R.id.registerAndLoginBackground);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_earth_animation);

        loginRegisterBackground.setClipToOutline(true);

        logo.animate().translationY(-1500).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(-1900).setDuration(1000).setStartDelay(4000);
        loginRegisterBackground.animate().scaleY(1150).setDuration(1000).setStartDelay(4000);
    }
}

