package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;

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
