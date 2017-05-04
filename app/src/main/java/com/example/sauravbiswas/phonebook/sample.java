package com.example.sauravbiswas.phonebook;

/**
 * Created by sauravbiswas on 04/05/17.
 */

public class sample {
    int age;
    String name;
    Boolean isMale;
    public sample(int age,String name,Boolean isMale){
        this.age = age;
        this.name = name;
        this.isMale = isMale;

    }
    public void printName(){
        System.out.println(name);
    }
    public String getName(){
        return name;
    }
}
