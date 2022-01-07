package cg.example.greenlife.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import cg.example.greenlife.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    System.out.println("in item selected");
//                    this.goToProfilePage(item);
                    return true;
                case R.id.navigationHome:
//                    this.goToHome(item);
                    return true;
                case R.id.navigationSearch:
//                    this.goToScanPage(item);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

    }


    public void goToHome(MenuItem menuItem) {
        System.out.println("!!!!!!!!!!");
        Toast.makeText(MainActivity.this, "goHome  pressed", Toast.LENGTH_LONG).show();
    }

    public void goToScanPage(MenuItem menuItem) {
        System.out.println("!!!!!!!!sdgfhg!");
        Toast.makeText(MainActivity.this, "goScan  pressed", Toast.LENGTH_LONG).show();
    }

    public void goToProfilePage(MenuItem menuItem) {
        System.out.println("!!!!!????????/!!!!!");
        Toast.makeText(MainActivity.this, "goProfile  pressed", Toast.LENGTH_LONG).show();
    }
}


