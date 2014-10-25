package com.vvl.fuel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by budnik on 24.10.13.
 */
public class Fuel2 extends Activity implements View.OnClickListener, View.OnTouchListener {

    String[] data = {"US Dollar", "грн.", "руб.", "Euro", "British Pound Sterling", "Polish Zloty"};
    SharedPreferences sPref;

    EditText editText_price;
    EditText editText_comp;
    Button button_next;

    final String price = "price";
    final String comp = "comp";
    final String curr_currency = "1";
    String d;
    int pos_item = 1;
    int savedCurrency;
    float fromPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel2);

        //находим элементы
        editText_price = (EditText) findViewById(R.id.editText_price);
        editText_comp = (EditText) findViewById(R.id.editText_comp);
        button_next = (Button) findViewById(R.id.button_next);

        button_next.setOnClickListener(this);



        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setAdapter(adapter);

        // заголовок
        // spinner.setPrompt("Title");
        // выделяем элемент

        loadText();

        spinner.setSelection(savedCurrency);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                d = spinner.getSelectedItem().toString();
                pos_item = spinner.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // Устанавливаем listener касаний, для последующего перехвата жестов
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout2);
        mainLayout.setOnTouchListener(this);

    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor priceED = sPref.edit();
        //Editor compED = sPref.edit();
        priceED.putString(price, editText_price.getText().toString());
        priceED.putString(comp, editText_comp.getText().toString());
        priceED.putString(curr_currency, Integer.toString(pos_item));
        priceED.commit();
        //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedPrice = sPref.getString(price, "");
        String savedComp = sPref.getString(comp, "");
        savedCurrency = Integer.parseInt(sPref.getString(curr_currency, "1"));
        editText_price.setText(savedPrice);
        editText_comp.setText(savedComp);




        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        // Проверяем поля на пустоту
        if (TextUtils.isEmpty(editText_price.getText().toString()) ) {
            Toast.makeText(this, getResources().getText(R.string.price_empty).toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.button_next:
                saveText();
                Intent intent = new Intent(this, Fuel3.class);
                intent.putExtra("price", editText_price.getText().toString());
                intent.putExtra("comp", editText_comp.getText().toString());
                intent.putExtra("curr", d);
                intent.putExtra("pos_item", Integer.toString(pos_item));
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
                if (fromPosition > toPosition){
                    // Проверяем поля на пустоту
                    if (TextUtils.isEmpty(editText_price.getText().toString()) ) {
                        Toast.makeText(this, getResources().getText(R.string.price_empty).toString(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    saveText();
                    Intent intent = new Intent(this, Fuel3.class);
                    intent.putExtra("price", editText_price.getText().toString());
                    intent.putExtra("comp", editText_comp.getText().toString());
                    intent.putExtra("curr", d);
                    intent.putExtra("pos_item", Integer.toString(pos_item));
                    startActivity(intent);}
                else if (fromPosition < toPosition){
                    //nothing
                    ;}
                break;
            default:
                break;
        }
        return true;
    }
}
