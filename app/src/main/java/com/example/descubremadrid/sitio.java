package com.example.descubremadrid;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class sitio {

    String quererVisitar;

    JsonObjectRequest jsonObjectRequest2;
    RequestQueue request2;

    public sitio(String quererVisitar) {
        this.quererVisitar = quererVisitar;
    }

    public sitio() {

    }

    public String getQuererVisitar() {
        return quererVisitar;
    }

    public void setQuererVisitar(String quererVisitar) {
        this.quererVisitar = quererVisitar;
    }


}
