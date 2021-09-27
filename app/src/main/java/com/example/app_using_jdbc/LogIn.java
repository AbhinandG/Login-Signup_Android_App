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
import java.sql.Statement;

import static com.example.app_using_jdbc.MainActivity.pass;
import static com.example.app_using_jdbc.MainActivity.url;
import static com.example.app_using_jdbc.MainActivity.user;

public class LogIn extends AppCompatActivity {

    Button btn_sublog;
    TextInputEditText textForUser, textForPass;
    public String user,pass;
    public boolean success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btn_sublog=findViewById(R.id.btn_sublog);
        textForUser=findViewById(R.id.user);
        textForPass=findViewById(R.id.pass);

        btn_sublog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success=false;
                user=String.valueOf(textForUser.getText());
                pass=String.valueOf(textForPass.getText());
                ConnectMySQLs connectMySqlzz = new ConnectMySQLs();
                connectMySqlzz.execute("");

            }
        });
    }

    public class ConnectMySQLs extends AsyncTask<String,Void,String>
    {

        String res="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Pre execution starting");
            Toast.makeText(LogIn.this, "Executing..", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {

            res="";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection(url,user,pass);
                System.out.println("Database connection success!");

                String result="Database connection successful\n";
                Statement st=con.createStatement();

                ResultSet rs=st.executeQuery("SELECT * FROM info_users_table WHERE USERNAME="+user+"AND PASSWORD="+pass);
                if(rs.next())
                {
                    success=true;
                }
                res=result;
            } catch(Exception e)
            {
                e.printStackTrace();
                res=e.toString();
            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(success)
            {
                System.out.println("Account exists!!");
                Toast.makeText(LogIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

