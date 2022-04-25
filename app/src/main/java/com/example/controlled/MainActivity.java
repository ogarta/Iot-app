package com.example.controlled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Slider sldR, sldG, sldB, sldBright;
    Button btnRun,btnRef;
    TextView txtShow, txtReview;
    private static final String url = "https://iot.svute.com/api/controls/led.php?act=update";
    private static final String api_getValue = "https://iot.svute.com/api/controls/led.php?act=getValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sldR = findViewById(R.id.sldRed);
        sldG = findViewById(R.id.sldGreen);
        sldB = findViewById(R.id.sldBlue);
        sldBright = findViewById(R.id.sldBrigntness);
        txtShow = findViewById(R.id.txtShow);
        txtReview = findViewById(R.id.txtReview);
        btnRun = findViewById(R.id.btnRun);
        btnRef = findViewById(R.id.btnRef);
        loadRGB();
        refresh();

        sldR.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                txtReview.setBackgroundColor(Color.rgb((int)sldR.getValue(), (int)sldG.getValue(), (int)sldB.getValue()));
            }
        });
        sldB.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                txtReview.setBackgroundColor(Color.rgb((int)sldR.getValue(), (int)sldG.getValue(), (int)sldB.getValue()));
            }
        });
        sldG.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                txtReview.setBackgroundColor(Color.rgb((int)sldR.getValue(), (int)sldG.getValue(), (int)sldB.getValue()));
            }
        });

        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRGB();
            }
        });
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtShow.setBackgroundColor(Color.rgb((int)sldR.getValue(), (int)sldG.getValue(), (int)sldB.getValue()));
                runRGB(Float.toString(sldR.getValue()),Float.toString(sldB.getValue()),Float.toString(sldG.getValue()),Float.toString(sldBright.getValue()));
            }
        });
    }

    private void runRGB(final String red,final String blue, final String green,final String bright) {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "that bai", Toast.LENGTH_LONG).show();
            }
        }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("rValue", red);
                    map.put("bValue", blue);
                    map.put("gValue", green);
                    map.put("bnValue",bright);
                    return map;
                }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void loadRGB() {

        StringRequest request = new StringRequest(Request.Method.POST, api_getValue, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rgb(response);
                Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //displaying the error in toast if occurrs
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    public void rgb(String str){
        String data = str.replace("[","");
        data = data.replace("]","");
        data = data.replace("\"","");
        String[] dataF = data.split(",");
        int R = Integer.parseInt(dataF[0]);
        int G = Integer.parseInt(dataF[1]);
        int B = Integer.parseInt(dataF[2]);
        sldR.setValue((float) R);
        sldG.setValue((float) G);
        sldB.setValue((float) B);
        txtReview.setBackgroundColor(Color.rgb(R,G,B));
        txtShow.setBackgroundColor(Color.rgb(R,G,B));
    }

    public void refresh(){
        sldR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });

        sldB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });
        sldG.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });
        btnRun.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });
        txtReview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });
        txtShow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loadRGB();
                }
            }
        });
    }
}