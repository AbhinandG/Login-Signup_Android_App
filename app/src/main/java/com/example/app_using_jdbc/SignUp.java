package com.example.app_using_jdbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import static com.example.app_using_jdbc.MainActivity.pass;
import static com.example.app_using_jdbc.MainActivity.url;
import static com.example.app_using_jdbc.MainActivity.user;


public class SignUp extends AppCompatActivity {

    Button btn_submit, button2;
    TextInputEditText textForUsername, textForEmail, textForPassword;

    public String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_submit=findViewById(R.id.btn_submit);
        textForUsername=findViewById(R.id.username);
        textForEmail=findViewById(R.id.email);
        textForPassword=findViewById(R.id.password);
        button2=findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ints=new Intent(SignUp.this, MainActivity.class);
                startActivity(ints);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=String.valueOf(textForUsername.getText());
                email=String.valueOf(textForEmail.getText());
                password=String.valueOf(textForPassword.getText());

                ConnectMySQL connectMySql = new ConnectMySQL();
                connectMySql.execute("");

            }
        });
    }

    public class ConnectMySQL extends AsyncTask<String,Void,String> {

        String res="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Pre execution starting");
            Toast.makeText(SignUp.this, "Executing..", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection(url,user,pass);
                System.out.println("Database connection success!");

                String result="Database connection successful\n";
                Statement st=con.createStatement();

                int rs=st.executeUpdate("INSERT INTO info_users_table(USERNAME,EMAIL,PASSWORD) VALUES('"+username+"','"+email+"','"+password+"') ");


                res=result;

            } catch(Exception e)
            {
                e.printStackTrace();
                res=e.toString();
            }
            return res;

        }

    }
}

