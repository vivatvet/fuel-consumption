package com.vvl.fuel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vladimir on 11.11.13.
 */
public class Fuel4 extends Activity implements View.OnClickListener {

    private ArrayList<PostItem> messages;

    TextView textView41;
    TextView textView42;
    TextView textView43;
    Button button41;

    String dist_sp_g;
    String dist_g;
    String price_g;
    String comp_g;
    String curr_g;
    String fuel_g;
    String d;
    String titl1 = null;
    String titl2 = null;
    float rate_titl1 =1;
    float rate_titl2 =1;
    float  result1_1_glob;
    float  result1_2_glob;
    float price_float;
    float distance;
    float consumption;

    String d2v_glob;
    int dubtrip;
    String[] data = {"dollars", "грн.", "руб.", "euro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel4);

        Intent intent = getIntent();

        String dist_sp = intent.getStringExtra("dist_sp");
        String dist = intent.getStringExtra("dist");
        String fuel = intent.getStringExtra("fuel");
        String price = intent.getStringExtra("price3");
        String comp = intent.getStringExtra("comp3");
        String curr = intent.getStringExtra("curr3");
        dist_sp_g = dist_sp;
        dist_g = dist;
        price_g = price;
        comp_g = comp;
        curr_g = curr;
        fuel_g = fuel;

        textView41 = (TextView) findViewById(R.id.textView41);
        textView42 = (TextView) findViewById(R.id.textView42);
        textView43 = (TextView) findViewById(R.id.textView43);
        button41 = (Button) findViewById(R.id.button41);

        button41.setOnClickListener(this);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner41);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        d = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                d = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        //Calculation consumption
        textView41.setText(getResources().getText(R.string.textView_price_title).toString() + " " + price + " " + curr);

        if ((TextUtils.isEmpty(comp.toString())) && !TextUtils.isEmpty(fuel.toString()) && !TextUtils.isEmpty(dist.toString())){
            textView42.setText(getResources().getText(R.string.textView_comp_title).toString() + " " + calculation_comp() + " " + getResources().getText(R.string.liters).toString());
        }
        else if (!TextUtils.isEmpty(comp.toString())) {
            textView42.setText(getResources().getText(R.string.textView_comp_title).toString() + " " + comp + " " + getResources().getText(R.string.liters).toString());
        }
        else {
            textView42.setText(getResources().getText(R.string.textView_comp_title).toString() + " " + getResources().getString(R.string.fuel_comp_empty).toString());
        }

        //Calculation spend
        pre_calculation_spedt();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button41:
                Spinner spinner = (Spinner) findViewById(R.id.spinner41);
                d = spinner.getSelectedItem().toString();
                pre_calculation_spedt();
                break;
            default:
                break;
        }
    }

    private float calculation_comp() {
        float dist_r = Float.parseFloat(dist_g);
        float fuel_r = Float.parseFloat(fuel_g);
        float comp_r = 100*fuel_r/dist_r;
        return comp_r;
    }

    private void pre_calculation_spedt() {
        price_float = Float.parseFloat(price_g);
        distance = Float.parseFloat(dist_g);
        if ((TextUtils.isEmpty(comp_g.toString())) && !TextUtils.isEmpty(fuel_g.toString()) && !TextUtils.isEmpty(dist_g.toString())) {
            consumption = calculation_comp();
            calculation_spedt();
        }
        else if (!TextUtils.isEmpty(comp_g.toString())) {
            consumption = Float.parseFloat(comp_g);
            calculation_spedt();
        }
        else {
            // TODO Auto-generated method stub
        }
    }

    private void calculation_spedt() {
        if (curr_g.equals("dollars")) { titl1 = "USD/UAH"; }
        if (curr_g.equals("руб.")) { titl1 = "RUB/UAH"; }
        if (curr_g.equals("грн.")) { titl1 = "UAH/UAH"; }
        if (curr_g.equals("euro")) { titl1 = "EUR/UAH"; }
        if (d.equals("dollars")) { titl2 = "USD/UAH"; }
        if (d.equals("руб.")) { titl2 = "RUB/UAH"; }
        if (d.equals("грн.")) { titl2 = "UAH/UAH"; }
        if (d.equals("euro")) { titl2 = "EUR/UAH"; }
        if (dist_sp_g.equals("1")) {dubtrip=2;}
        else {dubtrip=1;}
        result1_1_glob =((dubtrip * distance) * consumption)/100;
        result1_2_glob =price_float*result1_1_glob;
        GetCurrency runCurr = new GetCurrency();
        ConnectivityManager net = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (net.getActiveNetworkInfo() != null) {runCurr.execute();}
        else {
            Toast.makeText(this, getResources().getText(R.string.error_net).toString(), Toast.LENGTH_LONG).show();}

    }


    private class GetCurrency extends AsyncTask<Context, Integer, ArrayList<PostItem>> {

        private NewParser parser;

        protected void onPreExecute() {
            parser = new NewParser();
        }

        @Override
        protected ArrayList<PostItem> doInBackground(Context... arg0) {
            return  parser.parse();
        }

        @Override
        protected void onPostExecute(ArrayList<PostItem> result) {
            int ind;
            float curr_r=1;
            float result1_1;
            float result1_2;
            String rateM = null;
            messages = result;
            for (PostItem msg : messages){
                if (msg.title.equals(titl1)) {
                    ind = msg.description.indexOf("=");
                    rateM = msg.description.substring((ind+2), (ind+8)).trim();
                    rate_titl1 = Float.parseFloat(rateM);
                }
                if (msg.title.equals(titl2)) {
                    ind = msg.description.indexOf("=");
                    rateM = msg.description.substring((ind+2), (ind+8)).trim();
                    rate_titl2 = Float.parseFloat(rateM);
                }
            }
            curr_r = rate_titl2 / rate_titl1;
            result1_1 = result1_1_glob;
            result1_2 = result1_2_glob*curr_r;
            //String d2v = d2v_glob;
            textView43.setText(getResources().getText(R.string.result1_1).toString() + " " + result1_1 + " " + getResources().getText(R.string.result1_2).toString() + " " + result1_2 + " " + d);
        }
    }


}
