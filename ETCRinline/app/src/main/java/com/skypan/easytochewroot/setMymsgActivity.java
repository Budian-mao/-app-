package com.skypan.easytochewroot;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static com.skypan.easytochewroot.R.id.phone;
import static com.skypan.easytochewroot.R.id.qq;
import static com.skypan.easytochewroot.R.id.subject;
public class setMymsgActivity extends AppCompatActivity {
    private TextView userid;
    private EditText username;
    private EditText usersubject;
    private EditText userphone;
    private EditText userqq;
    private EditText useraddress;
    private Button usersave;
    private Button back;
    protected Intent intent;
    private String post_name=null,post_subject=null,post_phone=null,post_qq=null,post_address=null,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mymsg);

        userid=(TextView)findViewById(R.id.showUser);
        username=(EditText)findViewById(R.id.name);
        usersubject=(EditText)findViewById(subject);
        userphone=(EditText)findViewById(phone);
        userqq=(EditText)findViewById(qq);
        usersave=(Button)findViewById(R.id.save);
        back=(Button)findViewById(R.id.back);
        id=login.post_userid;
        userid.setText(id);
        if(id==null||id.equals("")){
            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            intent = new Intent(setMymsgActivity.this,Myaty.class);
            startActivity(intent);
        }
        //账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq,地址address
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();

        usersave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq
                post_name=username.getText().toString();
                post_subject=usersubject.getText().toString();
                post_phone=userphone.getText().toString();
                post_qq=userqq.getText().toString();
                ContentValues values=new ContentValues();
                if(!post_name.equals("")) {
                    values.put("name", post_name);
                }
                if(!post_subject.equals("")) {
                    values.put("subject", post_subject);
                }
                if(!post_phone.equals("")) {
                    values.put("phone", post_phone);
                }
                if(!post_qq.equals("")) {
                    values.put("qq", post_qq);
                }
                saveValues(values);
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                intent = new Intent(setMymsgActivity.this,PersonInformation.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(setMymsgActivity.this,PersonInformation.class);
                startActivity(intent);
            }
        });
    }
    private void saveValues(ContentValues values){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        db.update("users",values,"userId=?",new String[] {id});
        db.close();
    }
}

