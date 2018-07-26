package com.example.analistademejora.notificacion1;

/**
 * Created by Analista de Mejora on 18/7/2018.
 */

import android.net.NetworkInfo;
import android.support.annotation.IntDef;

/**
 * Sample interface containing bare minimum methods needed for an asynchronous task
 * to update the UI Context.
 */

public interface DownloadCallback {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    void updateFromDownload(String result);

    NetworkInfo getActiveNetworkInfo();

    void onProgressUpdate(int progressCode, int percentComplete);

    void finishDownloading();

}
