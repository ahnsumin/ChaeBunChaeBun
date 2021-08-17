package com.example.chaebunchaebun;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PostTask extends AsyncTask<String, Void, Void> {
    String receiveMsg, str;
    String res_json;

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://3.37.243.188:8080/" + params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true); // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(params[1].getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader br = null;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg: ", receiveMsg);

                bufferedReader.close();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    protected void onPostExecute(Void unused) {
//        super.onPostExecute(unused);
//    }
}
