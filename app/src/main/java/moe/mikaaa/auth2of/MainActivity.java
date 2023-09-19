package moe.mikaaa.auth2of;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment fragment = null;

                    int itemId = item.getItemId();
                    if (itemId == R.id.action_data_input) {
                        fragment = new DataInputFragment();
                    } else if (itemId == R.id.action_password_list) {
                        fragment = new PasswordListFragment();
                    }

                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, fragment);
                        transaction.commit();
                    }

                    return true;
                }
        );

        bottomNavigationView.setSelectedItemId(R.id.action_data_input);
    }

}