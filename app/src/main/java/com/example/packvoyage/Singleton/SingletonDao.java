package com.example.packvoyage.Singleton;

import com.example.packvoyage.repository.PackDao;
import com.example.packvoyage.repository.PreferencesDao;

public class SingletonDao {
    private static PackDao packDao;
    private static PreferencesDao preferencesDao;

    public static PackDao getPackDao(){
        if(packDao == null){
            packDao = new PackDao();
        }
        return packDao;
    }

    public static PreferencesDao getPreferencesDao(){
        if(preferencesDao == null){
            preferencesDao = new PreferencesDao();
        }
        return preferencesDao;
    }
}
