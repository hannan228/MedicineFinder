package jav.app.medicinefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
TextView name,email,gender,height,weight,bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPreferenceManager.getInstance(ProfileActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        setActionBar(SharedPreferenceManager.getInstance(ProfileActivity.this).getName());

        initValue();

    }

    public void initValue(){

        name = findViewById(R.id.name_profile);
        name.setText(SharedPreferenceManager.getInstance(this).getName());

        email = findViewById(R.id.email_profile);
        email.setText(SharedPreferenceManager.getInstance(this).getEmail());
        String userType;
        if (SharedPreferenceManager.getInstance(this).getGender()=="1"){
            userType = "patient";
        }else {
            userType = "doctor";
        }

        gender = findViewById(R.id.gender_profile);
        gender.setText(""+userType);

        height = findViewById(R.id.height_profile);
        height.setText(SharedPreferenceManager.getInstance(this).getHeight());

        weight = findViewById(R.id.weight_profile);
        weight.setText(SharedPreferenceManager.getInstance(this).getWeight());

        bmi = findViewById(R.id.bmi_profile);
        bmi.setText(SharedPreferenceManager.getInstance(this).getBMI());

    }
    public void setActionBar(String username) {
        // TODO Auto-generated method stub

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
        getMenuInflater().inflate(R.menu.logout_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            SharedPreferenceManager.getInstance(this).logout();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}