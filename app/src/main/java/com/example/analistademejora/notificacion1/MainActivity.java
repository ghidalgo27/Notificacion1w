package com.example.analistademejora.notificacion1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends FragmentActivity implements DownloadCallback {

    private Button boton;
    private Button boton2;
    private Button boton_consulta;
    private Button boton_limpiar;

    private EditText campo_url;
    private TextView intro_text;
    private Button boton_url;
    private Button boton_del;

   //*****
    private TextView mDataText;
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;
    //*****
    String texto_url=null;

    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;

    NotificationCompat.Builder notificacion2;
    private static final int idUnica2 = 51624;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ////$$$$$$$$$$
        campo_url = (EditText) findViewById(R.id.editText);
        intro_text = (TextView) findViewById(R.id.intro_text);
        boton_url = (Button) findViewById(R.id.boton_url);
        boton_del = (Button) findViewById(R.id.boton_del);


        boton_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texto_url = null;
                texto_url = campo_url.getText().toString();
                intro_text.setText(texto_url);
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
            }
        });

        ////######
        mDataText = (TextView) findViewById(R.id.data_text);
        String texto_urlA=texto_url;
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),"https://192.168.1.30/awp/llovasGH/index.html");
        //////#####



        ////$$$$$$$$$$

        boton = (Button) findViewById(R.id.boton);


        notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                notificacion.setSmallIcon(R.mipmap.ic_launcher);
                notificacion.setTicker("Nueva notificacion");
                notificacion.setPriority(Notification.PRIORITY_HIGH);
                notificacion.setWhen(System.currentTimeMillis());
                notificacion.setContentTitle("Variable fuera de rango");
                notificacion.setContentText("Noticacion PLC llovas");
                notificacion.setVibrate(new long[] {100, 250, 100, 500});

                Intent intent = new Intent(MainActivity.this,MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent                    .FLAG_UPDATE_CURRENT);
                notificacion.setContentIntent(pendingIntent);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(idUnica,notificacion.build());
            }
        });



        //    public void recibir_notificacion(View v){
        //        String token = FirebaseInstanceId.getInstance().getToken();
        //  String msg = getString(R.string.msg_token_fmt, token);
        //  Log.d(TOKEN, token);

        //      }

        //     public void enviarTokenRegistro(String token){
        //          Log.d("TOKEN", token);

        //     }

        //************************intento de eotra notificacion
///Creo la notificacion 2

        //si esta arriba------------NotificationCompat.Builder notificacion2;
        //si esta arriba------------private static final int idUnica2 = 51624;
        notificacion2 = new NotificationCompat.Builder(this);
        notificacion2.setAutoCancel(true);


        boton2 = (Button) findViewById(R.id.botontemp);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacion2.setSmallIcon(R.mipmap.ic_launcher);
                notificacion2.setTicker("Nueva notificacion Dos");
                notificacion2.setPriority(Notification.PRIORITY_HIGH);
                notificacion2.setWhen(System.currentTimeMillis());
                notificacion2.setContentTitle("Temperatura Correcta");
                notificacion2.setContentText("Set Point 30°C");
                notificacion2.setVibrate(new long[] {100, 250, 100, 500});

                Intent intent = new Intent(MainActivity.this,MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent                    .FLAG_UPDATE_CURRENT);
                notificacion2.setContentIntent(pendingIntent);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(idUnica2,notificacion2.build());
            }
        });

        boton_consulta = (Button) findViewById(R.id.boton_consulta);
        boton_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
            }
        });

        boton_limpiar = (Button) findViewById(R.id.boton_limpiar);
        boton_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDownloading();
                mDataText.setText("");
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
            }
        });













        //************************aqui termina

    }
    //////////////888888888888888888888888888////////////////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>////////



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    //menu de botones CONSULTA Y LIMPIAR ACCIONES-------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Aqui inincia el proceso de consulta
            // raw HTML from www.google.com.
            case R.id.fetch_action:
                startDownload();
                return true;
            // Clear the text and cancel download.
            case R.id.clear_action:
                finishDownloading();
                mDataText.setText("");
                return true;
        }

        return false;
    }

// www.andreaardions.com/   https://androidstudiofaqs.com/tutoriales/como-darle-funcion-a-un-boton-en-android-studio





    //-------------
    //Llamadas de Menu
    //mDownloading es una variable bandera
    //mNetworkFragment contiene la direccion web de youtube
    //mDataText es el TextView

    private void startDownload() {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }
    //---------------

    //""""""""""""""Si se baja algo de la descarga entonces mandele al string mDatatext caso contrario erroconexion
    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            mDataText.setText(result);
        } else {
            mDataText.setText(getString(R.string.connection_error));
        }
    }
    //""""""""""""""""""""""


    //Servicio de conexion
    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }


    //vuelve a null las variables de Dowlading
    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

// es lo del porcentaje

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                mDataText.setText("" + percentComplete + "%%%%%%%%%%%%%%%%%");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }





















    //////////////888888888888888888888888888////////////////////////////
}