package jav.app.medicinefinder;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_HEIGHT = "userheight";
    private static final String KEY_USER_WEIGHT = "userweight";
    private static final String KEY_USER_BMI = "uerbmi";
    private static final String KEY_USER_DIET_CHART = "dietchart";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_TYPE= "userTYPE";
    private static final String KEY_USER_PHYSICAL_ACTIVITY = "userphysicalactivity";

    private SharedPreferenceManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public boolean userLogin(int id, String userName, String email,String height,String weight, String bmi,String dietChart_id,
                             String physicalActivity,String usertype){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_NAME,userName);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_HEIGHT,height);
        editor.putString(KEY_USER_WEIGHT,weight);
        editor.putString(KEY_USER_BMI,bmi);
        editor.putString(KEY_USER_DIET_CHART,dietChart_id);
        editor.putString(KEY_USER_TYPE,usertype);
        editor.putString(KEY_USER_PHYSICAL_ACTIVITY,physicalActivity);

        editor.apply();

        return true;
    }

    public boolean userRegistration(String userName, String email,String height,String weight, String bmi,String dietChart_id,
                                    String physicalActivity,String usertype){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_NAME,userName);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_HEIGHT,height);
        editor.putString(KEY_USER_WEIGHT,weight);
        editor.putString(KEY_USER_BMI,bmi);
        editor.putString(KEY_USER_DIET_CHART,dietChart_id);
        editor.putString(KEY_USER_TYPE,usertype);
        editor.putString(KEY_USER_PHYSICAL_ACTIVITY,physicalActivity);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_NAME,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getName(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,null);
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }

    public String getHeight(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HEIGHT,null);
    }
    public String getWeight(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_WEIGHT,null);
    }

    public String getBMI(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_BMI,null);
    }
    public String getGender(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TYPE,null);
    }


}
