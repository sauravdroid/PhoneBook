package com.example.sauravbiswas.phonebook;
public class UserContainer {
    private String first_name , last_name , email , department , username,roll,phone_no;

    public UserContainer(String first_name,String last_name,String email,String department,String username,String phone_no){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.department = department;
        this.username = username;
        this.phone_no = phone_no;
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
    public String getPhoneNo(){return phone_no;}
}
