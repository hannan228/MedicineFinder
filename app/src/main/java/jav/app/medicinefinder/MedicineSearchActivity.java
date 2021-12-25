package jav.app.medicinefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import static jav.app.medicinefinder.Constants.URL_MEDICINE;

public class MedicineSearchActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView medName,medBrand,medDescription;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);

        searchBar = findViewById(R.id.searchEditText);
        medName = findViewById(R.id.med_name);
        medBrand = findViewById(R.id.med_brand);
        medDescription = findViewById(R.id.med_description);
        progressDialog = new ProgressDialog(this);

        search();

    }
    public void search() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }

        });
    }

    private void filter(String text) {
        requestMedicine(text);
    }

    public void requestMedicine(String title){

        progressDialog.setMessage("Searching medicine...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MEDICINE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){

                        if (jsonObject.getString("name")!=null){
                            medName.setText(jsonObject.getString("name"));
                            medBrand.setText(jsonObject.getString("brand"));
                            medDescription.setText(jsonObject.getString("description"));
                        }else{
                            Toast.makeText(MedicineSearchActivity.this, "Medicine not found", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MedicineSearchActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException jsonException){
                    Log.d("Error In Json",""+jsonException);
                    Toast.makeText(MedicineSearchActivity.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MedicineSearchActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("medecine",""+title);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}