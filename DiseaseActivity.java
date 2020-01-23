package com.nishan.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DiseaseActivity extends AppCompatActivity {


    EditText eT_diseaseName,eT_diseaseSyndrome,eT_requireMedicine,eT_restriction,eT_sideEffect,eT_naturalWay,eT_diseasetype;
     Button btnadd_Disase;

    ProgressDialog loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        eT_diseasetype=findViewById(R.id.etxt_diseaseType);
        eT_diseaseName=findViewById(R.id.etxt_diseaseName);
        eT_diseaseSyndrome=findViewById(R.id.etxt_DiseaseSyndrome);
        eT_requireMedicine=findViewById(R.id. etxt_DiseaseMedicine);
        eT_restriction=findViewById(R.id.etxt_DiseaseRestriction);
        eT_sideEffect=findViewById(R.id.etxt_DiseaseSideEffect);
        eT_naturalWay=findViewById(R.id.etxt_DiseaseNatural);

        btnadd_Disase=findViewById(R.id.btnAddDisease);

        btnadd_Disase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Disease();
            }
        });



    }
        private void  add_Disease() {


            //Getting values from edit texts
            final String diseaseType=eT_diseasetype.getText().toString().trim();
            final String diseaseName = eT_diseaseName.getText().toString().trim();
            final String diseaseSyndrome = eT_diseaseSyndrome.getText().toString().trim();
            final String requireMedicine = eT_requireMedicine.getText().toString().trim();
            final String restriction = eT_restriction.getText().toString().trim();
            final String sideEffect = eT_sideEffect.getText().toString().trim();
            final String naturalWay=eT_naturalWay.getText().toString().trim();



            //Checking  field/validation
            if (diseaseType.isEmpty()) {
                eT_diseasetype.setError("Please enter Disease type !");
                requestFocus(eT_diseasetype);
            }
            else if (diseaseName.isEmpty()) {
                eT_diseaseName.setError("Please enter Disease name !");
                requestFocus(eT_diseaseName);
            }
            else if (diseaseSyndrome.isEmpty()) {

                eT_diseaseSyndrome.setError("Please enter Disease Syndrome !");
                requestFocus(eT_diseaseSyndrome);

            }

            else if (requireMedicine.isEmpty()) {

                eT_requireMedicine.setError("Please enter Require Medicine!");
                requestFocus(eT_requireMedicine);

            }
            else if (restriction.isEmpty()) {

                eT_restriction.setError("Please enter Restriction !");
                requestFocus(eT_restriction);

            }
            else if (sideEffect.isEmpty()) {

                eT_sideEffect.setError("Please enter side effect !");
                requestFocus(eT_sideEffect);

            }
            else if (naturalWay.isEmpty()) {

                eT_naturalWay.setError("Please enter side effect !");
                requestFocus(eT_naturalWay);

            }

            else
            {
                loading = new ProgressDialog(this);
                // loading.setIcon(R.drawable.wait_icon);
                loading.setTitle("Adding");
                loading.setMessage("Please wait....");
                loading.show();


                StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.DISEASEADD_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //for track response in logcat
                        Log.d("RESPONSE", response);

                        if (response.equals("success")) {
                            loading.dismiss();
                            Intent intent = new Intent(DiseaseActivity.this, secondActivity.class);
                            Toast.makeText(DiseaseActivity.this, "Disease successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else if (response.equals("exists")) {

                            Toast.makeText(DiseaseActivity.this, "Disease already exists!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                        }

                        else if (response.equals("failure")) {

                            Toast.makeText(DiseaseActivity.this, "Add disease process Failed!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(DiseaseActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                                loading.dismiss();
                            }
                        }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request

                        params.put(Constants.KEY_DISEASETYPE,diseaseType);
                        params.put(Constants.KEY_DISEASENAME,diseaseName);
                        params.put(Constants.KEY_DISEASESYNDROME,diseaseSyndrome);
                        params.put(Constants.KEY_REQUIREMEDICINE,requireMedicine);
                        params.put(Constants.KEY_RESTRICTION,restriction);
                        params.put(Constants.KEY_SIDEEFFECT,sideEffect);
                        params.put(Constants.KEY_NATURALWAY,naturalWay);

                        Log.d("info"," "+diseaseName+" "+diseaseSyndrome+" "+requireMedicine+" "+restriction+" "+sideEffect+""+naturalWay+""+diseaseType);


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
    }