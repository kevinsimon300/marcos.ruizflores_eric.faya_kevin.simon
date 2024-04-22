package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class SingleFragmentActivity extends AppCompatActivity {
    /*private TabBarFragment tabBarFragment;
    protected Fragment createFragment() {
        return new TabBarFragment();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlefragmentactivity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*Fragment fragment = fragmentManager.findFragmentById(R.id.singleFragmentActivity);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.singleFragmentActivity, fragment).commit();
        }*/
    }
}