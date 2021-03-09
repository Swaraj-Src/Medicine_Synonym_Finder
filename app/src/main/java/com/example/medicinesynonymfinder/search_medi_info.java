package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search_medi_info extends AppCompatActivity {
    TextView search_medicine,medi_name,content1,price1,company1;
    Button get_address,place_order,call_medical;
    public static String[] shop_name;
    public static String[] owner_name;
    public static String[] address;
    public static String[] pincode;
    public static String[] lati;
    public static String[] langi;
    public static String[] contact;
    public static String[] email;
    public static String[] password;
    public static String medi_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medi_info);


        search_medicine=(TextView)findViewById(R.id.search_medi);
        medi_name=(TextView)findViewById(R.id.medi);
        content1=(TextView)findViewById(R.id.content);
        price1=(TextView)findViewById(R.id.price);
        company1=(TextView)findViewById(R.id.company);
        get_address=(Button)findViewById(R.id.get_address);
        place_order=(Button)findViewById(R.id.place_order);
        call_medical=(Button)findViewById(R.id.call_medical);
        //code for set data from bundle to Textview
            Bundle extras = getIntent().getExtras();
            search_medicine.setText(extras.getString("search_medi"));
            medi_name.setText(extras.getString("medicine"));
            content1.setText(extras.getString("substitute"));
            price1.setText(extras.getString("price"));
            company1.setText(extras.getString("company"));

        //code for medical information
        medi_id=extras.getString("owner_id");
        Toast.makeText(search_medi_info.this,""+medi_id,Toast.LENGTH_LONG).show();


        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_remove_medical.php?shopname="+medi_id);
        }catch (Exception e){
            Toast.makeText(search_medi_info.this,""+e,Toast.LENGTH_LONG).show();
        }
        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(search_medi_info.this,medical_location.class);
                Bundle bundle = new Bundle(2);
                bundle.putString("lati", lati[0]);
                bundle.putString("langi", langi[0]);
                bundle.putString("shop_name", shop_name[0]);
                add.putExtras(bundle);
                startActivity(add);
            }
        });

        //code for call medical
        call_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact[0]));
                startActivity(intent);
            }
        });
        //code for place order
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(search_medi_info.this,buy_medicine.class);
                Bundle bundle = new Bundle(12);
                bundle.putString("shop_name", shop_name[0]);
                bundle.putString("owner_name", owner_name[0]);
                bundle.putString("address", address[0]);
                bundle.putString("pincode", pincode[0]);
                bundle.putString("lati", lati[0]);
                bundle.putString("langi", langi[0]);
                bundle.putString("contact", contact[0]);
                bundle.putString("email", email[0]);
                bundle.putString("password", password[0]);
                bundle.putString("medicine", medi_name.getText().toString());
                bundle.putString("medi_id", medi_id);

                i.putExtras(bundle);
                startActivity(i);

            }
        });

    }


    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            private ProgressDialog progress = null;

            @Override
            protected void onPreExecute() {
                progress = ProgressDialog.show(search_medi_info.this, null,
                        "Updating Information...");
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progress.dismiss();
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
        owner_name = new String[jsonArray.length()];
        address = new String[jsonArray.length()];
        pincode = new String[jsonArray.length()];
        lati = new String[jsonArray.length()];
        langi = new String[jsonArray.length()];
        contact = new String[jsonArray.length()];
        email = new String[jsonArray.length()];
        password = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            shop_name[i] = obj.getString("shop_name");
            owner_name[i] = obj.getString("owner_name");
            address[i] = obj.getString("address");
            pincode[i] = obj.getString("pincode");
            lati[i] = obj.getString("lati");
            langi[i] = obj.getString("langi");
            contact[i] = obj.getString("contact");
            email[i] = obj.getString("email");
            password[i] = obj.getString("password");



        }
    }
}
