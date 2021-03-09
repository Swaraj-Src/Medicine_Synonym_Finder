package com.example.medicinesynonymfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class search_remove_medical extends AppCompatActivity {
    EditText remove_medical_search;
    Button remove_button;
    public SharedPreferences sharedpreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_remove_medical);
        remove_medical_search=(EditText)findViewById(R.id.serch_remove);
        remove_button=(Button)findViewById(R.id.remove_button);
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ITEM", remove_medical_search.getText().toString());
                editor.commit();
                //Toast.makeText(search_remove_medical.this,""+item,Toast.LENGTH_LONG).show();
                Intent show_i1= new Intent(search_remove_medical.this,show_remove_medical.class);
                startActivity(show_i1);
            }
        });
        sharedpreferences = getSharedPreferences("MYPREF",Context.MODE_PRIVATE);
    }
}
