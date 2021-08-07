package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Chongzhi extends AppCompatActivity implements View.OnClickListener {

    private int[] ints = new int[]{1, 5, 10, 20, 50, 100};
    private ItemAdapter itemAdapter;
    private EditText edt_phone;
    private RecyclerView rv_recharge;
    private Button btn_recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chongzhi);
        initView();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rv_recharge.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(ints);
        rv_recharge.setAdapter(itemAdapter);
    }

    private void initView() {
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        rv_recharge = (RecyclerView) findViewById(R.id.rv_recharge);
        btn_recharge = (Button) findViewById(R.id.btn_recharge);
        btn_recharge.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String phone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "充值号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        int index = itemAdapter.getPositionIndex();
        String money = ints[index] + "元";
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor2 = db.query("users",null,"userId=?",new String[]{phone},null,null,null,null);
        int aa = 0;
        if (cursor2.moveToFirst()){
            while (!cursor2.isAfterLast()){
                aa =  cursor2.getInt(5);
                cursor2.moveToNext();
            }
        }
        aa = aa + ints[index];
        ContentValues values = new ContentValues();
        values.put("bill", aa);
        db.update("users",values,"userId=?",new String[] {phone});
        Toast.makeText(this, "充值账户：" + phone + " 充值金额：" + money, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Chongzhi.this,Myaty.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge:
                submit();
                break;
        }
    }
}

