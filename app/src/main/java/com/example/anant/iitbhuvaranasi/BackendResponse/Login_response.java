package com.example.anant.iitbhuvaranasi.BackendResponse;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anant.iitbhuvaranasi.Constants;
import com.example.anant.iitbhuvaranasi.Interfaces.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Login_response {
    private static RequestQueue mRequestQueue;

    public static String method(final Context context, String mail, ServerCallback serverCallback)
    {
        mRequestQueue = Volley.newRequestQueue(context);

        String url = "http://iitbhuapp.tk/login";
        JSONObject obj = new JSONObject();

        try {
            obj.put("email",mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


               /* SharedPreferences pref = context.getSharedPreferences("MyPref2", 0);
                SharedPreferences.Editor editor = pref.edit();
                String rew = response.toString();
                editor.putString("bhaiplease", rew);
                editor.commit();*/

                try {
                    String password1 = response.getString("password");

                    Constants.password = password1;
                    SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.password_shared, password1);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                serverCallback.onSuccess();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        mRequestQueue.add(request);
        return "Successfull";
    }
}
