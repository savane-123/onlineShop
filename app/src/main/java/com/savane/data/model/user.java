package com.savane.data.model;

public class user {
    private Integer id;
    private  String firsName;
    private String LastName;
    private String Email;
    private String Phone;
    private String DateOfBirth;
    private String Password;
    private String DateOfregistration;
    private String DateOfUpdate;
    private String Gender;
    private String AddressId;
    private String UserType;

    public user(Integer id, String firsName, String LastName, String Email, String Phone, String DateOfBirth,String Password,String DateOfregistration,String DateOfUpdate,String Gender,String  AddressId,String UserType) {
        this.id = id;
        this.firsName = firsName ;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.DateOfBirth = DateOfBirth;
        this.Password= Password;
        this.DateOfregistration= DateOfregistration ;
        this.DateOfUpdate = DateOfUpdate;
        this.Gender = Gender;
        this.AddressId =  AddressId;
        this.UserType =  UserType;
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

    public String getPassword() {
        return Password;
    }

    public String getDateOfregistration() {
        return DateOfregistration;
    }

    public String getDateOfUpdate() {
        return DateOfUpdate;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddressId() {
        return AddressId;
    }

    public String getUserType() { return UserType; }
}
