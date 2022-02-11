package cg.example.greenlife.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cg.example.greenlife.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        }
    }


}


