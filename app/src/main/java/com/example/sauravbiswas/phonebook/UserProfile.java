package com.example.sauravbiswas.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView name, email, department, roll, textEmail, textRoll,phone_no;
    Button btnDelete, btnEdit;
    CardView displayCard, updateCard;
    Boolean isUpdateClicked = false;
    EditText editFirstName,editLastName,editEmail,editRoll,editDepartment,editPhoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sample saurav = new sample(23,"Saurav Biswas",true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        displayCard = (CardView) findViewById(R.id.cardView);
        updateCard = (CardView) findViewById(R.id.card_update);
        name = (TextView) findViewById(R.id.text_name);
        email = (TextView) findViewById(R.id.text_email);
        department = (TextView) findViewById(R.id.text_department);
        roll = (TextView) findViewById(R.id.text_roll);
        textEmail = (TextView) findViewById(R.id.textview_email);
        textRoll = (TextView) findViewById(R.id.textview_roll);
        phone_no = (TextView)findViewById(R.id.text_phone_no);

        editFirstName = (EditText)findViewById(R.id.edit_first_name);
        editLastName = (EditText)findViewById(R.id.edit_last_name);
        editDepartment = (EditText)findViewById(R.id.edit_department);
        editRoll = (EditText)findViewById(R.id.edit_roll);
        editEmail = (EditText)findViewById(R.id.edit_email);
        editPhoneNo = (EditText)findViewById(R.id.edit_phone_no);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        checkAdmin();
        Intent intent = getIntent();
        roll.setText(intent.getStringExtra("roll"));
        if (intent.getStringExtra("user").equalsIgnoreCase("teacher")) {
            email.setVisibility(View.INVISIBLE);
            textEmail.setVisibility(View.INVISIBLE);
            textRoll.setText("Email");
            roll.setText(intent.getStringExtra("email"));

        }
        name.setText(intent.getStringExtra("name"));
        phone_no.setText(intent.getStringExtra("phone_no"));
        editFirstName.setText(intent.getStringExtra("first_name"));
        editLastName.setText(intent.getStringExtra("last_name"));

        email.setText(intent.getStringExtra("email"));
        editEmail.setText(intent.getStringExtra("email"));

        department.setText(intent.getStringExtra("department"));
        editDepartment.setText(intent.getStringExtra("department"));
        editRoll.setText(intent.getStringExtra("roll"));
        editPhoneNo.setText(intent.getStringExtra("phone_no"));
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        final RequestQueue queue = Volley.newRequestQueue(this);
        switch (id) {
            case R.id.btn_delete:
                if (!isUpdateClicked) {
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
                        protected Map<String, String> getParams() { //Adds the data for the post request
                            Map<String, String> params = new HashMap<>();
                            // the POST parameters:
                            params.put("username", getIntent().getStringExtra("username"));
                            return params;
                        }
                    };
                    queue.add(postRequest);
                } else {
                    isUpdateClicked = false;
                    normalProfile();
                }
                break;
            case R.id.btn_edit:
                if (!isUpdateClicked) {
                    isUpdateClicked = true;
                    updateProfile();
                } else {
                    //Send Volley post request to update profile
                    final String url = "http://10.0.2.2:8000/user/edit";
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    Toast.makeText(getApplicationContext(), "Response is: " + response, Toast.LENGTH_SHORT).show();
                                    finish();
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
                            params.put("email", editEmail.getText().toString());
                            params.put("first_name", editFirstName.getText().toString());
                            params.put("last_name", editLastName.getText().toString());
                            params.put("department", editDepartment.getText().toString());
                            params.put("phone_no", editPhoneNo.getText().toString());
                            params.put("roll", editRoll.getText().toString());
                            params.put("user_type", getIntent().getStringExtra("user"));
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
                break;
        }
    }

    public void updateProfile() {
        displayCard.setVisibility(View.INVISIBLE);
        updateCard.setVisibility(View.VISIBLE);
        btnEdit.setText("Done");
        btnDelete.setText("Discard");
        if(getIntent().getStringExtra("user").equalsIgnoreCase("teacher")){
            editRoll.setVisibility(View.INVISIBLE);
        }else{
            editRoll.setVisibility(View.VISIBLE);
        }
    }

    public void normalProfile() {
        displayCard.setVisibility(View.VISIBLE);
        updateCard.setVisibility(View.INVISIBLE);
        btnEdit.setText("Edit");
        btnDelete.setText("Delete");
    }
    public void checkAdmin(){
        if(!MainActivity.pref.getBoolean("isAdmin",false)){
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

}
