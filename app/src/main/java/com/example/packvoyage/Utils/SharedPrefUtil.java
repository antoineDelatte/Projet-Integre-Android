package com.example.packvoyage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.packvoyage.R;
import com.example.packvoyage.model.ActivityTag;

import java.util.ArrayList;

public class SharedPrefUtil {

    public static boolean checkIfUserHasThisPreference(Context context, ArrayList<ActivityTag>tags){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.SHARED_PREF_FILE_KEY), Context.MODE_PRIVATE);
        for(ActivityTag tag : tags){
            if(sharedPref.contains(tag.getName()))
                return true;
        }
        return false;
    }
}
