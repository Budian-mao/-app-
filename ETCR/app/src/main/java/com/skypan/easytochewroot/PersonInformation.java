package com.skypan.easytochewroot;

import android.app.Activity;
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
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONSerializer;
import org.w3c.dom.Text;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonInformation extends AppCompatActivity {
    private TextView userid;
    private TextView username;
    private TextView usersubject;
    private TextView userphone;
    private TextView userqq;
    private Button userchange;
    private Button back;
    protected Intent intent;
    private String id,name,subject,phone,qq;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personinformation);
        userid = (TextView) findViewById(R.id.showUser);
        username = (TextView) findViewById(R.id.name);
        usersubject = (TextView) findViewById(R.id.subject);
        userphone = (TextView) findViewById(R.id.phone);
        userqq = (TextView) findViewById(R.id.qq);
        userchange = (Button) findViewById(R.id.changemsg);
        back = (Button) findViewById(R.id.back);
        id = login.post_userid;
        userid.setText(id);
        if(id==null||id.equals("")){
           Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            intent = new Intent(PersonInformation.this, login.class);
            startActivity(intent);
        }
        else{
/*
            DatabaseHelper dbhelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            try {
                String sql = "SELECT * FROM users WHERE userId=?";
                Cursor cursor = db.rawQuery(sql, new String[]{id});
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "用户不存在！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        subject = cursor.getString(cursor.getColumnIndex("subject"));
                        phone = cursor.getString(cursor.getColumnIndex("phone"));
                        qq = cursor.getString(cursor.getColumnIndex("qq"));
                    }
                    username.setText(name);
                    userphone.setText(phone);
                    usersubject.setText(subject);
                    userqq.setText(qq);
                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast.makeText(getApplicationContext(), "无法显示个人信息", Toast.LENGTH_SHORT).show();
            }
*/



           Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
            .add("id",id)
            .build();
            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/user/read")
                     .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {

                String test1=response.body().string();
                JSONObject userJson = JSONObject.parseObject(test1);
                User temp= JSON.toJavaObject(userJson,User.class);
               // List<User> data1 = JSONArray.parseArray(response.body().string(),User.class);

                   name=temp.getName();
                    phone=temp.getPhone();
                    subject=temp.getDepart();
                    qq=temp.getQq();



            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        long start = System.currentTimeMillis();
        System.out.println("start = " + start);
        t.start();
        long end = 0;
        while(t.isAlive() == true){//t.getState() != State.TERMINATED这两种判断方式都可以
            end = System.currentTimeMillis();
        }
        System.out.println("end = " + end);
        System.out.println("end - start = " + (end - start));

            username.setText(name);
            userphone.setText(phone);
            usersubject.setText(subject);
            userqq.setText(qq);


        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInformation.this,Myaty.class));
            }
        });
        findViewById(R.id.changemsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonInformation.this,setMymsgActivity.class));
            }
        });
    }
}
