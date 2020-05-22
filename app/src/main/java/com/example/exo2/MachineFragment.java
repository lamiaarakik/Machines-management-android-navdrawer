package com.example.exo2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.exo2.beans.User;
import com.example.exo2.dummy.DummyContent;
import com.example.exo2.dummy.Machine;
import com.example.exo2.dummy.Marque;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MachineFragment extends Fragment {
    private Machine m;
public ArrayList<Machine.MachineItem> mylist=new ArrayList<Machine.MachineItem>();
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;



   static RequestQueue requestQueue;

    static String insertUrl = "http://192.168.88.2:8090/machines/all";
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MachineFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MachineFragment newInstance(int columnCount) {
        MachineFragment fragment = new MachineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }




    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_itemmachine_list, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(insertUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Machine.MachineItem machine = new Machine.MachineItem();
                        Marque marque=new Marque();
                        machine.setId(String.valueOf(i+1));
                        machine.setReference(jsonObject.getString("reference"));
                        machine.setPrix(jsonObject.getString("prix"));

                        JSONObject mrq = jsonObject.getJSONObject("marque");
                        marque.setId(Integer.parseInt(mrq.getString("id")));
                        marque.setCode(mrq.getString("code"));
                        marque.setLibelle(mrq.getString("libelle"));
                        machine.setMarque(marque);




                        mylist.add(machine);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                Log.d("marque",mylist.get(0).getMarque().getLibelle());
                Log.d("machine",mylist.get(0).getId().toString());
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;

                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }

                    recyclerView.setAdapter(new MyItemMachineRecyclerViewAdapter(mylist, mListener));
                }
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




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Machine.MachineItem item);
    }
}
