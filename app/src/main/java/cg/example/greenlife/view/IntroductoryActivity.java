package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import cg.example.greenlife.R;

public class IntroductoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        ImageView logo = findViewById(R.id.splash_screen_logo);
        ImageView background = findViewById(R.id.green_start_color);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_earth_animation);
        logo.animate().translationY(-1500).setDuration(1000).setStartDelay(4000);
        background.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(-1900).setDuration(1000).setStartDelay(4000);
    }
}