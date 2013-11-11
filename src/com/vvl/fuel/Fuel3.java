package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by budnik on 28.10.13.
 */
public class Fuel3 extends Activity implements View.OnClickListener {

    TextView textView_price;
    TextView textView_comp;
    EditText editText_dist;
    EditText editText_sum;
    EditText editText_fuel;

    SharedPreferences sPref;

    final String dist = "dist";
    final String sum = "sum";
    final  String fuel = "fuel";
    int dist_sp;
    int spinpoz;


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
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.ways, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                // dist_sp = spinner.getSelectedItem().toString();
                dist_sp = spinner.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        loadText();
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
        SharedPreferences.Editor priceED = sPref.edit();
        //Editor compED = sPref.edit();
        priceED.putString(dist, editText_dist.getText().toString());
        priceED.putString(sum, editText_sum.getText().toString());
        priceED.putString(fuel, editText_fuel.getText().toString());
        priceED.putInt(String.valueOf(spinpoz), spinner.getSelectedItemPosition());
        priceED.commit();
        //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
        String savedDist = sPref.getString(dist, "");
        String savedSum = sPref.getString(sum, "");
        String savedFuel = sPref.getString(fuel, "");
        String savedDP = sPref.getString(String.valueOf(spinpoz), "");
        editText_dist.setText(savedDist);
        editText_sum.setText(savedSum);
        editText_fuel.setText(savedFuel);
        spinner.setSelection(Integer.parseInt(savedDP));

        spinner.setSelection(dist_sp);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next3:
                saveText();
                Intent intent = new Intent(this, Fuel4.class);
                intent.putExtra("dist", editText_dist.getText().toString());
                intent.putExtra("sum", editText_sum.getText().toString());
                intent.putExtra("fuel", editText_fuel.getText().toString());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
