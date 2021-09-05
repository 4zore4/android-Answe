package com.example.answer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        textView = (TextView) findViewById(R.id.textView);


    }
}