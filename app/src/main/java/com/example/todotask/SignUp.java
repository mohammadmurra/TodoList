package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todotask.SqLite.DBHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText email,firstName,lastName, password, repassword;
    Button signup;
    DBHelper DB;
    boolean IsPasswordValid = true, IsConPasswordValid = true;
    String regex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=.*[A-Z])"
                + "(?=.*[{}@#$%!])"
                + "(?=\\S+$).{8,15}$";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=(EditText) findViewById(R.id.email);
        lastName=(EditText) findViewById(R.id.lastname);
        firstName = (EditText) findViewById(R.id.firstname);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.signupBtn);

        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();
                String firstname = firstName.getText().toString();
                String lastname = lastName.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("")||firstname.equals("")||lastname.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(isEmpty(password) ){password.setError("Password is required!");
                        IsPasswordValid = false;

                    } else {
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(password.getText().toString());
                        if (m.matches()) {
                            IsPasswordValid = m.matches();
                        } else {
                            IsPasswordValid = m.matches();
                            password.setError("Password must be minimum 8 characters and maximum 15 characters. It must contain at least one number,\n" +
                                    "one lowercase letter, one uppercase letter, and at least one special character from this character set\n" +
                                    "only: $, %, #, @, !, {, and }.");
                        }
                    }
                    if (isEmpty(repassword)) {
                        repassword.setError("Password confirmation is required!");
                        IsConPasswordValid = false;
                    } else if (repassword.getText().length() < 8 && repassword.getText().length() > 15) {
                        repassword.setError("Password must be between 8 and 15 characters long!");
                        IsConPasswordValid = false;
                    } else if (repassword.getText().toString().equals(password.getText().toString())) {

                        IsConPasswordValid = true;
                    } else {
                        repassword.setError("Those passwords did not match. Try again!");
                        IsConPasswordValid = false;

                    }



                    if(IsConPasswordValid && IsPasswordValid ){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,pass,firstname,lastname);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });

    }
    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}