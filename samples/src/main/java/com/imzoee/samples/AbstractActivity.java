package com.imzoee.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imzoee.leathnu.Leathnu;
import com.imzoee.leathnu.Transition;
import com.imzoee.samples.R;

public class AbstractActivity extends AppCompatActivity {

    private Leathnu leathnu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract);

        leathnu = Leathnu.getInstance();

        final RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_abs_root);
        ImageView ivImg = (ImageView) findViewById(R.id.iv_img);
        final ImageView ivStar = (ImageView) findViewById(R.id.iv_star);
        TextView tvName = (TextView) findViewById(R.id.tv_name);

        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AbstractActivity.this).inflate(R.layout.activity_expand, null);
                View expRoot = view.findViewById(R.id.rl_exp_root);
                Transition transition = Transition.with(rlRoot, expRoot)
                        .dismiss(ivStar)
                        .raise(R.id.iv_heart);
                leathnu.prepare(transition);

                Toast.makeText(AbstractActivity.this, String.valueOf(expRoot.getWidth()), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AbstractActivity.this, ExpandActivity.class);
                startActivity(intent);
            }
        });
    }
}
