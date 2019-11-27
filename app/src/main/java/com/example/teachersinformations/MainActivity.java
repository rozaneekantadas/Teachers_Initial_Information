package com.example.teachersinformations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText initial, name, department, contact, email;
    private Button submit;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Teachers Information");
        getSupportActionBar().setSubtitle("Daffodil International University");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("teacherInfo");

        initial = findViewById(R.id.initialEditText);
        name = findViewById(R.id.nameEditText);
        department = findViewById(R.id.departmentEditText);
        contact = findViewById(R.id.contactEditText);
        email = findViewById(R.id.emailEditText);

        submit = findViewById(R.id.submitBtn);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitBtn){
            saveData();
        }
    }

    public void saveData(){
        String tcrInitial, tcrName, tcrDepartment, tcrContact, tcrEmail;

        tcrInitial = initial.getText().toString().trim();
        tcrName = name.getText().toString().trim();
        tcrDepartment = department.getText().toString().trim();
        tcrContact = contact.getText().toString().trim();
        tcrEmail = email.getText().toString().trim();

        if(tcrInitial.isEmpty() || tcrName.isEmpty() || tcrContact.isEmpty() || tcrEmail.isEmpty() || tcrDepartment.isEmpty()){
            if(tcrInitial.isEmpty()){
                initial.setError("Fill Up Information");
            }

            if(tcrName.isEmpty()){
                name.setError("Fill Up Information");
            }

            if(tcrContact.isEmpty()){
                contact.setError("Fill Up Information");
            }

            if(tcrEmail.isEmpty()){
                email.setError("Fill Up Information");
            }

            if(tcrDepartment.isEmpty()){
                department.setError("Fill Up Information");
            }
        }

        else{
            SaveData teacher = new SaveData(tcrInitial, tcrName, tcrDepartment, tcrContact, tcrEmail);

            String key = databaseReference.push().getKey();

            databaseReference.child(key).setValue(teacher);

            Toast.makeText(MainActivity.this, "Saved Data", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Thank You For Your Contribution", Toast.LENGTH_LONG).show();

            initial.setText(null);
            name.setText(null);
            department.setText(null);
            contact.setText(null);
            email.setText(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
