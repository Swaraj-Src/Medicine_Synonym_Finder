package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
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
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Search_medicine extends AppCompatActivity {

    EditText medicine_search;
    Button search_button;
    ListView listView;
    public SharedPreferences sharedpreferences;
    String content,search_medicine_content;
    //public String medicine_name[];
    public static String[] medicine_name;
    public static String company[];
    public static String substitute[];
    public static String substitute_match[];
    public static String price[];
    public static String medicine_id[];
    public static String match_medicine[]=new String[10];
    public static String match_medicine_id[];
    public static String owner_id[];
    public static String medical_name[];
    //ArrayList<String> match_medicine = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);
        medicine_search = (EditText) findViewById(R.id.serch_remove);
        search_button = (Button) findViewById(R.id.remove_button);

        listView=(ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listView.getItemAtPosition(position).toString();
                Intent show_i1= new Intent(Search_medicine.this,search_medi_info.class);
                String medi_name=medicine_name[position];
                String medi_id=medicine_id[position];
                String comp=company[position];
                String sub=substitute[position];
                String price_medi=price[position];
                String owner=owner_id[position];
                Toast.makeText(Search_medicine.this,""+price_medi,Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle(10);
                bundle.putString("search_medi", medicine_search.getText().toString());
                bundle.putString("company", comp);
                bundle.putString("medicine", medi_name);
                bundle.putString("medi_id", medi_id);
                bundle.putString("substitute", sub);
                bundle.putString("price", price_medi);
                bundle.putString("owner_id", owner);
                show_i1.putExtras(bundle);
                startActivity(show_i1);

            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/search_medicine.php?medicine_name="+medicine_search.getText().toString();
                gettrans.execute(url1);
                try {
                    getJSON("http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/show_all_medi_info.php?medicine_name="+medicine_search.getText().toString());
                }catch (Exception e){
                    Toast.makeText(Search_medicine.this,""+e,Toast.LENGTH_LONG).show();
                }

            }
        });
    }

   //code for getting content of search medicine
   private class LedOnOff extends AsyncTask<String, Integer, String>  {
        private ProgressDialog progress = null;
        String out="";
        @Override
        protected String doInBackground(String... geturl) {
            try{
                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();
            }catch (Exception e){
                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Search_medicine.this, null, "Loading Medicine...");
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Search_medicine.this,result,Toast.LENGTH_LONG).show();
            
            search_medicine_content=result;
            progress.dismiss();
            super.onPostExecute(result);
        }
    }

    //code for getting all information of medicine
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

        medicine_name = new String[jsonArray.length()];
        medicine_id = new String[jsonArray.length()];
        company = new String[jsonArray.length()];
        price = new String[jsonArray.length()];
        substitute = new String[jsonArray.length()];
        owner_id = new String[jsonArray.length()];
        medical_name = new String[jsonArray.length()];


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            medicine_name[i] = obj.getString("medicine_name");
            medicine_id[i] = obj.getString("medicine_id");
            company[i] = obj.getString("company");
            price[i] = obj.getString("price");
            substitute[i] = obj.getString("substitute");
            owner_id[i] = obj.getString("owner_id");
            medical_name[i] = obj.getString("medical_name");

        }

        //String[] substitute_match = new String[content.split(",").length];

        //substitute_match=content.split(",");
        int m=0;
            for (int k1 = 0; k1 < medicine_name.length; k1++) {
                //match=0;
                //String mcontent = medicine_name[k1];
                String sep_content[] = medicine_name[k1].split(",");

                    for (int k2 = 0; k2 < sep_content.length; k2++) {
                        String match_content[] = search_medicine_content.split(",");
                        try {
                            for (int k3 = 0; k3 < match_content.length; k3++) {
                                if (sep_content[k2].contentEquals(match_content[k3])) {
                                    try {
                                        //match_medicine.add(medicine_name[k1]);
                                        match_medicine[m++]=medicine_name[k1];
                                        //m++;
                                        continue;
                                    }catch(Exception e){
                                        Toast.makeText(Search_medicine.this,"EXP 1:"+e,Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        }catch (Exception e){
                            Toast.makeText(Search_medicine.this,"EXP :"+e,Toast.LENGTH_LONG).show();
                        }

                    }

            }


        LevelAdapter1 leveladapter=new LevelAdapter1(Search_medicine.this,match_medicine,match_medicine,match_medicine);
        listView.setAdapter(leveladapter);


    }



}
