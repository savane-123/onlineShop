package com.savane.data.model;

public class User {
    private Integer id;
    private  String firsName;
    private String LastName;
    private String Email;
    private String Phone;
    private String DateOfBirth;
    private String Gender;


    public User(Integer id, String firsName, String LastName, String Email, String Phone, String DateOfBirth,String Gender) {
        this.id = id;
        this.firsName = firsName ;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.DateOfBirth = DateOfBirth;
        this.Gender = Gender;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firsName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getGender() {
        return Gender;
    }


}
