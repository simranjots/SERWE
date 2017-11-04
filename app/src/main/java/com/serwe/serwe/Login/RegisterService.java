package com.serwe.serwe.Login;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class RegisterService extends FirebaseInstanceIdService {

    SharedPreferences pref;

    public RegisterService() {
    }


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        String token = FirebaseInstanceId.getInstance().getToken();
        editor.putString("KEY_FCM_TOKEN", token);
        Log.d("TAG_TOKEN", token);
        editor.commit();
    }
}
