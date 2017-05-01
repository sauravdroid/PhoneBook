package com.example.sauravbiswas.phonebook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    int item = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Fragment fragment = null;
        item = getIntent().getIntExtra("fragment",1);
        if (item == 1)
            fragment = new StudentRegistration();
        else
            fragment = new TeacherRegistration();
        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.registration_main,fragment);
            ft.commit();
        }

    }
}
