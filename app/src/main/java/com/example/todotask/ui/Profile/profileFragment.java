package com.example.todotask.ui.Profile;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todotask.LoginActivity;
import com.example.todotask.R;
import com.example.todotask.SqLite.DBHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class profileFragment extends Fragment {
    TextView email,fullName,firstName,secondName;
Button update ;
    String user;
    boolean IsPasswordValid = true, IsConPasswordValid = true;
    String regex;
    DBHelper DB;
    SharedPreferences preferences ;
    private static final String LOGIN_SHARED_PREFS = "loginSharedPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=.*[A-Z])"
                + "(?=.*[{}@#$%!])"
                + "(?=\\S+$).{8,15}$";

         preferences = getContext().getSharedPreferences("loginSharedPrefs", MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_profile,
                        container, false);
        DB = new DBHelper(getContext());
        // Inflate the layout for this fragment
        email=(TextView)view.findViewById(R.id.EmailView);
        fullName=(TextView)view.findViewById(R.id.FullName);
        firstName=(TextView)view.findViewById(R.id.FirstNameView);
        secondName=(TextView)view.findViewById(R.id.SecondNameView);
        update = (Button)view.findViewById(R.id.updateProfile) ;
        SharedPreferences preferences = this.getActivity().getSharedPreferences("EmailSharedPrefs", MODE_PRIVATE);
         user=preferences.getString("emailInfo","");
        readInfo(user);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        return  view;
    }

    private void readInfo(String user) {
       ArrayList<String> info=LoginActivity.DB.getInfo(user);
        Log.d(TAG, "getInfo: "+info.toString());

        email.setText(info.get(0));
        fullName.setText(info.get(1)+"  "+info.get(2));
        firstName.setText(info.get(1));
        secondName.setText(info.get(2));
    }

    private void updateProfile() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_edit_profile, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();

        final EditText firstName = view.findViewById(R.id.UPfirstname);
        final EditText lastName = view.findViewById(R.id.UPlastname);
        final EditText pass = view.findViewById(R.id.UPfirstname);
        final EditText conPass = view.findViewById(R.id.UPpassword);


        Button updateButton = view.findViewById(R.id.UpdateBTN);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   task = mTask.getText().toString().trim();
                //description = mDescription.getText().toString().trim();
                String updateFname, UpdateSname , UpdatePass , UpdateCpass;


                 updateFname = firstName.getText().toString();
                 UpdateSname = lastName.getText().toString();
                 UpdatePass = pass.getText().toString();
                 UpdateCpass = conPass.getText().toString();

                if(updateFname.equals("")||UpdatePass.equals("")||UpdateCpass.equals("")||updateFname.equals("")||UpdateSname.equals(""))
                    Toast.makeText(getContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (isEmpty(pass)) {
                        pass.setError("Password is required!");
                        IsPasswordValid = false;

                    } else {
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(pass.getText().toString());
                        if (m.matches()) {
                            IsPasswordValid = m.matches();
                        } else {
                            IsPasswordValid = m.matches();
                            pass.setError("Password must be minimum 8 characters and maximum 15 characters. It must contain at least one number,\n" +
                                    "one lowercase letter, one uppercase letter, and at least one special character from this character set\n" +
                                    "only: $, %, #, @, !, {, and }.");
                        }
                    }
                    if (isEmpty(conPass)) {
                        conPass.setError("Password confirmation is required!");
                        IsConPasswordValid = false;
                    } else if (conPass.getText().length() < 8 && conPass.getText().length() > 15) {
                        conPass.setError("Password must be between 8 and 15 characters long!");
                        IsConPasswordValid = false;
                    } else if (conPass.getText().toString().equals(pass.getText().toString())) {

                        IsConPasswordValid = true;
                    } else {
                        conPass.setError("Those passwords did not match. Try again!");
                        IsConPasswordValid = false;

                    }


                    if (IsConPasswordValid && IsPasswordValid) {

Boolean result =  DB.updateUser(user,UpdatePass,updateFname,UpdateSname);
                if(result==true){
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putString("email", user);

                    editor.putString("password", UpdatePass);
                    editor.commit();

                }
                else {
                    Toast.makeText(getContext(), "Update filed", Toast.LENGTH_SHORT).show();

                }


                        dialog.dismiss();


                    }
                }}});

                dialog.show();
            }





    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
        }
