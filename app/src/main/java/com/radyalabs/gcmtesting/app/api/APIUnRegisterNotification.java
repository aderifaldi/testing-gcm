package com.radyalabs.gcmtesting.app.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.radyalabs.async.AsyncHttpResponseHandler;
import com.radyalabs.gcmtesting.app.model.ModelRegisterNotification;
import com.radyalabs.irfan.util.AppUtility;

import org.apache.http.Header;


abstract public class APIUnRegisterNotification extends BaseApi{

    protected ModelRegisterNotification data;
    private JsonObject object;
    private JsonObject json;
    private String rawContent;
    private Gson gson;
    private GsonBuilder gsonBuilder;

    public APIUnRegisterNotification(Context context, String token) {
        super(context);

        ajaxType = AjaxType.POST;
        endpointApi = "aderifaldi.wordpress.com/posts/";

        responseHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int codeReturn, Header[] headers, byte[] content) {
                try {
                    rawContent = new String(content);
                    json = new JsonParser().parse(rawContent).getAsJsonObject();
                    object = json.getAsJsonObject();
                    gsonBuilder = new GsonBuilder();
                    gson = gsonBuilder.create();
                    data = gson.fromJson(object, ModelRegisterNotification.class);

                    AppUtility.logD("APIUnRegisterNotification", "Success-Result : " + rawContent);

                    onFinishRequest(true, rawContent);
                } catch(Exception e) {
                    e.printStackTrace();
                    AppUtility.logD("APIUnRegisterNotification", "Exception-Result : " + rawContent);
                    onFinishRequest(false, rawContent);
                }
            }

            @Override
            public void onFailure(int codeReturn, Header[] headers, byte[] content, Throwable error) {
                String textContent = null;
                if (content != null){
                    textContent = new String(content);
                    AppUtility.logD("APIUnRegisterNotification", "Failed-Result : " + textContent);
                }
                onFinishRequest(false, textContent);
            }
        };
    }

}
