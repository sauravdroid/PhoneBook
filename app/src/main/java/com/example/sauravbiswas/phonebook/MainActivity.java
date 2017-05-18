package com.example.sauravbiswas.phonebook;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editUsername, editPassword;
    Button btnLogin;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("user_detail", 0); // 0 - for private mode
        editor = pref.edit();
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        if (pref.getBoolean("isLoggedIn", false)) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogin:
                loginUser();
                break;
        }
        ;
    }

    public void loginUser() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://10.0.2.2:8000/user/login";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success")) {
                        JSONObject user = jsonResponse.getJSONObject("user");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        Boolean isAdmin = user.getBoolean("admin");
                        Toast.makeText(getApplicationContext(), "Successful Login "+isAdmin, Toast.LENGTH_SHORT).show();
                        saveUserDetails(username, email,isAdmin);
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Username or password or not authorized", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("username", editUsername.getText().toString());
                params.put("password", editPassword.getText().toString());
                return params;
            }
        };
        queue.add(request);
    }

    public static void logoutUser() {
        deleteUserDetails();
    }

    public static void saveUserDetails(String username, String email,Boolean isAdmin) {
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("isAdmin", isAdmin);
        editor.commit();
    }

    public static void deleteUserDetails() {
        editor.clear();
        editor.commit();
    }
}
