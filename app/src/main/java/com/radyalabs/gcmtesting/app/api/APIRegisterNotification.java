package com.radyalabs.gcmtesting.app.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.radyalabs.async.AsyncHttpResponseHandler;
import com.radyalabs.gcmtesting.app.model.ModelRegisterNotification;
import com.radyalabs.gcmtesting.app.util.GlobalVariable;
import com.radyalabs.irfan.util.AppUtility;

import org.apache.http.Header;


abstract public class APIRegisterNotification extends BaseApi{

    protected ModelRegisterNotification data;
    private JsonObject object;
    private JsonObject json;
    private String rawContent;
    private Gson gson;
    private GsonBuilder gsonBuilder;

    public APIRegisterNotification(Context context, String deviceId, String token) {
        super(context);

        ajaxType = AjaxType.POST;
        endpointApi = "Reg";

        params.put("app_token", "01b307acba4f54f55aafc33bb06bbbf6ca803e9a");
        params.put("device_id", deviceId);
        params.put("device_token", token);

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

                    AppUtility.logD("APIRegisterNotification", "Success-Result : " + rawContent);

                    onFinishRequest(true, rawContent);
                } catch(Exception e) {
                    e.printStackTrace();
                    AppUtility.logD("APIRegisterNotification", "Exception-Result : " + rawContent);
                    onFinishRequest(false, rawContent);
                }
            }

            @Override
            public void onFailure(int codeReturn, Header[] headers, byte[] content, Throwable error) {
                String textContent = null;
                if (content != null){
                    textContent = new String(content);
                    AppUtility.logD("APIRegisterNotification", "Failed-Result : " + textContent);
                }
                onFinishRequest(false, textContent);
            }
        };
    }

}
