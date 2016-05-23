package com.imzoee.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imzoee.leathnu.Leathnu;
import com.imzoee.samples.R;

public class ExpandActivity extends AppCompatActivity {
    private Leathnu leathnu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leathnu = Leathnu.getInstance();
        //setContentView(R.layout.activity_expand);
        setContentView(leathnu.getLaunchView());

        leathnu.start();
    }
}
