package com.example.packvoyage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.packvoyage.R;

public class SharedPrefUtil {

    public static boolean checkIfUserHasThisPreference(Context context, String tagName){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.SHARED_PREF_FILE_KEY), Context.MODE_PRIVATE);
        return sharedPref.contains(tagName);
    }
}
