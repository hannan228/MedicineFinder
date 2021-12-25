package jav.app.medicinefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static jav.app.medicinefinder.Constants.URL_LOGIN;
import static jav.app.medicinefinder.Constants.URL_REGISTER;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //checking weather user already loggedIn or not
        if (SharedPreferenceManager.getInstance(LoginActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }

        username = findViewById(R.id.gmail_login);
        password = findViewById(R.id.password_login);
        progressDialog = new ProgressDialog(this);

    }

    public void loginButton(View view) {
        String name = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        progressDialog.setMessage("Sign in...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){
                        SharedPreferenceManager.getInstance(LoginActivity.this)
                                .userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("Height"),
                                        jsonObject.getString("Weight"),
                                        jsonObject.getString("BMI"),
                                        jsonObject.getString("DietChart_Id"),
                                        jsonObject.getString("PhysicalActivity"),
                                        jsonObject.getString("UserType")
                                        );

                        Toast.makeText(LoginActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,Dashboard.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException jsonException){
                    Log.d("Error In Json",""+jsonException);
                    Toast.makeText(LoginActivity.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("useremail",""+name);
                params.put("password",""+pass);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}