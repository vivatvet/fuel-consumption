package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by budnik on 28.10.13.
 */
public class Fuel3 extends Activity {

    TextView textView_price;
    TextView textView_comp;

    String cm;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel3);

        Intent intent = getIntent();

        textView_price = (TextView) findViewById(R.id.textView_price);
        textView_comp = (TextView) findViewById(R.id.textView_comp);

        String price = intent.getStringExtra("price");
        String comp = intent.getStringExtra("comp");
        String curr = intent.getStringExtra("curr");
        if (comp.isEmpty()) {comp ="netu";}

        textView_price.setText("Price = " + comp);
        textView_comp.setText("Comp = " + curr);

    }
}
