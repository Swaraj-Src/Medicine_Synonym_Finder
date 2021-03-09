package com.example.medicinesynonymfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class admin extends AppCompatActivity {
    Button add_medical, view_user,remove_medical,view_medical,update_medical,logout,transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        add_medical = (Button) findViewById(R.id.add_medical);
        view_user = (Button) findViewById(R.id.view_user);
        remove_medical = (Button) findViewById(R.id.delete_medical);
        view_medical = (Button) findViewById(R.id.view_medical);
        update_medical = (Button) findViewById(R.id.update_medical);
        transaction = (Button) findViewById(R.id.transactions);

        logout = (Button) findViewById(R.id.logout);

        add_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, add_medical.class);
                startActivity(i1);
            }
        });
        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, view_user.class);
                startActivity(i1);
            }
        });
        remove_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, show_remove_medical.class);
                startActivity(i1);
            }
        });
        view_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, view_medical.class);
                startActivity(i1);
            }
        });
        update_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, show_update_medical.class);
                startActivity(i1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, home.class);
                startActivity(i1);
                finish();
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin.this, view_transaction.class);
                startActivity(i1);

            }
        });
    }

}