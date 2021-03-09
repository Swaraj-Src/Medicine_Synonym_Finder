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

public class show_buy_medicine extends AppCompatActivity {
    ListView listview;
    SharedPreferences sharedpreferences;
    public  static  String shop_name[];
    public static String shop_id[];
    public static String address[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buy_medicine);
       listview=(ListView)findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listview.getItemAtPosition(position).toString();
                Intent show_i1= new Intent(show_buy_medicine.this,show_buy_medi_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", shop_id[position]);
                show_i1.putExtras(bundle);
                startActivity(show_i1);
            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String name =sharedpreferences.getString("OWNER", "");

        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/search_buy_item.php?id="+name);
        }catch (Exception e){
            //Toast.makeText(show_update_medical.this,""+e,Toast.LENGTH_LONG).show();
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

        shop_name = new String[jsonArray.length()];
        shop_id = new String[jsonArray.length()];
        address = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            shop_name[i] = obj.getString("name");
            shop_id[i] = obj.getString("id");
            address[i] = obj.getString("address");

        }
        LevelAdapter leveladapter=new LevelAdapter(show_buy_medicine.this,shop_name,address,address);
        listview.setAdapter(leveladapter);


    }
}
