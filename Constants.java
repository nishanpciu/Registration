package com.nishan.registration;

public class Constants {

    private static final String MAIN_URL="http://192.168.43.202";

   // public static final String URL_REGISTER=ROOT_URL+"register.php";

    public static final String SIGNUP_URL = MAIN_URL+"/AyurvedaApp/signUp.php";
    public static final String DISEASEADD_URL=MAIN_URL+"/AyurvedaApp/diseaseAdd.php";

    //Keys for server communications
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MOBILENO = "mobileno";






    //for disease add
    public static final String KEY_DISEASETYPE="diseasetype";
    public static final String KEY_DISEASENAME="diseasename";
    public static final String KEY_DISEASESYNDROME="diseasesyndrome";
    public static final String KEY_REQUIREMEDICINE="requiremedicine";
    public static final String KEY_RESTRICTION="restriction";
    public static final String KEY_SIDEEFFECT="sideeffect";
    public static final String KEY_NATURALWAY="naturalway";


    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.nishan.registration.userlogin"; //pcakage name+ id

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";


    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";


}


