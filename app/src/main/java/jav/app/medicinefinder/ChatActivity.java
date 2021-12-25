package jav.app.medicinefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jav.app.medicinefinder.Adapter.RecyclerViewAdapter;
import jav.app.medicinefinder.Model.Message;

import static jav.app.medicinefinder.Constants.URL_LOGIN;
import static jav.app.medicinefinder.Constants.URL_SEND_MESSAGE;
import static jav.app.medicinefinder.Constants.URL_SEND_User_AVAILABLE;

public class ChatActivity extends AppCompatActivity {

    EditText chat,sendTo;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView, recyclerViewHorizontal;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Message> messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat = findViewById(R.id.textTOsend);
        sendTo = findViewById(R.id.sendTo);
        progressDialog = new ProgressDialog(this);
        messageList = new ArrayList<>();
        verticalRecyclerViewSettings();
    }
    private void verticalRecyclerViewSettings() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerViewAdapter = new RecyclerViewAdapter(ChatActivity.this, messageList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    public void sendText(View view) {
        String text = chat.getText().toString();

        try {
            if (!text.isEmpty()){
                sendToIsAvailable("bb"+text);
            }else {
                chat.setError("message is empty");
                Toast.makeText(this, "message is empty", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
        }
    }

    public void sendMessage(String message){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String sendto = sendTo.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject2 = new JSONObject(response);
                        Log.d("object",""+jsonObject2);
                    }

                    if(!jsonObject.getBoolean("error")){
                        messageList.add(new Message(""+message,
                                ""+SharedPreferenceManager.getInstance(ChatActivity.this).getEmail(),
                                ""+sendTo,
                                ""+currentDate));
                        recyclerViewAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(ChatActivity.this, ""+"user not available", Toast.LENGTH_SHORT).show();
                        sendTo.setError("this user is not available");
                    }

                }catch (JSONException jsonException){
                    Log.d("Error In Json",""+jsonException);
                    Toast.makeText(ChatActivity.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChatActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("text",""+message);
                params.put("sender",""+SharedPreferenceManager.getInstance(ChatActivity.this).getEmail());
                params.put("sendTo",""+sendto);
                params.put("date",""+currentDate);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }



    public void sendToIsAvailable(String message){
        String sendto = sendTo.getText().toString();

        if (sendto.isEmpty()){
            sendTo.setError("empty");
            Toast.makeText(this, "sendTo is empty", Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND_User_AVAILABLE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getBoolean("message")){
                            sendMessage(message);
                        }else{
                            Toast.makeText(ChatActivity.this, ""+"user not available", Toast.LENGTH_SHORT).show();
                            sendTo.setError("this user is not available");
                        }

                    }catch (JSONException jsonException){
                        Log.d("Error In Json",""+jsonException);
                        Toast.makeText(ChatActivity.this, ""+jsonException, Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("mail",""+sendto);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}