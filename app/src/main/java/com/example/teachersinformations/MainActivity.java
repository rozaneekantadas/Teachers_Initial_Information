package com.example.teachersinformations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText initial, name, department, contact, email, designation;
    private Button submit,choose;
    private ImageView showImg;
    private Uri imgUri;
    private String tcrInitial, tcrName, tcrDepartment, tcrContact, tcrEmail, tcrDesignation;

    private int imgOpen = 0;

    private static final int IMAGE_REQUEST = 1;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Teachers Initial Information");
        getSupportActionBar().setSubtitle("Daffodil International University");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("teacherInfo");
        storageReference = FirebaseStorage.getInstance().getReference();

        initial = findViewById(R.id.initialEditText);
        name = findViewById(R.id.nameEditText);
        department = findViewById(R.id.departmentEditText);
        contact = findViewById(R.id.contactEditText);
        email = findViewById(R.id.emailEditText);
        designation = findViewById(R.id.designationEditText);
        showImg = findViewById(R.id.showImage);

        choose = findViewById(R.id.chooseButton);
        submit = findViewById(R.id.submitBtn);

        choose.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitBtn){
            saveData();
        }

        if(view.getId() == R.id.chooseButton){
            OpenFile();
        }
    }

    public void saveData(){

        tcrInitial = initial.getText().toString().trim();
        tcrName = name.getText().toString().trim();
        tcrDepartment = department.getText().toString().trim();
        tcrContact = contact.getText().toString().trim();
        tcrEmail = email.getText().toString().trim();
        tcrDesignation = designation.getText().toString().trim();

        if(tcrInitial.isEmpty() || tcrName.isEmpty() || tcrContact.isEmpty() || tcrEmail.isEmpty() || tcrDepartment.isEmpty() || tcrDesignation.isEmpty() || imgOpen == 0){
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

            if(tcrDesignation.isEmpty()){
                designation.setError("Fill Up Information");
            }

            if(imgOpen == 0){
                Toast.makeText(getApplicationContext(),"Choose a picture", Toast.LENGTH_LONG).show();
            }
        }

        else{

            String imageName = tcrInitial;

            StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imgUri));

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadUri = uriTask.getResult();

                            SaveData teacher = new SaveData(tcrInitial, tcrName, tcrDepartment, tcrContact, tcrEmail, tcrDesignation, downloadUri.toString());

                            String key = databaseReference.push().getKey();

                            databaseReference.child(key).setValue(teacher);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(getApplicationContext(),"Image is not Save Successfully", Toast.LENGTH_LONG).show();
                        }
                    });

            Toast.makeText(MainActivity.this, "Saved Data", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Thank You For Your Contribution", Toast.LENGTH_LONG).show();

            initial.setText(null);
            name.setText(null);
            department.setText(null);
            contact.setText(null);
            email.setText(null);
            designation.setText(null);
            showImg.setImageResource(R.drawable.icon_photo);
            imgOpen = 0;
        }
    }

    public String getFileExtension(Uri imgUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgUri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void OpenFile(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
        imgOpen = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(showImg);
        }
    }
}
