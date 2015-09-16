package com.multunus.neomdm;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class PackageInstallerService extends Service {
    public PackageInstallerService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = "https://dl.dropboxusercontent.com/s/tx7dr793uhczxdq/com.uberspot.a2048_18.apk";
        new DownloadAsyncTask(this).execute(url);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Neo MDM")
                .setContentText("Installer")
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .build();
        startForeground(500, notification);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
