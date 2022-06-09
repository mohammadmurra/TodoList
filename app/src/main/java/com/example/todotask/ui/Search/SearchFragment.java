package com.example.todotask.ui.Search;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todotask.Adapter;
import com.example.todotask.R;
import com.example.todotask.SqLite.DBHelper;
import com.example.todotask.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SearchFragment extends Fragment {
    private   int beforeDay ;
    private  int afteRDay ;

    ArrayList<Todo> model = new ArrayList<>();


    private Adapter adapter;
    DBHelper DB;
    private RecyclerView listView;
    private FloatingActionButton floatingActionButton;

    final Calendar myCalendar= Calendar.getInstance();

    private String key = "";
    private String task;
    private String description;

    TextView fromDate,toDate;
    Button searchButton ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB=  new DBHelper(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_search,
                        container, false);



        listView = (RecyclerView) view.findViewById(R.id.idRVSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        listView.setHasFixedSize(true);
        listView.setLayoutManager(linearLayoutManager);
        listView.addItemDecoration( new DividerItemDecoration( getContext(),LinearLayoutManager.VERTICAL));
        // loader = new ProgressDialog(this);



        floatingActionButton = view.findViewById(R.id.fab);
        searchButton = view.findViewById(R.id.searchBtn);
        fromDate=view.findViewById(R.id.fromDate);
        toDate=view.findViewById(R.id.toDate);




        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                afteRDay = day;
                updateLabelFrom();
            }
        };
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DatePickerDialog.OnDateSetListener dateTo =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                beforeDay= day;
                updateLabelTo();
            }
        };
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),dateTo,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        SharedPreferences preferences = this.getActivity().getSharedPreferences("EmailSharedPrefs", Context.MODE_PRIVATE);
        String user=preferences.getString("emailInfo","");


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = fromDate.getText().toString();
                String todate = toDate.getText().toString();
                // model =   DB.AllTask();
                Log.d(TAG, "testtttttttttttttt: "+start + " ssss" + todate);

                model= DB.searchTask(user,start,todate);

                adapter = new Adapter(model,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), listView.VERTICAL, false);
                listView.setLayoutManager(linearLayoutManager);

                // setting our adapter to recycler view.
                listView.setAdapter(adapter);

            }
        });
        return view;
    }




    private void updateLabelFrom(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.DAY_OF_MONTH,(afteRDay));

        fromDate.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabelTo(){
        myCalendar.set(Calendar.DAY_OF_MONTH,(beforeDay));

        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        toDate.setText(dateFormat.format(myCalendar.getTime()));
    }











}