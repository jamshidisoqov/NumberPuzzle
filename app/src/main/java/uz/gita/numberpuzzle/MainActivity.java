package uz.gita.numberpuzzle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.numberpuzzle.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_layout, new HomeFragment())
                .commit();
    }
}