package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class show_remove_medicine extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    TextView medicine_name,company,substitute,price;
    Button remove_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_remove_medicine);

        medicine_name=(TextView)findViewById(R.id.medicine_name);
        company=(TextView)findViewById(R.id.company_name);
        substitute=(TextView)findViewById(R.id.substitute);
        price=(TextView)findViewById(R.id.price);
        remove_medicine=(Button)findViewById(R.id.remove_medicine);
        remove_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                String name =sharedpreferences.getString("id", "");
                LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/remove_medicine.php?id="+name;
                gettrans.execute(url1);


            }
        });



        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("id", "");
        String owner =sharedpreferences.getString("OWNER", "");
        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_update_medicine.php?medi_name="+name+"&owner_id="+owner);
        }catch (Exception e){
            //Toast.makeText(show_remove_medicine.this,""+e,Toast.LENGTH_LONG).show();
        }
    }


    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            //heroes[i] = obj.getString("name");
            medicine_name.setText(obj.getString("medicine_name"));
            company.setText(obj.getString("company"));
            substitute.setText(obj.getString("substitute"));
            price.setText(obj.getString("price"));


        }
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
        Toast.makeText(delete_user_details.this,"NAME :"+name1,Toast.LENGTH_LONG).show();*/


    }
    //code for sending data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class LedOnOff extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out = "";
        int count = 0;

        @Override
        protected String doInBackground(String... geturl) {


            try {
                //	String url= ;


                HttpClient http = new DefaultHttpClient();
                HttpPost http_get = new HttpPost(geturl[0]);
                HttpResponse response = http.execute(http_get);
                HttpEntity http_entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            } catch (Exception e) {

                out = e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(show_remove_medicine.this, null,
                    "Updated Record...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(show_remove_medicine.this, "" + result, Toast.LENGTH_LONG).show();
            //transactions.setText("Transactions :"+count);
            progress.dismiss();
            Intent show_i1 = new Intent(show_remove_medicine.this, owner.class);
            startActivity(show_i1);
        }


    }
}
