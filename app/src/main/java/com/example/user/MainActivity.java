package com.example.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.widget.VerticalTextView;

public class MainActivity extends AppCompatActivity {
VerticalTextView verticalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalTextView.setmText("君子食无求饱，居无求安，敏于事而慎于言，就有道而正焉，可谓好学也已。");
    }
}
