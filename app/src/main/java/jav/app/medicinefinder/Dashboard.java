package jav.app.medicinefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jav.app.medicinefinder.Constants.URL_DIET;
import static jav.app.medicinefinder.Constants.URL_LOGIN;
import static jav.app.medicinefinder.Constants.URL_MEDICINE;

public class Dashboard extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView dietTitle,dietDescription,medName,medBrand,medDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        progressDialog = new ProgressDialog(this);
        setActionBar(SharedPreferenceManager.getInstance(Dashboard.this).getName());
        dietchart(SharedPreferenceManager.getInstance(getApplicationContext()).getBMI());

        dietTitle = findViewById(R.id.diet_title);
        dietDescription = findViewById(R.id.diet_description);

    }

    public void setActionBar(String username) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_500)));
        actionBar.setTitle(""+username);
        actionBar.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile){
            startActivity(new Intent(Dashboard.this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void dietchart(String bmi2){
        double bmi = Double.parseDouble(bmi2);
        String nutritionalStatus = "";
        if (bmi<18.5){
            nutritionalStatus = "under weight";
        }else if(bmi>=18.5 || bmi <24.9){
            nutritionalStatus = "Normal Weight";
        }else if(bmi>=24.9 || bmi <29.9){
            nutritionalStatus = "Pre-Obesity";
        }else if(bmi>=29.9 || bmi <34.9){
            nutritionalStatus = "Obesity CLass 1";
        }else if(bmi>=34.9 || bmi <39.9){
            nutritionalStatus = "Obesity CLass 2";
        }else if(bmi>=39.9){
            nutritionalStatus = "Obesity CLass 3";
        }

        requestDiet(nutritionalStatus);
    }

    public void requestDiet(String title){

        progressDialog.setMessage("Fetching Diet Plan..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DIET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){
                        DietChartSharedPref.getInstance(Dashboard.this)
                                .dietPlan(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("description")
                                );
                        dietTitle.setText(jsonObject.getString("title"));
                        dietDescription.setText(jsonObject.getString("description"));
                        Toast.makeText(Dashboard.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Dashboard.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException jsonException){
                    Log.d("Error In Json",""+jsonException);
                    Toast.makeText(Dashboard.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Dashboard.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dietplan",""+title);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void goToChat(View view) {
        startActivity(new Intent(this,ChatActivity.class));
    }

    public void searchMedicine(View view) {
        startActivity(new Intent(this,MedicineSearchActivity.class));
    }
}