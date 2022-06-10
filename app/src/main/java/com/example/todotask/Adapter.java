package com.example.todotask;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todotask.SqLite.DBHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
String id , task , description , user , date;
    DBHelper DB;
    // variable for our array list and context
    private ArrayList<Todo> ModalArrayList;
    private Context context;
    private Todo modal;
    // constructor
    public Adapter(ArrayList<Todo> ModalArrayList, Context context) {
        this.ModalArrayList = ModalArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_task, parent, false);
        DB = new DBHelper(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        Todo modal = ModalArrayList.get(position);
        holder.dateTv.setText(modal.getDate());
        //  holder.id.setText(modal.getId());
        // holder.email.setText(modal.getUserName());
        holder.taskTv.setText(modal.getName());
        holder.descriptionTv.setText(modal.getDecrption());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               id = modal.getId();
                task = modal.getName();
                description = modal.getDecrption();
                user = modal.getUserName();
                date = modal.getDate();
                updateTask();
            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return ModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView dateTv, id, email, descriptionTv, taskTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            taskTv = itemView.findViewById(R.id.taskTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            //  id = itemView.findViewById(R.id.idTv);
            //  email = itemView.findViewById(R.id.emailTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
        }
    }

    private void updateTask() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragmeen_modefie, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();

        final EditText mTask = view.findViewById(R.id.mEditTextTask);
        final EditText mDescription = view.findViewById(R.id.mEditTextDescription);

       mTask.setText(task);
      mTask.setSelection(task.length());
//
        mDescription.setText(description);
        mDescription.setSelection(description.length());

        Button delButton = view.findViewById(R.id.btnDelete);
        Button updateButton = view.findViewById(R.id.btnUpdate);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   task = mTask.getText().toString().trim();
                //description = mDescription.getText().toString().trim();
                Toast.makeText(context, "id" + id, Toast.LENGTH_SHORT).show();

                Boolean resul =  DB.updateTask( id.trim(),  task,  description,  date , user );
              if(resul==true)
                Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
else
                  Toast.makeText(context, "filed", Toast.LENGTH_SHORT).show();


       //         Model model = new Model(task, description, key, date);



                dialog.dismiss();

            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteCourse(id);
                dialog.dismiss();
                    }


        });

     dialog.show();
    }

}


