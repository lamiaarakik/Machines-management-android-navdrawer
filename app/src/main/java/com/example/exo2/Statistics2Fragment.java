package com.example.exo2;
import android.os.Bundle;

import com.example.exo2.R;
import com.example.exo2.beans.MarqueCounter;
import com.example.exo2.beans.MachineDate;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Statistics2Fragment extends Fragment {
    private BarChart mBarChart;
    static RequestQueue requestQueue;

    static String insertUrl = "http://192.168.88.2:8090/machines/all";
    public ArrayList<MachineDate> mylist=new ArrayList<MachineDate>();

    public Statistics2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_statistics2, container, false);
        mBarChart=view.findViewById(R.id.dateChart);
        requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(insertUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                                   for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                           MachineDate md=new MachineDate();
                         String date=jsonObject.getString("dateAchat");

                            try {
                                Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
                                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                                String year = df.format(date1);


                                md.setDateAchat(year );
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            mylist.add(md);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                                   int c17=0;
                                   int c18=0;
                                   int c19=0;
                                  for(int i=0; i<mylist.size();i++){
                                       if (mylist.get(i).getDateAchat().equals("2017")){
                                           c17++;
                                       }
                                      else if (mylist.get(i).getDateAchat().equals("2018")){
                                          c18++;
                                      }
                                       else if (mylist.get(i).getDateAchat().equals("2019")){
                                           c19++;
                                       }
                                  }




                 Log.d("total",String.valueOf(mylist.get(0).getDateAchat()));

                ArrayList<BarEntry> entries = new ArrayList<>();
            String[] years=new String[]{"2017","2018","2019"};

                    entries.add(new BarEntry(0, c17));
                entries.add(new BarEntry(1, c18));

                entries.add(new BarEntry(2, c19));



                BarDataSet barDataSet = new BarDataSet(entries, "nbr d'achat par année");
                barDataSet.setBarBorderWidth(0.9f);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData barData = new BarData(barDataSet);

                XAxis xAxis = mBarChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(years);
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
