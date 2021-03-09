package com.example.medicinesynonymfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search_update_medicine extends AppCompatActivity {

    ListView listView;
    public SharedPreferences sharedpreferences;

    public static String name[];
    public static String id1[];
    public static String company[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_update_medicine);
        listView = (ListView) findViewById(R.id.list_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listView.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("id", id1[position]);
                editor.commit();
                //Toast.makeText(view_user.this,""+item,Toast.LENGTH_LONG).show();
                Intent show_i1= new Intent(search_update_medicine.this,show_update_medicine.class);
                startActivity(show_i1);

            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("OWNER", "");
        //Toast.makeText(search_update_medicine.this,""+name,Toast.LENGTH_LONG).show();

        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_medicine.php?id="+name);
        }catch (Exception e){
            //Toast.makeText(view_user.this,""+e,Toast.LENGTH_LONG).show();
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
        id1 = new String[jsonArray.length()];
        company = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("medicine_name");
            id1[i] = obj.getString("medicine_id");
            company[i] = obj.getString("company");
        }
        LevelAdapter levelAdapter=new LevelAdapter(search_update_medicine.this,name,company,company);
        listView.setAdapter(levelAdapter);
    }
}
