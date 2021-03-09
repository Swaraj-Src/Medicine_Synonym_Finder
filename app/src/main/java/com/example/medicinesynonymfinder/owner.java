package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class owner extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    Button add_medicine,update_medicine, remove_medicine, update_profile,logout, my_oorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        add_medicine=(Button)findViewById(R.id.add_medicine);
        update_medicine=(Button)findViewById(R.id.update_medicine);
        remove_medicine=(Button)findViewById(R.id.delete_medicine);
        update_profile=(Button)findViewById(R.id.update_owner);
        my_oorder=(Button)findViewById(R.id.my_orders);
        logout=(Button)findViewById(R.id.logout);

        add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this, add_medicine.class);
                startActivity(i1);
            }
        });

        update_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(owner.this, search_update_medicine.class);
                startActivity(i2);
            }
        });

        remove_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this, search_remove_medicine.class);
                startActivity(i1);
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this, update_profile.class);
                startActivity(i1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this, home.class);
                startActivity(i1);
                finish();
            }
        });
        my_oorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this, show_buy_medicine.class);
                startActivity(i1);
                finish();
            }
        });

        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("OWNER", "");

        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_remove_medical.php?shopname="+name);
        }catch (Exception e){
            Toast.makeText(owner.this,""+e,Toast.LENGTH_LONG).show();
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
        String[] medicine_name = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            medicine_name[i] = obj.getString("shop_name");
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("MEDI_NAME",medicine_name[0]);
        editor.commit();




    }
}
