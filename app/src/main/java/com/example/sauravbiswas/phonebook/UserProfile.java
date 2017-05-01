package com.example.sauravbiswas.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView name, email, department, roll, textEmail, textRoll;
    Button btnDelete, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = (TextView) findViewById(R.id.text_name);
        email = (TextView) findViewById(R.id.text_email);
        department = (TextView) findViewById(R.id.text_department);
        roll = (TextView) findViewById(R.id.text_roll);
        textEmail = (TextView) findViewById(R.id.textview_email);
        textRoll = (TextView) findViewById(R.id.textview_roll);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        Intent intent = getIntent();
        roll.setText(intent.getStringExtra("roll"));
        if (intent.getStringExtra("user").equalsIgnoreCase("teacher")) {
            email.setVisibility(View.INVISIBLE);
            textEmail.setVisibility(View.INVISIBLE);
            textRoll.setText("Email");
            roll.setText(intent.getStringExtra("email"));
        }
        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        department.setText(intent.getStringExtra("department"));
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_delete:
                final RequestQueue queue = Volley.newRequestQueue(this);
                final String url = "http://10.0.2.2:8000/user/delete/student";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Toast.makeText(getApplicationContext(), "Response is: " + response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error Occured " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        // the POST parameters:
                        params.put("username", getIntent().getStringExtra("username"));
                        return params;
                    }
                };
                queue.add(postRequest);
                break;
            case R.id.btn_edit:
                break;
        }
    }
}
