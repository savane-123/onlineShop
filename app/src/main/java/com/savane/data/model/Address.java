package com.savane.data.model;

public class Address {

        private Integer addressId;
        private String userFullAddress;
        private String  userCity;
        private String userState;
        private  String userCountry;
        private Integer userId;

        public Address(Integer addressId, String userFullAddress, String userCity, String userState, String userCountry, Integer userId) {
            this.addressId = addressId;
            this.userFullAddress = userFullAddress;
            this.userCity = userCity;
            this.userState = userState;
            this.userCountry = userCountry;
            this.userId = userId;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public String getUserFullAddress() {
            return userFullAddress;
        }

        public void setUserFullAddress(String userFullAddress) {
            this.userFullAddress = userFullAddress;
        }

        public String getUserCity() {
            return userCity;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserState() {
            return userState;
        }

        public void setUserState(String userState) {
            this.userState = userState;
        }

        public String getUserCountry() {
            return userCountry;
        }

        public void setUserCountry(String userCountry) {
            this.userCountry = userCountry;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }


