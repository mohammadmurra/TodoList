package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todotask.SqLite.DBHelper;


public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    TextView SignUp;
    Button btnlogin;
    CheckBox remember;
   public static DBHelper DB;
    private static final String LOGIN_SHARED_PREFS = "loginSharedPrefs";
    private static final String Email_SHARED_PREFS = "EmailSharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.loginBtn);
        SignUp=(TextView) findViewById(R.id.signupView);
        remember=(CheckBox)findViewById(R.id.remember_me);
        DB = new DBHelper(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(remember.isChecked()){
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("email", user);

                    editor.putString("password", pass);
                    editor.commit();
                }

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Email_SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("emailInfo", user);
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }
} 