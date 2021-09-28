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
import java.sql.Statement;

import static com.example.app_using_jdbc.MainActivity.pass;
import static com.example.app_using_jdbc.MainActivity.url;
import static com.example.app_using_jdbc.MainActivity.user;

public class LogIn extends AppCompatActivity {

    Button btn_sublog, btn_gohome;
    TextInputEditText textForUser, textForPass;
    public String userz,passz;
    public boolean success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btn_sublog=findViewById(R.id.btn_sublog);
        textForUser=findViewById(R.id.userz);
        textForPass=findViewById(R.id.passz);
        btn_gohome=findViewById(R.id.btn_gohome);

        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intw=new Intent(LogIn.this, MainActivity.class);
                startActivity(intw);
            }
        });

        btn_sublog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success=false;
                userz=String.valueOf(textForUser.getText());
                passz=String.valueOf(textForPass.getText());
                ConnectMySQL connectMySqlzz = new ConnectMySQL();
                connectMySqlzz.execute("");

            }
        });
    }

    private class ConnectMySQL extends AsyncTask<String,Void,String>
    {

        String resz="";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Pre execution starting");
            Toast.makeText(LogIn.this, "Executing..", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                resz="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection(url,user,pass);
                System.out.println("Database connection success!");

                String result="";
                Statement st=con.createStatement();

                ResultSet rs=st.executeQuery("SELECT * FROM `info_users_table` WHERE `USERNAME` LIKE '"+userz+"' AND `PASSWORD` LIKE '"+passz+"'");

                ResultSetMetaData rsmd = rs.getMetaData();
                while(rs.next())
                {
                    success=true;
                    result+=rs.getString(1).toString();
                }
                resz=result;
            } catch(Exception e)
            {
                e.printStackTrace();
                resz=e.toString();
            }
            return resz;

        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
            super.onPreExecute();
            if(success==true)
            {
                System.out.println("Account exists!!");
                Toast.makeText(LogIn.this, "Login Successful! Welcome back "+s, Toast.LENGTH_SHORT).show();
                Intent finalintent=new Intent(LogIn.this, FinalPage.class);
                startActivity(finalintent);
            }
            else
            {
                Toast.makeText(LogIn.this, "Invalid credentials!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

