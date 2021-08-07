package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity{
    private EditText User;
    private EditText Password1;
    private EditText Password2;
    private Button button_register;
    private Button button_return;
    private TextView first;
    String user=null;
    String password1=null;
    String password2=null;
    private Object Constant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        User=(EditText)findViewById(R.id.user);
        Password1=(EditText)findViewById(R.id.password1);
        Password2=(EditText)findViewById(R.id.password2);
        button_register=(Button)findViewById(R.id.register);
        button_return=(Button)findViewById(R.id.toReturn);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=User.getText().toString();
                password1=Password1.getText().toString();
                password2=Password2.getText().toString();
                if(user==null||user.equals("")){
                    Toast.makeText(getApplicationContext(), "请输入用户学号！", Toast.LENGTH_SHORT).show();
                }
                else if(password1==null||password1.equals("")){
                    Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
                }
                else if(!password1.equals(password2)){
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
                }


                else {
                    new Thread(()->{
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody formBody = new FormBody.Builder().add("id", user).add("password",password1).build();

                        Request request = new Request.Builder()
                                .url("http://121.43.181.253:8080/user/register")
                                .post(formBody)
                                .build();
                        try (Response response = okHttpClient.newCall(request).execute()) {
                            Looper.prepare();
                            if (Boolean.parseBoolean(response.body().string()))
                            {
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this,login.class);

                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();};
            }

        });
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                startActivity(new Intent(Register.this,login.class));
            }
        });
    }
}

