package com.example.laher.learnfractions.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.laher.learnfractions.util.AppConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class Service extends AsyncTask<Void, Void, Void>{
    ProgressDialog progressBar;
    private String url;
    private RequestParams rp;
    private Context context;
    private ServiceResponse sr;
    private JSONObject response;
    private String loadingTitle;
    private String method;

    public Service(String loadingTitle, Context context, ServiceResponse serviceResponse) {
        this.context = context;
        this.sr = serviceResponse;
        this.loadingTitle = loadingTitle;
    }

    public void post(String url, RequestParams params) {
        this.url = url;
        this.rp = params;
        this.method = AppConstants.METHOD_POST;
    }

    public void get(String url, RequestParams params){
        this.url = url;
        this.rp = params;
        this.method = AppConstants.METHOD_GET;
    }

    @Override
    protected void onPreExecute() {
        progressBar = new ProgressDialog(this.context);
        progressBar.setCancelable(false);
        progressBar.setMessage(this.loadingTitle);
        progressBar.show();

    }

    @Override
    protected void onPostExecute(Void result) {
        if (response != null) {
            sr.postExecute(response);
        }
        try {
            progressBar.dismiss();
        } catch (Exception e) {
        }
    }
    @Override
    protected Void doInBackground(Void... voids) {

        SyncHttpClient client = new SyncHttpClient();

        if (this.method.equals(AppConstants.METHOD_POST)) {
            client.post(url, rp, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                    super.onSuccess(statusCode, headers, resp);
                    response = resp;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    response = errorResponse;
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String st, Throwable th){
                    String s = "";
                }
            });
        }else if(this.method.equals(AppConstants.METHOD_GET)){
            client.get(url, rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                    super.onSuccess(statusCode, headers, resp);
                    response = resp;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    response = errorResponse;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("Failed: ", ""+statusCode);
                    Log.d("Error : ", "" + throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }

        return null;
    }
}
