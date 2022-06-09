package com.example.todotask.ui.newTask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todotask.LoginActivity;
import com.example.todotask.R;
import com.example.todotask.SqLite.DBHelper;
import com.example.todotask.databinding.FragmentGalleryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewTask extends Fragment {
    TextView TaskDate,name,des;
    Button save,cancel;
    DBHelper DB=new DBHelper(getContext());
    final Calendar myCalendar= Calendar.getInstance();

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewTaskViewModel galleryViewModel =
                new ViewModelProvider(this).get(NewTaskViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TaskDate=root.findViewById(R.id.EventDate);
        name=root.findViewById(R.id.task);
        des=root.findViewById(R.id.description);
        save=root.findViewById(R.id.saveBtn);
        cancel=root.findViewById(R.id.CancelBtn);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabelTo();
            }
        };
        TaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName,taskDes,taskdate;
                taskName=name.getText().toString().trim();
                taskDes=des.getText().toString().trim();
                taskdate=TaskDate.getText().toString().trim();
                saveTask(taskName,taskDes,taskdate );
            }
        });


        return root;
    }

    private void saveTask(String taskName, String taskDes, String taskdate ) {

        if(taskName.equals("")||taskdate.equals("")||taskDes.equals(""))
            Toast.makeText(getContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else{
            SharedPreferences preferences = this.getActivity().getSharedPreferences("EmailSharedPrefs", Context.MODE_PRIVATE);
            String user=preferences.getString("emailInfo","");
                    boolean insertTask = DB.insertTask(taskName,taskDes,taskdate,user );
                    if(insertTask==true){
                        Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }




    }

    private void updateLabelTo(){

        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        TaskDate.setText(dateFormat.format(myCalendar.getTime()));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}