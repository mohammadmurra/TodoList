package com.example.todotask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        // variable for our array list and context
        private ArrayList<Todo> ModalArrayList;
        private Context context;

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
        }

        @Override
        public int getItemCount() {
            // returning the size of our array list
            return ModalArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            // creating variables for our text views.
            private TextView dateTv, id, email, descriptionTv,taskTv;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our text views
                taskTv=itemView.findViewById(R.id.taskTv);
                dateTv = itemView.findViewById(R.id.dateTv);
              //  id = itemView.findViewById(R.id.idTv);
              //  email = itemView.findViewById(R.id.emailTv);
                descriptionTv = itemView.findViewById(R.id.descriptionTv);
            }
        }
    }

