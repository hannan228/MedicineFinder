package jav.app.medicinefinder;

import android.content.Context;
import android.content.SharedPreferences;

public class MedecineSharedPreferences {

    private static MedecineSharedPreferences instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME_MED = "medecinesharedpref12";
    private static final String KEY_MED_ID = "medid";
    private static final String KEY_MED_NAME = "medName";
    private static final String KEY_MED_BRAND = "medBrand";
    private static final String KEY_MED_DESC = "medDesc";

    private MedecineSharedPreferences(Context context) {
        ctx = context;
    }

    public static synchronized MedecineSharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new MedecineSharedPreferences(context);
        }
        return instance;
    }

    public void medicineDesc(int id, String name,String descrption,String brand){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME_MED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_MED_ID,id);
        editor.putString(KEY_MED_NAME,name);
        editor.putString(KEY_MED_BRAND,brand);
        editor.putString(KEY_MED_DESC,descrption);

        editor.apply();
    }





}
