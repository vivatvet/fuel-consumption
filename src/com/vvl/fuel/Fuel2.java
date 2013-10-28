package com.vvl.fuel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by budnik on 24.10.13.
 */
public class Fuel2 extends Activity {

    //String[] data = {"dollars", "грн.", "руб.", "euro"};
    private Fuel fuel = new Fuel();
    String[] data = fuel.data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        // заголовок
        // spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(1);

    }
}
