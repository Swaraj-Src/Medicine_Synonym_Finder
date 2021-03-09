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
import android.widget.ArrayAdapter;
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

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class remove_medical extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    TextView shop_name, owner_name, address, pincode, lati, langi, contact, email, password;
    Button delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_medical);

        shop_name=(TextView)findViewById(R.id.medical_name);
        owner_name=(TextView)findViewById(R.id.owner_name);
        address=(TextView)findViewById(R.id.address_medical);
        pincode=(TextView)findViewById(R.id.pincode);
        lati=(TextView)findViewById(R.id.lati);
        langi=(TextView)findViewById(R.id.langi);
        contact=(TextView)findViewById(R.id.contact);
        email=(TextView)findViewById(R.id.email);
        password=(TextView)findViewById(R.id.medical_password);
        delete_button=(Button)findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                String name =sharedpreferences.getString("id", "");

                LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/remove_medical.php?id="+name;
                gettrans.execute(url1);


            }
        });



        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("id", "");
        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_remove_medical.php?shopname="+name);
        }catch (Exception e){
            //Toast.makeText(remove_medical.this,""+e,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
            shop_name.setText(obj.getString("shop_name"));
            owner_name.setText(obj.getString("owner_name"));
            address.setText(obj.getString("address"));
            pincode.setText(obj.getString("pincode"));
            lati.setText(obj.getString("lati"));
            langi.setText(obj.getString("langi"));
            contact.setText(obj.getString("contact"));
            email.setText(obj.getString("email"));
            password.setText(obj.getString("password"));

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
            progress = ProgressDialog.show(remove_medical.this, null,
                    "Deleting Record...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Toast.makeText(remove_medical.this, "" + result, Toast.LENGTH_LONG).show();
            //transactions.setText("Transactions :"+count);
            progress.dismiss();
            Intent show_i1 = new Intent(remove_medical.this, admin.class);
            startActivity(show_i1);
        }


    }


}