package com.example.asus.sportiway.Preferences;

/**
 * Created by Asus on 07/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TBPediaPref";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_EVENT_ID = "event_id";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createEventIdSession(String id, String eventId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, id);
        editor.putString(KEY_EVENT_ID, eventId);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_EVENT_ID, pref.getString(KEY_EVENT_ID, null));
        return user;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}