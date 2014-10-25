package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by budnik on 28.10.13.
 */
public class Fuel3 extends Activity implements View.OnClickListener, View.OnTouchListener {

    TextView textView_price;
    TextView textView_comp;
    EditText editText_dist;
    EditText editText_sum;
    EditText editText_fuel;
    Button button_next3;

    SharedPreferences sPref;

    final String dist = "dist";
    final String sum = "sum";
    final String fuel = "fuel";
    String dist_sp;
    int spinpoz;
    String price_g;
    String comp_g;
    String curr_g;
    String pos_item;
    float fromPosition = 0;

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
        button_next3 = (Button) findViewById(R.id.button_next3);

        button_next3.setOnClickListener(this);

        String price = intent.getStringExtra("price");
        String comp = intent.getStringExtra("comp");
        String curr = intent.getStringExtra("curr");
        pos_item = intent.getStringExtra("pos_item");
        price_g = price;
        comp_g = comp;
        curr_g = curr;

        if (comp.isEmpty()) {
            textView_comp.setText(getResources().getText(R.string.fuel_comp_empty).toString());
        } else {
            textView_comp.setText(comp + " " + getResources().getText(R.string.liters).toString());
        }
        textView_price.setText(price + " " + curr);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);

        // адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.ways, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                // dist_sp = spinner.getSelectedItem().toString();
                dist_sp = String.valueOf(spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        loadText();

        // Устанавливаем listener касаний, для последующего перехвата жестов
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout3);
        mainLayout.setOnTouchListener(this);
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
        SharedPreferences.Editor priceED = sPref.edit();
        //Editor compED = sPref.edit();
        priceED.putString(dist, editText_dist.getText().toString());
        priceED.putString(sum, editText_sum.getText().toString());
        priceED.putString(fuel, editText_fuel.getText().toString());
        priceED.putString(String.valueOf(spinpoz), String.valueOf(spinner.getSelectedItemPosition()));

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
        if (savedDP == "") {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(Integer.parseInt(savedDP));
        }
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View vv) {
        switch (vv.getId()) {
            case R.id.button_next3:
                saveText();
                final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
                dist_sp = String.valueOf(spinner.getSelectedItemPosition());
                Intent intent = new Intent(this, Fuel4.class);
                intent.putExtra("dist", editText_dist.getText().toString());
                intent.putExtra("sum", editText_sum.getText().toString());
                intent.putExtra("fuel", editText_fuel.getText().toString());
                intent.putExtra("dist_sp", dist_sp.toString());
                intent.putExtra("price3", price_g);
                intent.putExtra("comp3", comp_g);
                intent.putExtra("curr3", curr_g);
                intent.putExtra("pos_item", pos_item);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            // Пользователь нажал на экран, т.е. начало движения
            // fromPosition - координата по оси X начала выполнения операции
            case MotionEvent.ACTION_DOWN:
                fromPosition = event.getX();
                break;
            // Пользователь отпустил экран, т.е. окончание движения
            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (fromPosition > toPosition) {
                    saveText();
                    final Spinner spinner = (Spinner) findViewById(R.id.spinner_way);
                    dist_sp = String.valueOf(spinner.getSelectedItemPosition());
                    Intent intent = new Intent(this, Fuel4.class);
                    intent.putExtra("dist", editText_dist.getText().toString());
                    intent.putExtra("sum", editText_sum.getText().toString());
                    intent.putExtra("fuel", editText_fuel.getText().toString());
                    intent.putExtra("dist_sp", dist_sp.toString());
                    intent.putExtra("price3", price_g);
                    intent.putExtra("comp3", comp_g);
                    intent.putExtra("curr3", curr_g);
                    intent.putExtra("pos_item", pos_item);
                    startActivity(intent);}
                else if (fromPosition < toPosition) {
                       finish();
                    }
                break;
            default:
                break;
        }
        return true;
    }
}
