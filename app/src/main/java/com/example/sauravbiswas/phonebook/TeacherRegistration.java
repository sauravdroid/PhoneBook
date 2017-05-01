package com.example.sauravbiswas.phonebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegistration extends Fragment {

    EditText firstName, lastName, email, password, username, department;
    Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_registration,container,false);
        try {
            firstName = (EditText) view.findViewById(R.id.edit_student_first_name);
            lastName = (EditText) view.findViewById(R.id.edit_student_last_name);
            email = (EditText) view.findViewById(R.id.edit_student_email);
            password = (EditText) view.findViewById(R.id.edit_student_password);
            username = (EditText) view.findViewById(R.id.edit_student_username);
            department = (EditText) view.findViewById(R.id.edit_student_department);
            btnRegister = (Button) view.findViewById(R.id.btn_register);
            final RequestQueue queue = Volley.newRequestQueue(getActivity());
            final String url = "http://10.0.2.2:8000/user/add/teacher";
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request a string response from the provided URL.
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    Toast.makeText(getActivity(), "Response is: " + response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error Occured " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            // the POST parameters:
                            params.put("username", username.getText().toString());
                            params.put("first_name", firstName.getText().toString());
                            params.put("last_name", lastName.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("department", department.getText().toString());
                            return params;
                        }
                    };
// Add the request to the RequestQueue.
                    queue.add(postRequest);
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
