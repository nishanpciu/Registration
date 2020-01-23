package com.nishan.registration;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView, imageUpload;
    private Animation smalltobig, btta, btta2;
    private TextView textView, subtitle_header, txtLogin,txtGender;
    private Button btnSignUp,btnDisease,btnChoose;
    private EditText eT_FirstName, eT_LastName, eT_Email, eT_MobileNo, eT_Password, eT_Con_password,eT_Division;
    //Spinner sCountry;
    private RadioGroup radioGroup, radiogroup2;
    private RadioButton rbMale,rbFemale,rb_Patient,rb_Specialist,rb_ShopOwner;
    LinearLayout layout;

    ProgressDialog loading;
    final int CODE_GALLERY_REQUEST=999;
    final  String countries[] = {"Select Area", "Dhaka", "Chattogram", "Rajshahi", "Sylhet", "Barisal", "Kumilla", "Khulna", "Jassore", "Rangpur", "Mymonshing", "Tangail", "Cox's Bazar", "Dinajpur"};


    private AlertDialog.Builder alertDialogBuilder;
    String division;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Register Page");

// load this animation
//        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
//        btta = AnimationUtils.loadAnimation(this, R.anim.btta);
//        btta2 = AnimationUtils.loadAnimation(this, R.anim.btta2);

        imageView = findViewById(R.id.imageView);

        imageUpload=findViewById(R.id.imageUpload);
        btnChoose=findViewById(R.id.btnChoose);

       // txtGender=findViewById(R.id.txtgender);
        //eT_Division = findViewById(R.id.txt_area_signup);

        textView = findViewById(R.id.textView);
        subtitle_header = findViewById(R.id.subtitle_header);
        txtLogin = findViewById(R.id.textView3);

        btnSignUp = findViewById(R.id.btnSingUp);
        btnDisease=findViewById(R.id.btnDisease);

        eT_FirstName = findViewById(R.id.txt_firstname_signup);
        eT_LastName = findViewById(R.id.txt_lastname_signup);
        eT_Email = findViewById(R.id.txt_emailSignup);
        eT_MobileNo = findViewById(R.id.txt_mobile_signup);
        eT_Password = findViewById(R.id.txt_password_signup);
        eT_Con_password = findViewById(R.id.txt_confirmPass_singup);


        //editText4 = findViewById(R.id.txt_area_signup);
        radioGroup = findViewById(R.id.radiogroup);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        layout = (LinearLayout) findViewById(R.id.radiogrplayout);

        rb_Patient=findViewById(R.id.rb_Patient);
        rb_Specialist=findViewById(R.id.rb_Specialist);
        rb_ShopOwner=findViewById(R.id.rb_SOwner);
        // radiogroup2=findViewById(R.id.radiogroupType_signup);


        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //sCountry.setAdapter(adapter);



         btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnSignUp){
                    sign_up();
                }

            }
        });
        btnDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v==btnDisease){
                    Intent intent=new Intent(MainActivity.this,DiseaseActivity.class);
                    startActivity(intent);

                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST);
            }
        });


    }



        private void sign_up() {

            final String firstName = eT_FirstName.getText().toString().trim();
        final String lastName = eT_LastName.getText().toString().trim();
        final String email = eT_Email.getText().toString().trim();
        final String password = eT_Password.getText().toString().trim();
        final String mobileno = eT_MobileNo.getText().toString().trim();
       // final String area=sCountry.getSelectedItem().toString().trim();



        //Checking  field/validation
        if (firstName.isEmpty()) {
            eT_FirstName.setError("Please enter your first name !");
            requestFocus(eT_FirstName);
        }
        else if (lastName.isEmpty()) {

            eT_LastName.setError("Please enter your last name !");
            requestFocus(eT_LastName);
        }

        else if (email.isEmpty()) {

            eT_Email.setError("Please enter your email !");
            requestFocus(eT_Email);

        }
        else if (password.isEmpty()) {

            eT_Password.setError("Please enter your password !");
            requestFocus(eT_Password);

        }
        else if (mobileno.length()!=11 || mobileno.contains(" ") || mobileno.charAt(0)!='0'
                ||mobileno.charAt(1)!='1') {

            eT_MobileNo.setError("Please enter your MobileNo !");
            requestFocus(eT_MobileNo);

        }

        else
        {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SIGNUP_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);

                    if (response.equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(MainActivity.this, secondActivity.class);
                        Toast.makeText(MainActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else if (response.equals("exists")) {

                        Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.equals("failure")) {

                        Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constants.KEY_FIRSTNAME,firstName);
                    params.put(Constants.KEY_LASTNAME,lastName);
                    params.put(Constants.KEY_EMAIL,email);
                    params.put(Constants.KEY_PASSWORD,password);
                    params.put(Constants.KEY_MOBILENO,mobileno);
                   // params.put(Constants.KEY_GENDER,gender);
                  //  params.put(Constants.KEY_GENDER, gender);



                    Log.d("info",""+firstName+" "+lastName+" "+email+" "+mobileno+" "+password);
                  //  Log.d("info",""+name+" "+cell);


                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }



    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //image upload work
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CODE_GALLERY_REQUEST){
            if(grantResults.length >0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"select image"),CODE_GALLERY_REQUEST);

            }
            else{
                Toast.makeText(getApplicationContext(),"you dont have permission",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //image upload work
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CODE_GALLERY_REQUEST && requestCode== RESULT_OK && data!=null)
        {
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                imageUpload.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
