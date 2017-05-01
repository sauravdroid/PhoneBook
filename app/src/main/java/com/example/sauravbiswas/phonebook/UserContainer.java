package com.example.sauravbiswas.phonebook;
public class UserContainer {
    private String first_name , last_name , email , department , username,roll;

    public UserContainer(String first_name,String last_name,String email,String department,String username){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.department = department;
        this.username = username;
    }
    public void setRoll(String roll){
        this.roll = roll;
    }
    public String getUsername(){
        return username;
    }
    public String getFirstName(){
        return  first_name;
    }
    public String getLastName(){
        return last_name;
    }
    public String getEmail(){
        return email;
    }
    public String getDepartment(){
        return department;
    }
    public String getRoll(){
        return roll;
    }
}
