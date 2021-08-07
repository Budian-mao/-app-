package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class Bill extends Activity {
    private String loginid = login.post_userid;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM users WHERE userId = ?",
                      new String[]{loginid});
        Integer bill1 = 0;
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                bill1 = cursor.getInt(5);
                cursor.moveToNext();
            }
        }
        cursor.close();
        TextView yue = findViewById(R.id.yue);
        String aa = bill1.toString();
        yue.setText(aa);
        findViewById(R.id.button_bill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bill.this,Chongzhi.class));
            }
        });
    }
}
