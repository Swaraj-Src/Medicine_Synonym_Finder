package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class add_medical extends AppCompatActivity {
    EditText medical_name, owner_name, address, pincode, lati, langi,contact,email,pass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical);
        medical_name=(EditText)findViewById(R.id.medical_name);
        owner_name=(EditText)findViewById(R.id.owner_name);
        address=(EditText)findViewById(R.id.address_medical);
        pincode=(EditText)findViewById(R.id.pincode);
        lati=(EditText)findViewById(R.id.lati);
        langi=(EditText)findViewById(R.id.langi);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.medical_password);

        register=(Button)findViewById(R.id.register_medical);

        //code for register medical
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medical_name.length() > 0) {
                    if(owner_name.length()>0){
                        if(address.length()>0){
                            if(pincode.length()==6){
                                if(lati.length()>0){
                                    if(langi.length()>0){
                                        if(contact.length()==10){
                                            if(pass.length()>0){

                                                LedOnOff gettrans = new LedOnOff();
                                                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/medical_register.php?shop_name="+ URLEncoder.encode(medical_name.getText().toString())
                                                        +"&owner_name="+URLEncoder.encode(owner_name.getText().toString())
                                                        +"&address="+URLEncoder.encode(address.getText().toString())
                                                        +"&pincode="+URLEncoder.encode(pincode.getText().toString())
                                                        +"&lati="+URLEncoder.encode(lati.getText().toString())
                                                        +"&langi="+URLEncoder.encode(langi.getText().toString())
                                                        +"&contact="+URLEncoder.encode(contact.getText().toString())
                                                        +"&email="+URLEncoder.encode(email.getText().toString())
                                                        +"&password="+URLEncoder.encode(pass.getText().toString())
                                                        +"&user_name="+URLEncoder.encode(email.getText().toString());
                                                gettrans.execute(url1);




                                            }else{
                                                pass.setError("Enter valid password");
                                                pass.requestFocus(20);
                                            }

                                        }else{
                                            contact.setError("Enter valid Contact Number");
                                            contact.requestFocus(20);
                                        }

                                    }else{
                                        langi.setError("Enter valid value");
                                        langi.requestFocus(20);
                                    }
                                }else{
                                    lati.setError("Enter valid value");
                                    lati.requestFocus(20);
                                }

                            }else{
                                pincode.setError("pincode length must be 6 digit");
                                pincode.requestFocus(20);
                            }

                        }else{
                            address.setError("Enter valid address");

                            address.requestFocus(20);
                        }

                    }else{
                        owner_name.setError("Enter valid Owner Name");
                        owner_name.requestFocus(20);
                    }

                }else {
                    medical_name.setError("Enter valid Medical Name");
                    medical_name.requestFocus(20);
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
            progress = ProgressDialog.show(add_medical.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(add_medical.this, "Register Successful"+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(contact.getText().toString(),null,"user name: "+email.getText().toString()+" password: "+pass.getText().toString(),null,null);
            Intent register_i1 = new Intent(add_medical.this, admin.class);
            startActivity(register_i1);
            finish();
        }



    }


}
