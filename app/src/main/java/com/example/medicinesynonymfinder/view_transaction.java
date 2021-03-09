package com.example.medicinesynonymfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class view_transaction extends AppCompatActivity {

    ListView listView;
    SharedPreferences sharedpreferences;
    public static String name[];
    public static String address[];
    public static String contact[];
    public static String quantity[];
    public static String medicine_name[];
    public static String buy_id[];
    public static String medical_id[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        listView=(ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent show_i1= new Intent(view_transaction.this,view_transa_info.class);

                Bundle bundle = new Bundle(10);
                bundle.putString("name", name[position]);
                bundle.putString("address", address[position]);
                bundle.putString("contact", contact[position]);
                bundle.putString("quantity", quantity[position]);
                bundle.putString("medicine_name", medicine_name[position]);
                bundle.putString("buy_id", buy_id[position]);
                bundle.putString("medical_id", medical_id[position]);



                show_i1.putExtras(bundle);
                startActivity(show_i1);

            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("ITEM", "");
        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/view_transaction.php");
        }catch (Exception e){
            //Toast.makeText(show_remove_medical.this,""+e,Toast.LENGTH_LONG).show();
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


        name = new String[jsonArray.length()];
        address = new String[jsonArray.length()];
        contact = new String[jsonArray.length()];
        quantity = new String[jsonArray.length()];
        medicine_name = new String[jsonArray.length()];
        buy_id = new String[jsonArray.length()];
        medical_id = new String[jsonArray.length()];



        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("name");
            address[i] = obj.getString("address");
            contact[i] = obj.getString("contact");
            quantity[i] = obj.getString("quantity");
            medicine_name[i] = obj.getString("medicine_name");
            buy_id[i] = obj.getString("buy_id");
            medical_id[i] = obj.getString("medical_id");




        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
        //Toast.makeText(user_details.this,"NAME :"+name1,Toast.LENGTH_LONG).show();
        LevelAdapter leveladapter=new LevelAdapter(view_transaction.this,name,medicine_name,medicine_name);
        listView.setAdapter(leveladapter);


    }
}
