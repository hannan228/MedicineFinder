package jav.app.medicinefinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

import static jav.app.medicinefinder.Constants.URL_REGISTER;

public class MainActivity extends AppCompatActivity {

    EditText userName,password,email,height,weight;
    RadioGroup gender,physicalActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);

        if (SharedPreferenceManager.getInstance(MainActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Dashboard.class));
            return;
        }

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        gender = (RadioGroup) findViewById(R.id.radioGender);
        physicalActivity = (RadioGroup) findViewById(R.id.physicalActivity);

    }


    public void submitButton(View view) {
        String name = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String heightt = height.getText().toString().trim();
        String weightt = weight.getText().toString().trim();

        Log.d("height",""+heightt);
        Log.d("weight",""+weightt);


        int height2 = Integer.parseInt(heightt);
        int weight2 = Integer.parseInt(weightt);

        int a = (height2/100);
        int b = (height2%100);
        String c = a+"."+b;

        double heightInm2 = Double.parseDouble(c)*Double.parseDouble(c);

        double bmi2 = weight2/heightInm2;

         int gender2 = gender();
        int physicalType = physicalType();

        progressDialog.setMessage("Registering User");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (!jsonObject.getBoolean("error")){
                        final String gender1 = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                        final String physicalActivity1 = ((RadioButton) findViewById(physicalActivity.getCheckedRadioButtonId())).getText().toString();

                        SharedPreferenceManager.getInstance(MainActivity.this)
                                .userRegistration(
                                        ""+name,
                                        ""+mail,
                                        ""+height2,
                                        ""+weight2,
                                        ""+bmi2,
                                        ""+gender2,
                                        ""+physicalActivity1,
                                        ""+gender1);

                        Toast.makeText(MainActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Dashboard.class));
                    }else{
                        Toast.makeText(MainActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException jsonException){
                    Log.d("Error In Json",""+jsonException);
                    Toast.makeText(MainActivity.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",""+name);
                params.put("password",""+pass);
                params.put("email",""+mail);
                params.put("height",""+height2);
                params.put("weight",""+weight2);
                params.put("bmindex",""+bmi2);
                params.put("physical",""+physicalType);
                params.put("usertype",""+gender2);
                params.put("dietchart",""+gender2);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private int gender(){
        int userType = 1;
        final String gender1 = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        if (gender1.equals("Patient")){
            userType = 1;
        }else if (gender1.equals("Doctor")){
            userType = 2;
        }
        return userType;
    }

    private int physicalType(){
        int physicalType = 1;
        final String physicalActivity1 = ((RadioButton) findViewById(physicalActivity.getCheckedRadioButtonId())).getText().toString();
        if (physicalActivity1.equals("Normal")){
            physicalType = 1;
        }else if (physicalActivity1.equals("Moderate")){
            physicalType = 2;
        }else if (physicalActivity1.equals("Heavy")){
            physicalType = 3;
        }
        return physicalType;
    }

    public void alreadyHaveAccount(View view) {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}