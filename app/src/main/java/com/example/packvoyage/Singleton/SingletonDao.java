package com.example.packvoyage.Singleton;

import com.example.packvoyage.repository.PackDao;

public class SingletonDao {
    private static PackDao packDao;

    public static PackDao getPackDao(){
        if(packDao == null){
            packDao = new PackDao();
        }
        return packDao;
    }
}
