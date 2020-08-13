package com.example.teachersinformations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowData extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    private CustomAdepter customAdepter;
    private List<SaveData> teacherList;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        getSupportActionBar().setTitle("Teachers Initial Information");
        getSupportActionBar().setSubtitle("Daffodil International University");

        databaseReference = FirebaseDatabase.getInstance().getReference("teacherInfo");
        databaseReference.keepSynced(true);

        teacherList = new ArrayList<>();

        customAdepter = new CustomAdepter(ShowData.this, teacherList);

        listView = findViewById(R.id.listViewId);
        searchView = findViewById(R.id.searchViewId);
        progressBar = findViewById(R.id.progressBarId);

        databaseReference.addValueEventListener(valueEventListener);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Query query = databaseReference.orderByChild("initial").startAt(s).endAt(s+"\uf8ff");
                query.addListenerForSingleValueEvent(valueEventListener);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaveData value = teacherList.get(i);
                Intent intent = new Intent(ShowData.this, PopInfo.class);

                Bundle extras = new Bundle();
                extras.putString("name", value.getName());
                extras.putString("designation", value.getDesignation());
                extras.putString("department", value.getDepartment());
                extras.putString("email", value.getEmail());
                extras.putString("phone", value.getContact());
                extras.putString("initial", value.getInitial());
                extras.putString("imageUrl", value.getImage());
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            teacherList.clear();

            for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){

                SaveData teacher = snapshot1.getValue(SaveData.class);
                teacherList.add(teacher);
            }

            Collections.sort(teacherList, new Comparator<SaveData>() {
                @Override
                public int compare(SaveData saveData, SaveData t1) {
                    return saveData.getInitial().compareTo(t1.getInitial());
                }
            });

            listView.setAdapter(customAdepter);

            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.contribution){
            Intent intent = new Intent(ShowData.this, MainActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(ShowData.this, PopUpAbout.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
