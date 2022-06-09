package com.example.todotask.ui.All;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.todotask.Adapter;
import com.example.todotask.R;
import com.example.todotask.SqLite.DBHelper;
import com.example.todotask.Todo;

import java.util.ArrayList;


public class AllFragment extends Fragment {
ArrayList<Todo> model = new ArrayList<>();
    private RecyclerView listView;

    private Adapter adapter;
    DBHelper DB;
    public AllFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      DB=  new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all, container, false);

        // Inflate the layout for this fragment
        //   ;
        SharedPreferences preferences = this.getActivity().getSharedPreferences("EmailSharedPrefs", Context.MODE_PRIVATE);
        String user=preferences.getString("emailInfo","");
       // model =   DB.AllTask();
model= DB.AllTask(user);
            listView = (RecyclerView) root.findViewById(R.id.idRVS);

            adapter = new Adapter(model,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), listView.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
            listView.setAdapter(adapter);

         return root;
    }

}