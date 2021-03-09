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
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class add_medicine extends AppCompatActivity {
    EditText medicine_name,company,substitute,price;
    Button register_medicine;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        medicine_name=(EditText)findViewById(R.id.medicine_name);
        company=(EditText)findViewById(R.id.company_name);
        substitute=(EditText)findViewById(R.id.substitute);
        price=(EditText)findViewById(R.id.price);


        register_medicine=(Button)findViewById(R.id.register_medicine);
        sharedPreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        final String owner_id =sharedPreferences.getString("OWNER", "");
        final String medi_name =sharedPreferences.getString("MEDI_NAME", "");
        register_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(add_medicine.this," "+a+" "+b+" "+c,Toast.LENGTH_LONG).show();
                if(medicine_name.length() > 0) {
                    if(company.length()>0){
                        if(substitute.length()>0){
                            if(price.length()>0){


                                              LedOnOff gettrans = new LedOnOff();
                                                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/medicine_register.php?medicine_name="+ URLEncoder.encode(medicine_name.getText().toString())
                                                        +"&company="+URLEncoder.encode(company.getText().toString())
                                                        +"&substitute="+URLEncoder.encode(substitute.getText().toString())
                                                        +"&price="+URLEncoder.encode(price.getText().toString())
                                                        +"&owner_id="+URLEncoder.encode(owner_id)
                                                        +"&medi_name="+URLEncoder.encode(medi_name);

                                                gettrans.execute(url1);



                            }else{
                                price.setError("valid price");
                                price.requestFocus(20);
                            }

                        }else{
                            substitute.setError("Enter valid address");

                             substitute.requestFocus(20);
                        }

                    }else{
                        company.setError("Enter valid Owner Name");
                        company.requestFocus(20);
                    }

                }else {
                    medicine_name.setError("Enter valid Medical Name");
                    medicine_name.requestFocus(20);
                }


            }
        });
    }


    //code for send data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class LedOnOff extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;


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
            progress = ProgressDialog.show(add_medicine.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(add_medicine.this, "Medicine successfully added"+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            Intent register_i1 = new Intent(add_medicine.this, owner.class);
            startActivity(register_i1);
            finish();
        }



    }
}
