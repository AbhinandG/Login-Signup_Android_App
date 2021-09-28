package com.example.app_using_jdbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static com.example.app_using_jdbc.MainActivity.pass;
import static com.example.app_using_jdbc.MainActivity.url;
import static com.example.app_using_jdbc.MainActivity.user;

public class FinalPage extends AppCompatActivity {

    TextView tv_title, tv_user, usernamevals;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);

        tv_title=findViewById(R.id.tv_title);
        tv_user=findViewById(R.id.tv_user);
        usernamevals=findViewById(R.id.usernamevals);
        btn_logout=findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intfff=new Intent(FinalPage.this, SignUp.class);
                startActivity(intfff);
            }
        });


        FinalPage.ConnectMySQL connectMySqlzz = new FinalPage.ConnectMySQL();
        connectMySqlzz.execute("");
    }

    private class ConnectMySQL extends AsyncTask<String,Void,String>
    {

        String resz="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                ResultSet rs=st.executeQuery("SELECT `USERNAME`, `PASSWORD` FROM `info_users_table`");

                ResultSetMetaData rsmd = rs.getMetaData();
                while(rs.next())
                {
                    result+=(rs.getString(1).toString()+ "\n");
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
            usernamevals.setText(s);
        }
    }
}