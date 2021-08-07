package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import androidx.annotation.Nullable;

public class login extends Activity {
    private EditText User;
    private EditText Password;
    private Button button_login;
    private TextView first;
    private TextView toRegister;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    protected Intent intent;
    protected static String post_userid = "";
    String user = null;
    String password = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        User = (EditText) findViewById(R.id.login_user);
        Password = (EditText) findViewById(R.id.login_password);
        button_login = (Button) findViewById(R.id.login);
        toRegister = (TextView) findViewById(R.id.toRegister);
        post_userid = "";

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = User.getText().toString();
                password = Password.getText().toString();

                if (user.equals("") || user == null) {
                    Toast.makeText(getApplicationContext(), "请输入用户账号！", Toast.LENGTH_SHORT).show();
                }
                if (password.equals("") || password == null) {
                    Toast.makeText(getApplicationContext(), "请输入用户密码！", Toast.LENGTH_SHORT).show();
                }
                checkUser(user, password);
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Register.class));
            }
        });

    }

    private void checkUser(String user, String password) {
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            String sql = "SELECT * FROM users WHERE userId=? and passWord=?";
            Cursor cursor = db.rawQuery(sql, new String[]{user, password});
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "用户密码错误！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, Main_Page.class);
                post_userid = user;
                startActivity(intent);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
