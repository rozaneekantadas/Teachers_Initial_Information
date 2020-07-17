package com.example.teachersinformations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class PopInfo extends AppCompatActivity {

    TextView crossBtn, initial, name, designation, department, email, phone ;
    ImageView imageView;
    String tcrinitial, tcrname, tcrdesignation, tcrdepartment, tcremail, tcrphone, tcrImageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_info);

        crossBtn = findViewById(R.id.closebtn);
        initial = findViewById(R.id.popupinitial);
        name = findViewById(R.id.popupname);
        designation = findViewById(R.id.popupdesignation);
        department = findViewById(R.id.popupdepartment);
        email = findViewById(R.id.popupemail);
        phone = findViewById(R.id.popupcontact);
        imageView = findViewById(R.id.popimgView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        tcrinitial = extras.getString("initial");
        tcrname = extras.getString("name");
        tcrdesignation = extras.getString("designation");
        tcrdepartment = extras.getString("department");
        tcrphone = extras.getString("phone");
        tcremail = extras.getString("email");
        tcrImageurl = extras.getString("imageUrl");

        initial.setText(tcrinitial);
        name.setText(tcrname);
        designation.setText(tcrdesignation);
        department.setText(tcrdepartment);
        email.setText(tcremail);
        phone.setText(tcrphone);
        Picasso.get().load(tcrImageurl).into(imageView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.7) );

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
