package com.example.app_using_jdbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final String url = "jdbc:mysql://192.168.64.2:3306/info_users";
    public static final String user = "abhi";
    public static final String pass = " ";

    Button btn_login, btn_signup;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Going to SignUp...", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(MainActivity.this, SignUp.class);
                startActivity(intent1);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Going to LogIn...", Toast.LENGTH_SHORT).show();
            }
        });


    }
}