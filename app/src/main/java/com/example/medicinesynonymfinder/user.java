package com.example.medicinesynonymfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user extends AppCompatActivity {
    Button register,autologin,logout,search_medicine,find_Synonym_Medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        search_medicine=(Button)findViewById(R.id.search);
        find_Synonym_Medicine=(Button)findViewById(R.id.find);
        logout=(Button)findViewById(R.id.logout);
        search_medicine=(Button)findViewById(R.id.search);
        find_Synonym_Medicine=(Button)findViewById(R.id.find);
        //code for register module
        search_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1_register=new Intent(user.this,Search_medicine.class);
                startActivity(i1_register);
            }
        });
        find_Synonym_Medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1_register=new Intent(user.this,Search_medicine.class);
                startActivity(i1_register);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1_register=new Intent(user.this,home.class);
                startActivity(i1_register);
                finish();

            }
        });


    }
}
