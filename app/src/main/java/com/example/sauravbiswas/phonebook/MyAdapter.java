package com.example.sauravbiswas.phonebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<UserContainer> list;

    public MyAdapter(ArrayList<UserContainer> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView department, teacherName;

        MyViewHolder(View itemView) {
            super(itemView);
            department = (TextView) itemView.findViewById(R.id.text_department);
            teacherName = (TextView) itemView.findViewById(R.id.text_teacher_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.department.setText(list.get(position).getDepartment());
        holder.teacherName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
