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
                    checkUser(user,password1);
                }
            }

        });
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,login.class));
            }
        });
    }

    private void checkUser(String user,String pwd){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try{
            String sql="SELECT * FROM users WHERE userId=?";
            Cursor cursor=db.rawQuery(sql,new String[]{user});
            if(cursor.getCount()>0){
                Toast.makeText(getApplicationContext(), "学号已存在！", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues values = new ContentValues();
                //开始组装第一条数据   //账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
                values.put("userId",user);
                values.put("passWord",pwd);
                db.insert("users",null,values);//插入第一条数据
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this,login.class);
                startActivity(intent);
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
        }
    }

}
