package com.example.medicinesynonymfinder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class show_buy_medi_info extends AppCompatActivity {
    TextView name_user, address, contact, quantity, name_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buy_medi_info);
        name_user=(TextView)findViewById(R.id.full_name);
        address=(TextView)findViewById(R.id.address);
        contact=(TextView)findViewById(R.id.contact);
        quantity=(TextView)findViewById(R.id.quntity);
        name_medicine=(TextView)findViewById(R.id.name_medicine);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("id");
        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_buy_medicine.php?id="+title);
        }catch (Exception e){
            Toast.makeText(show_buy_medi_info.this,""+e,Toast.LENGTH_LONG).show();
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
            name_user.setText(obj.getString("name"));
            address.setText(obj.getString("address"));
            contact.setText(obj.getString("contact"));
            quantity.setText(obj.getString("quantity"));
            name_medicine.setText(obj.getString("medicine_name"));

        }

    }
}
