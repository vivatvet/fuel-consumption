package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by vladimir on 11.11.13.
 */
public class Fuel4 extends Activity {

    TextView textView41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel4);

        Intent intent = getIntent();

        String dist_sp = intent.getStringExtra("dist_sp");
        String price = intent.getStringExtra("price3");
        String comp = intent.getStringExtra("comp3");
        String curr = intent.getStringExtra("curr3");

        textView41 = (TextView) findViewById(R.id.textView41);
        textView41.setText(getResources().getText(R.string.textView_price_title).toString() + " " + price + " " + curr);

    }

}
