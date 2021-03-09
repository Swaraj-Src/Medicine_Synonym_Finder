package com.example.medicinesynonymfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class show_remove_medical extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_remove_medical);
        listView=(ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*String item=listView.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ITEM", item);
                editor.commit();*/

                //Toast.makeText(show_remove_medical.this,""+listView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                String item=listView.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("id", listView.getItemAtPosition(position).toString());
                editor.commit();
                Intent show_i1= new Intent(show_remove_medical.this,remove_medical.class);
                startActivity(show_i1);

            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("ITEM", "");
        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/search_remove_medical.php?shop_name="+name);
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

        String[] shop_name = new String[jsonArray.length()];
        String[] shop_id = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            shop_name[i] = obj.getString("shop_name");
            shop_id[i] = obj.getString("shop_id");



        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
        //Toast.makeText(user_details.this,"NAME :"+name1,Toast.LENGTH_LONG).show();
        LevelAdapter leveladapter=new LevelAdapter(show_remove_medical.this,shop_id,shop_name,shop_name);
        listView.setAdapter(leveladapter);


    }
}
