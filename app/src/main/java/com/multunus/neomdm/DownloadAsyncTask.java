package com.multunus.neomdm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by heisenberg on 29/06/15.
 */
public class DownloadAsyncTask extends AsyncTask<String, String, String> {

    Context mContext;
    ProgressDialog mProgressDialog;

    public DownloadAsyncTask(Context context) {
        mContext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "", "Downloading...", true);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int length = urlConnection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream("/sdcard/the_apk.apk");

            byte data[] = new byte[1024];

            int count;

            int total = 0;

            while ((count = input.read(data)) != -1) {
                total = total + count;
                publishProgress(String.valueOf(total), String.valueOf(length));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setMessage("Downloading... \n\n" + values[0] + "\\" + values[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgressDialog.dismiss();
        File apk = new File(Environment.getExternalStorageDirectory() + "/the_apk.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
