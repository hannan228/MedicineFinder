package jav.app.medicinefinder;

import android.content.Context;
import android.content.SharedPreferences;

public class DietChartSharedPref {
    private static DietChartSharedPref instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME_DIET = "dietchartsharedpref";
    private static final String KEY_DIET_ID = "dieId";
    private static final String KEY_DIET_NAME = "dietTitle";
    private static final String KEY_DIET_DESCRIPTION = "dietDescription";

    private DietChartSharedPref(Context context) {
        ctx = context;
    }

    public static synchronized DietChartSharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new DietChartSharedPref(context);
        }
        return instance;
    }


    public void dietPlan(int id, String name,String descrption){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME_DIET,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_DIET_ID,id);
        editor.putString(KEY_DIET_NAME,name);
        editor.putString(KEY_DIET_DESCRIPTION,descrption);

        editor.apply();
    }


    public String getDietName(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME_DIET,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DIET_NAME,null);
    }

    public String getDietDescription(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME_DIET,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DIET_DESCRIPTION,null);
    }

}
