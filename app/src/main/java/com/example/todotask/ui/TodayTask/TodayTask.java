package com.example.todotask.ui.TodayTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todotask.Adapter;
import com.example.todotask.R;
import com.example.todotask.SqLite.DBHelper;
import com.example.todotask.Todo;

import java.util.ArrayList;


public class TodayTask extends Fragment {


        ArrayList<Todo> model = new ArrayList<>();
        private RecyclerView listView;

        private Adapter adapter;
        DBHelper DB;




        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            DB=  new DBHelper(getContext());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_today, container, false);

            // Inflate the layout for this fragment
            //   ;
            SharedPreferences preferences = this.getActivity().getSharedPreferences("EmailSharedPrefs", Context.MODE_PRIVATE);
            String user=preferences.getString("emailInfo","");
            // model =   DB.AllTask();
            model= DB.Todayask(user);
            listView = (RecyclerView) root.findViewById(R.id.idRVSToday);

            adapter = new Adapter(model,getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), listView.VERTICAL, false);
            listView.setLayoutManager(linearLayoutManager);

            // setting our adapter to recycler view.
            listView.setAdapter(adapter);

            return root;
        }

    }