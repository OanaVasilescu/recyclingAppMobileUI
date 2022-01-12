package cg.example.greenlife.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cg.example.greenlife.R;
import cg.example.greenlife.api.RetrofitClient;
import cg.example.greenlife.controller.AccountFragment;
import cg.example.greenlife.controller.HomeFragment;
import cg.example.greenlife.controller.SearchFragment;
import cg.example.greenlife.model.Tip;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    ft.replace(R.id.frame_main, new AccountFragment());
                    ft.commit();
                    return true;
                case R.id.navigationHome:
                    ft.replace(R.id.frame_main, new HomeFragment());
                    ft.commit();
                    return true;
                case R.id.navigationSearch:
                    ft.replace(R.id.frame_main, new SearchFragment());
                    ft.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_main, new HomeFragment());
        if (getIntent().getStringExtra("Destination") != null) {
            Log.e("extra:", getIntent().getStringExtra("Destination"));
            if (getIntent().getStringExtra("Destination").equals("search")) {
                ft.replace(R.id.frame_main, new SearchFragment());
            }
        }
        ft.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getStringExtra("Destination") != null) {
            if (getIntent().getStringExtra("Destination").equals("search")) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_main, new SearchFragment());
                ft.commit();
            }
        }else {
            Log.e("Main", "before get random tip");
            this.getRandomTip();
        }
    }

    private void getRandomTip() {
        Call<Tip> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getRandomTip();

        call.enqueue(new Callback<Tip>() {
            @Override
            public void onResponse(Call<Tip> call, Response<Tip> response) {
                Boolean success;
                success = response.isSuccessful();

                int requestCode = response.code();


                if (success) {
                    this.setTipData(response);
                } else {
                    if (requestCode == 500)
                        Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(MainActivity.this, "no tips", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Tip> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            private void setTipData(Response<Tip> response) {
                TextView text = findViewById(R.id.recyclingTipText);
                assert response.body() != null;
                Tip tip = response.body(); //TODO: see how to get data
                text.setText(tip.getTipText());
            }
        });
    }
}


