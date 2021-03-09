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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class buy_medicine extends AppCompatActivity {
    EditText name_user, address, contact, quantity;
    TextView name_medicine;
    RadioButton paymode;
    RadioGroup radioGroup;
    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);



        name_user=(EditText)findViewById(R.id.full_name);
        address=(EditText)findViewById(R.id.address);
        contact=(EditText)findViewById(R.id.contact);
        quantity=(EditText)findViewById(R.id.quantity);
        name_medicine=(TextView) findViewById(R.id.name_medicine);
        buy=(Button)findViewById(R.id.buy_medicine);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("medicine");
        final String medi_id = bundle.getString("medi_id");
        name_medicine.setText(title);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                paymode = (RadioButton) findViewById(selectedId);

                Toast.makeText(buy_medicine.this, paymode.getText(), Toast.LENGTH_SHORT).show();





                if(name_user.length() > 0) {
                    if(address.length()>0){
                        if(contact.length()>0){
                            if(quantity.length()>0){


                                LedOnOff gettrans = new LedOnOff();
                                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/buy_medicine.php?name="+ URLEncoder.encode(name_user.getText().toString())
                                        +"&address="+URLEncoder.encode(address.getText().toString())
                                        +"&contact="+URLEncoder.encode(contact.getText().toString())
                                        +"&quantity="+URLEncoder.encode(quantity.getText().toString())
                                        +"&medicine_name="+URLEncoder.encode(name_medicine.getText().toString())
                                        +"&medical_id="+URLEncoder.encode(medi_id);


                                gettrans.execute(url1);



                            }else{
                                quantity.setError("valid Quatity");
                                quantity.requestFocus(20);
                            }

                        }else{
                            contact.setError("Enter valid Contact");

                            contact.requestFocus(20);
                        }

                    }else{
                        address.setError("Enter valid Address");
                        address.requestFocus(20);
                    }

                }else {
                    name_user.setError("Enter valid Name");
                    name_user.requestFocus(20);
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
            progress = ProgressDialog.show(buy_medicine.this, null,
                    "Updating Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Toast.makeText(add_medical.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();

            Intent register_i1 = new Intent(buy_medicine.this, user.class);
            startActivity(register_i1);
            finish();
        }



    }
}
