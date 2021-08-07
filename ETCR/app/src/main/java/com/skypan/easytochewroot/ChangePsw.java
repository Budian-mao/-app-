package com.skypan.easytochewroot;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ChangePsw extends AppCompatActivity {
    private TextView showuser;
    private EditText oldpwd;
    private EditText newpwd;
    private EditText newpwd1;
    private Button change;
    private Button back;
    private String oldpass,newpass,newpass1,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepsw);

        showuser=(TextView)findViewById(R.id.showuser);
        oldpwd=(EditText)findViewById(R.id.oldpwd);
        newpwd=(EditText)findViewById(R.id.newpwd);
        newpwd1=(EditText)findViewById(R.id.newpwd1);
        change=(Button)findViewById(R.id.change);
        back=(Button)findViewById(R.id.back);
        user=login.post_userid;
        showuser.setText(user);
        if(user.equals("")||user==null){
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePsw.this,login.class);
            startActivity(intent);
        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("用户数据",user);
                oldpass=oldpwd.getText().toString();
                newpass=newpwd.getText().toString();
                newpass1=newpwd1.getText().toString();

                boolean flag=true;
                if(oldpass.equals("")||oldpass==null){
                    Toast.makeText(getApplicationContext(), "请输入旧密码！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(newpass.equals("")||newpass==null){
                    Toast.makeText(getApplicationContext(), "请输入新密码！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if (!newpass.equals(newpass1)){
                    Toast.makeText(getApplicationContext(), "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    flag=false;
                }
                if(flag){
                    checkpass(oldpass,newpass);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePsw.this,Myaty.class);
                startActivity(intent);
            }
        });

    }

    private void checkpass(String oldpass,String newpass) {
        String feihua ="feihua";
        new Thread(() -> {
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("id", user)
                    .add("password",newpass)
                    .add("name",feihua)
                    .add("depart",feihua)
                    .add("phone",feihua)
                    .add("qq", feihua)
                    .build();


            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/user/modify")
                    .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                Looper.prepare();
                if (Boolean.parseBoolean(response.body().string())) {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePsw.this, Myaty.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}