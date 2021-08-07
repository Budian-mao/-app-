package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

public class Myaty extends AppCompatActivity {
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private Button myself;
    private Button myshow;
    private Button changepwd;
    private Button about;
    private Button Login;
    private TextView myId;
    protected Intent intent;
    private String a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaty);
        button1=(RadioButton)findViewById(R.id.button_1);
        button2=(RadioButton)findViewById(R.id.button_2);
        button3=(RadioButton)findViewById(R.id.button_3);
        myself=(Button)findViewById(R.id.myself);
        myshow=(Button)findViewById(R.id.myShow);
        changepwd=(Button)findViewById(R.id.changepwd);
        about=(Button)findViewById(R.id.about);
        Login=(Button)findViewById(R.id.login) ;
        myId=(TextView)findViewById(R.id.myId);
        a=login.post_userid;
        myId.setText(a);//显示用户账号
        if(a==null||a.equals("")){
           Login.setText("登 录");
        }else{
            Login.setText("退出登录");
        }

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Myaty.this,Main_Page.class));
            }
        });
        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Myaty.this,Myaty.class));
            }
        });
        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Myaty.this,Message_list.class));
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();
                    login.post_userid="";
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.myself).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(Myaty.this, PersonInformation.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.changepwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(Myaty.this,ChangePsw.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(Myaty.this,AboutUs.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.myShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(Myaty.this,MyRelase.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.publish_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==null||a.equals("")){
                    Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Myaty.this,login.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(Myaty.this,PublishGoods.class);
                    startActivity(intent);
                }
            }
        });
    }
}
