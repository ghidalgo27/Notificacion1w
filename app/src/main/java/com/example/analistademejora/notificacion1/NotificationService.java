package com.example.analistademejora.notificacion1;

/**
 * Created by Analista de Mejora on 11/7/2018.
 */
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService {

    private static final int idUnica2 = 51624;
   public static final String TAG = "FIREBASE";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.nMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        //Intent intent = new Intent(MainActivity.this,MainActivity.class);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                     .setSmallIcon(R.mipmap.ic_launcher)
                     .setTicker("Nueva notificacion")
                     .setPriority(Notification.PRIORITY_HIGH)
                     .setWhen(System.currentTimeMillis())
                     .setContentTitle("Variable fuera de rango")
                     .setContentText(remoteMessage.getNotification().getBody())
                     .setVibrate(new long[] {100, 250, 100, 500})
                     .setContentIntent(pendingIntent);


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica2,notificacion.build());


    }


}










