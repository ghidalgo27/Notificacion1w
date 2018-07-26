package com.example.analistademejora.notificacion1;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Analista de Mejora on 11/7/2018.
 */

public class NotificacionIDTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "FIREBASE_TOKEN";

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        enviarTokenRegistro(token);
    }

    private void enviarTokenRegistro(String token) {
        Log.d(TAG, "Nuevo Token: " + token);
    }
}
