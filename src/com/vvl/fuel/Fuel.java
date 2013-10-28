package com.vvl.fuel;

import java.util.ArrayList;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.AdapterView.OnItemSelectedListener;


public class Fuel extends Activity implements OnClickListener {

	 private ArrayList<PostItem> messages;
    SharedPreferences sPref;
	
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	
	TextView textViewResult1;
	TextView textViewResult2;
	TextView textViewResult3;
	
	CheckBox td;
	
	String d;
	String d2;

   final String price = "price";
   final String comp = "comp";
	
	float rate_titlg =1;
	float rate_titl1 =1;
    float rate_titl2 =1;
    float  result1_1_glob;
    float  result1_2_glob;
	String cur1 = null;
	String cur2 = null;
	String titl1 = null;
	String titl2 = null;
	String d2v_glob;
	
	String[] data = {"dollars", "грн.", "руб.", "euro"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel2);
        
     // находим элементы
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        textViewResult1 = (TextView) findViewById(R.id.textViewResult1);
        textViewResult2 = (TextView) findViewById(R.id.textViewResult2);
        textViewResult3 = (TextView) findViewById(R.id.textViewResult3);
        
     // прописываем обработчик
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        
        td = (CheckBox) findViewById(R.id.checkBox1);
        
     // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);
                
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        
        // заголовок
       // spinner.setPrompt("Title");
        // выделяем элемент 
        spinner.setSelection(1);
        spinner2.setSelection(1);
        
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
     @Override
    public void onItemSelected(AdapterView<?> parent, View view,
            int position, long id) {
              // показываем позиция нажатого элемента
             // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
              d = spinner.getSelectedItem().toString();
              d2  = spinner2.getSelectedItem().toString();
                          }
            @Override
	public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
          });
        loadText();
        
 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fuel, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 float price = 0;
	        float consumption = 0;
	        float km_drove = 0;
	        float sum = 0;
	        float gas = 0;
	        float result1_1 = 0;
	        float result1_2 = 0;
	        float result2_1 = 0;
	        float result2_2 = 0;
	        float result3 = 0;	        
	        
	     // Проверяем поля на пустоту
	        if (TextUtils.isEmpty(editText1.getText().toString())
	            || TextUtils.isEmpty(editText2.getText().toString()) ) {
	          textViewResult1.setText(getResources().getText(R.string.error_1).toString());
	          textViewResult2.setText(getResources().getText(R.string.error_1).toString());
	          textViewResult3.setText(getResources().getText(R.string.error_1).toString());
	          return;
	        }
	        
	     // читаем EditText и заполняем переменные числами
	        price = Float.parseFloat(editText1.getText().toString());
	        consumption = Float.parseFloat(editText2.getText().toString());
	        if (TextUtils.isEmpty(editText3.getText().toString())) {  km_drove=0; } else {  km_drove = Float.parseFloat(editText3.getText().toString()); }
	        if (TextUtils.isEmpty(editText4.getText().toString())) {  sum=0; } else {  sum = Float.parseFloat(editText4.getText().toString()); }
	        if (TextUtils.isEmpty(editText5.getText().toString())) {  gas=0; } else {  gas = Float.parseFloat(editText5.getText().toString()); }
	        
			// определяем нажатую кнопку и выполняем соответствующую операцию
	        switch (v.getId()) {
	        case R.id.button1:
                  saveText();
				  int dubtrip;
				if (td.isChecked()) {dubtrip=2;}
	        	  else {dubtrip=1;}
	              result1_1 =( (dubtrip * km_drove) * consumption)/100;
	              result1_2 =price*result1_1;	              
	              textViewResult1.setText(getResources().getText(R.string.result1_1).toString() + " " + result1_1 + " " + getResources().getText(R.string.result1_2).toString() + " " + result1_2 + " " + d);
	              break;
	        case R.id.button2:
                  saveText();
		          result2_1 =sum/price;
		          result2_2 =result2_1*100/consumption;
		          textViewResult2.setText(getResources().getText(R.string.result2_1).toString() + " " + result2_1 + " " + getResources().getText(R.string.result2_2).toString() + " " + result2_2 + " " + getResources().getText(R.string.result2_3).toString());
		          break;
	        case R.id.button3:
                  saveText();
		          result3 =gas*100/consumption;
		          textViewResult3.setText(getResources().getText(R.string.result3_1).toString() + " " + result3 + " " + getResources().getText(R.string.result2_3).toString());
		          break;
	        case R.id.button4:
	        	String d2v;
	        	Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
	        	d2v  = spinner2.getSelectedItem().toString();
	        	d2v_glob = d2v;
	            if (d.equals("dollars")) { titl1 = "USD/UAH"; }
	     	   	if (d.equals("руб.")) { titl1 = "RUB/UAH"; }
	     		if (d.equals("грн.")) { titl1 = "UAH/UAH"; }
	     		if (d.equals("euro")) { titl1 = "EUR/UAH"; }
	     		if (d2v.equals("dollars")) { titl2 = "USD/UAH"; }
	     		if (d2v.equals("руб.")) { titl2 = "RUB/UAH"; }
	     		if (d2v.equals("грн.")) { titl2 = "UAH/UAH"; }
	     		if (d2v.equals("euro")) { titl2 = "EUR/UAH"; }
	     		if (td.isChecked()) {dubtrip=2;}
	        	  else {dubtrip=1;}
	     	    result1_1_glob =((dubtrip * km_drove) * consumption)/100;
	            result1_2_glob =price*result1_1_glob;
	     		GetCurrency runCurr = new GetCurrency();
	     		ConnectivityManager net = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	     		if (net.getActiveNetworkInfo() != null) {runCurr.execute();}
	     		else {Toast.makeText(this, getResources().getText(R.string.error_net).toString(), Toast.LENGTH_LONG).show();}
	     		break;
	        default:
	          break;
	        }
			        
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
		float curr=1;
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
 		curr = rate_titl2 / rate_titl1;
        result1_1 = result1_1_glob;
        result1_2 = result1_2_glob*curr;	
        String d2v = d2v_glob;
        textViewResult1.setText(getResources().getText(R.string.result1_1).toString() + " " + result1_1 + " " + getResources().getText(R.string.result1_2).toString() + " " + result1_2 + " " + d2v);
    }
 }
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        Editor priceED = sPref.edit();
        //Editor compED = sPref.edit();
        priceED.putString(price, editText1.getText().toString());
        priceED.putString(comp, editText2.getText().toString());
        priceED.commit();
        //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedPrice = sPref.getString(price, "");
        String savedComp = sPref.getString(comp, "");
        editText1.setText(savedPrice);
        editText2.setText(savedComp);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
}
