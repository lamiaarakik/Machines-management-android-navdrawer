package com.example.exo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.exo2.beans.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
EditText username;
    private List<User> userList;
EditText password;

    RequestQueue requestQueue;

    String insertUrl = "http://192.168.88.2:8090/users/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=  findViewById(R.id.username);
        password = findViewById(R.id.password);
        userList = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(insertUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new User();
                        user.setUsername(jsonObject.getString("username"));
                        user.setPassword(jsonObject.getString("password"));
userList.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);}

public void login(View v){
Log.d("lamiaa",userList.get(0).getUsername().toString());

for(int i=0;i<userList.size();i++){
    String usernametext=username.getText().toString();
    String passwordtext=password.getText().toString();
    if(usernametext.equals(userList.get(i).getUsername())){
        if(passwordtext.equals(userList.get(i).getPassword())){
            Toast. makeText(this, "valid", Toast. LENGTH_SHORT).show();
            Intent intent= new Intent(LoginActivity.this,PrincipalActivity.class);
            startActivity(intent);
            break;
        }
    }

    else{
        Toast. makeText(this, "invalid", Toast. LENGTH_SHORT).show();
    }}
}
            }

