package com.example.exo2;


import android.os.Bundle;
import com.example.exo2.beans.MarqueCounter;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.exo2.dummy.Machine;
import com.example.exo2.dummy.Marque;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Statistics1Fragment extends Fragment {
private BarChart mBarChart;
    static RequestQueue requestQueue;

    static String insertUrl = "http://192.168.88.2:8090/marques/count";
    public ArrayList<MarqueCounter> mylist=new ArrayList<MarqueCounter>();

    public Statistics1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.fragment_statistics1, container, false);
     mBarChart=view.findViewById(R.id.barChart);
        requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(insertUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject= null;
                try {
                    jsonObject = response.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator iterator   = jsonObject.keys();
                List<String> keysList = new ArrayList<String>();
                while(iterator.hasNext()) {
                    String key = (String) iterator.next();
                    keysList.add(key);
                    Log.d("keys",key);
                }
                for(String i:keysList){
                    try {
                        MarqueCounter mrq=new MarqueCounter();
                        mrq.setLibelle(i);
                       mrq.setCount(Integer.parseInt(jsonObject.getString(i)));
                        mylist.add(mrq);
                    }catch (JSONException e){

                    }


                } Log.d("total",String.valueOf(mylist.get(0).getCount()));
                Log.d("libelle",mylist.get(1).getLibelle());
                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> libelles =new ArrayList<String>();

                for(int i=0;i<mylist.size();i++){

                    entries.add(new BarEntry(i, mylist.get(i).getCount()));
                    libelles.add(mylist.get(i).getLibelle());
                }



                BarDataSet barDataSet = new BarDataSet(entries, "nombre de machine par marque");
                barDataSet.setBarBorderWidth(0.9f);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData barData = new BarData(barDataSet);

                XAxis xAxis = mBarChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(libelles);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(formatter);
               mBarChart.setData(barData);
               mBarChart.setVisibility(View.VISIBLE);
                mBarChart.setFitBars(true);
                mBarChart.animateXY(5000,5000);
                mBarChart.invalidate();



            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

        // Set the adapter

        return view;
    }


}
