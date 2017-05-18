package com.example.sauravbiswas.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TeacherFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<UserContainer> studentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_fragment, container, false);
        getActivity().setTitle("Teachers");
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        getAllStudents();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), UserProfile.class);
                        intent.putExtra("name", studentList.get(position).getFirstName() + " " + studentList.get(position).getLastName());
                        intent.putExtra("email", studentList.get(position).getEmail());
                        intent.putExtra("department", studentList.get(position).getDepartment());
                        intent.putExtra("user", "teacher");
                        intent.putExtra("username", studentList.get(position).getUsername());
                        intent.putExtra("first_name", studentList.get(position).getFirstName());
                        intent.putExtra("last_name", studentList.get(position).getLastName());
                        intent.putExtra("phone_no", studentList.get(position).getPhoneNo());
                        startActivity(intent);
                    }
                })
        );
        return view;
    }

    public void getAllStudents() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://10.0.2.2:8000/user/all/teachers";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response:", response.toString());

                MyAdapter adapter = null;
                try {
                    adapter = new MyAdapter(getStudentList(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Occured", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    public ArrayList<UserContainer> getStudentList(JSONArray response) throws JSONException {
        studentList = new ArrayList<UserContainer>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject student = (JSONObject) response.get(i);
            String first_name = student.getString("first_name");
            String last_name = student.getString("last_name");
            String username = student.getString("username");
            String email = student.getString("email");
            String department = student.getString("department");
            String phone_no = student.getString("phone_no");
            UserContainer user = new UserContainer(first_name, last_name, email, department, username,phone_no);
            studentList.add(user);
        }
        return studentList;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllStudents();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
