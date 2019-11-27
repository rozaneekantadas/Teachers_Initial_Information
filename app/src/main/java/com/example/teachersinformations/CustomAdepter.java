package com.example.teachersinformations;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class CustomAdepter extends ArrayAdapter<SaveData> {

    Activity context;

    private  List<SaveData> teacherList;

    public CustomAdepter(Activity context, List<SaveData> teacherList) {
        super(context, R.layout.sample_layout, teacherList);
        this.context = context;
        this.teacherList = teacherList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout, null, true);

        SaveData teacher = teacherList.get(position);

        TextView initial, name, department, contact, email;

        initial = view.findViewById(R.id.initial);
        name = view.findViewById(R.id.name);
        department = view.findViewById(R.id.department);
        contact = view.findViewById(R.id.contact);
        email = view.findViewById(R.id.email);

        initial.setText(teacher.getInitial());
        name.setText(teacher.getName());
        department.setText(teacher.getDepartment());
        contact.setText(teacher.getContact());
        email.setText(teacher.getEmail());

        return view;
    }
}


