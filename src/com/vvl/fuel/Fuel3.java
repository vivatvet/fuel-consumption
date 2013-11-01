package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by budnik on 28.10.13.
 */
public class Fuel3 extends Activity {

    TextView textView_price;
    TextView textView_comp;
    EditText editText_dist;
    EditText editText_sum;
    EditText editText_fuel;
    String[] data_way = {getResources().getText(R.string.way1).toString(), getResources().getText(R.string.way2).toString()};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel3);

        Intent intent = getIntent();

        textView_price = (TextView) findViewById(R.id.textView_price);
        textView_comp = (TextView) findViewById(R.id.textView_comp);
        editText_dist = (EditText) findViewById(R.id.editText_dist);
        editText_sum = (EditText) findViewById(R.id.editText_sum);
        editText_fuel = (EditText) findViewById(R.id.editText_fuel);

        String price = intent.getStringExtra("price");
        String comp = intent.getStringExtra("comp");
        String curr = intent.getStringExtra("curr");

        if (comp.isEmpty()) {textView_comp.setText(getResources().getText(R.string.fuel_comp_empty).toString());}
        else {textView_comp.setText(comp + " " + getResources().getText(R.string.liters).toString());}
        textView_price.setText(price + " " + curr);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_way);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
        spinner.setAdapter(adapter);


    }
}
