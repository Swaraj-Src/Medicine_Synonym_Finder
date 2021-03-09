package com.example.medicinesynonymfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class view_transa_info extends AppCompatActivity {
    TextView name,address,contact,quantity,medicine_name,buy_id,medical_id;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transa_info);
        name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        contact=(TextView)findViewById(R.id.contact);
        quantity=(TextView)findViewById(R.id.quantity);
        medicine_name=(TextView)findViewById(R.id.medicine_name);
        medical_id=(TextView)findViewById(R.id.medical_id);
        ok=(Button)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(view_transa_info.this,admin.class);
                startActivity(i1);
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        name.setText(extras.getString("name"));
        address.setText(extras.getString("address"));
        contact.setText(extras.getString("contact"));
        quantity.setText(extras.getString("quantity"));
        medicine_name.setText(extras.getString("medicine_name"));
        medical_id.setText(extras.getString("medical_id"));



    }
}
