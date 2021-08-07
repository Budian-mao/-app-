package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

public class Item extends Activity {
    //private Integer item_position = Main_Page.item_position+1;
    protected static String item_userid = null;
    private String title=Main_Page.item_title;
    private String post_userid = login.post_userid;
    private String price=" ";
    String pp = null;
    Integer aa = 0;
    private String userid = " ";
    private String info=" ";
    private String contact=" ";
    private String itemid=" ";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final Intent intent = getIntent();
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        //ImageView image = (ImageView)findViewById(R.id.imageView);
        TextView Price = (TextView)findViewById(R.id.item_price);
        TextView Title = (TextView)findViewById(R.id.item_title) ;
        TextView Info = (TextView)findViewById(R.id.item_info);
        TextView Contact = (TextView)findViewById(R.id.contact);
        Cursor cursor = db.query("iteminfo",null,"title=?",new String[]{title},null,null,null,null); // 根据接收到的id进行数据库查询
        Log.i("商品的title是",title);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                itemid = cursor.getString(0);
                userid = cursor.getString(1);
                Title.setText(cursor.getString(2));
                Price.setText(cursor.getString(5));
                pp = cursor.getString(5);
                Info.setText(cursor.getString(4));
                Contact.setText(cursor.getString(8));
                cursor.moveToNext();
            }
        }


        findViewById(R.id.want).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_userid = userid;
                startActivity(new Intent(Item.this,Chatting.class));

            }
        });
        findViewById(R.id.but1_m1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Item.this,Main_Page.class));
            }
        });

        findViewById(R.id.goumai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Item.this);
                builder.setTitle("购物提示");
                builder.setMessage("将直接扣除您账户的余额，购买成功您需要自行与卖家联系，您是否要继续购买");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aa = Integer.valueOf(pp);
                        DatabaseHelper database = new DatabaseHelper(Item.this);
                        SQLiteDatabase db = database.getWritableDatabase();
                        Cursor cursor2 = db.query("users",null,"userId=?",new String[]{post_userid},null,null,null,null);
                        int aaa = 0;
                        if (cursor2.moveToFirst()){
                            while (!cursor2.isAfterLast()){
                                aaa =  cursor2.getInt(5);
                                cursor2.moveToNext();
                            }
                        }
                        DatabaseHelper database1 = new DatabaseHelper(Item.this);
                        SQLiteDatabase db1 = database1.getWritableDatabase();
                        Cursor cursor3 = db1.query("users",null,"userId=?",new String[]{userid},null,null,null,null);
                        int aaaa = 0;
                        if (cursor3.moveToFirst()){
                            while (!cursor3.isAfterLast()){
                                aaaa =  cursor3.getInt(5);
                                cursor3.moveToNext();
                            }
                        }

                        if(aaa<aa){
                            Toast.makeText(Item.this, "余额不足", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            aaa = aaa - aa;
                            ContentValues values = new ContentValues();
                            values.put("bill", aaa);
                            db.update("users",values,"userId=?",new String[] {post_userid});

                            aaaa = aaaa + aa;
                            ContentValues values1 = new ContentValues();
                            values1.put("bill", aaaa);
                            db.update("users",values1,"userId=?",new String[] {userid});
                            Toast.makeText(Item.this, "交易成功，请及时联系卖家", Toast.LENGTH_SHORT).show();
                            db.execSQL("DELETE FROM iteminfo WHERE title = ?",
                                    new String[]{title});
                            startActivity(new Intent(Item.this,Main_Page.class));
                        }


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Item.this,Item.class));
                    }
                });
                //一样要show
                builder.show();
            }
        });


        findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor2 = db.query("iteminfo",null,"title=?",new String[]{title},null,null,null,null);
                Map<String, Object> item2;
                final List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>(); // 列表
                if (cursor2.moveToFirst()){
                    while (!cursor2.isAfterLast()){
                        item2 = new HashMap<String, Object>();  // 为列表项赋值
                        item2.put("id",cursor2.getString(0));
                        item2.put("userid",cursor2.getString(1));
                        item2.put("title",cursor2.getString(2));
                        item2.put("kind",cursor2.getString(3));
                        item2.put("info",cursor2.getString(4));
                        item2.put("price",cursor2.getString(5));
                        item2.put("image",cursor2.getString(6));
                        item2.put("star",cursor2.getInt(7));
                        item2.put("time",cursor2.getString(8));
                        item2.put("contact",cursor2.getString(9));
                        cursor2.moveToNext();
                        data2.add(item2);
                    }
                }
                Integer a = (Integer) data2.get(0).get("star");
                a = a + 1;
                ContentValues values111=new ContentValues();
                values111.put("title",(String) data2.get(0).get("title"));
                values111.put("userId",(String)data2.get(0).get("userId"));
                values111.put("userId",(String) data2.get(0).get("userId"));
                values111.put("kind", (String) data2.get(0).get("kind"));
                values111.put("time",(String) data2.get(0).get("time"));
                values111.put("price",(String) data2.get(0).get("price"));
                values111.put("contact",(String) data2.get(0).get("contact"));
                values111.put("info",(String) data2.get(0).get("info"));
                values111.put("stars",a);
                String whereClause = "title=?";
                String[] whereArgs = {title};
                db.delete("iteminfo",whereClause,whereArgs);
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                db.insert("iteminfo",null,values111);
            }
        });


        ListView commentList = (ListView)findViewById(R.id.commentList);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor_ = db.query("comments",null,"itemId=?",new String[]{itemid},null,null,null,null); // 数据库查询
        if (cursor_.moveToFirst()){
            while (!cursor_.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                item.put("userId",cursor_.getString(0));
                item.put("comment",cursor_.getString(2));
                item.put("time",cursor_.getString(3));
                cursor_.moveToNext();
                data.add(item); // 加入到列表中
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.comment_item, new String[] { "userId", "comment", "time"},
                new int[] { R.id.userId, R.id.commentInfo, R.id.time });
        commentList.setAdapter(simpleAdapter);


        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText comment = (EditText)findViewById(R.id.comment);
                String submit_comment = comment.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);
                ContentValues values=new ContentValues();
                values.put("userId",post_userid);
                values.put("itemId",itemid);
                values.put("comment",submit_comment);
                values.put("time",time);
                db.insert("comments",null,values);
                Log.i("1","评论成功");
                Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                Intent intent_=new Intent(Item.this,Item.class);
                intent_.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent_);
            }
        });

        //init();
    }
    /*private void init(){
        /*DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.query("iteminfo",null,null,null,null,null,null,null); // 数据库查询
        //Cursor cursor =  db.rawQuery("SELECT * FROM iteminfo WHERE id = 3",null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                title = cursor.getString(3);
                price = cursor.getString(6);
                info = cursor.getString(5);
                contact = cursor.getString(9);
            }
        }
        cursor.close();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        /*Cursor cursor =  db.rawQuery("SELECT * FROM iteminfo WHERE kind = ?",
                new String[]{"电子产品"});
        title = cursor.getString(3);
        price = cursor.getString(6);
        info = cursor.getString(5);
        contact = cursor.getString(9);
        TextView TITLE = (TextView)findViewById(R.id.item_title);
        TextView PRICE = (TextView)findViewById(R.id.item_price);
        TextView INFO = (TextView)findViewById(R.id.item_info);
        TextView CONTACT = (TextView)findViewById(R.id.contact);
        TITLE.setText(title);
        //PRICE.setText("2");
        //INFO.setText("3");
        //CONTACT.setText("4");
    }*/
}
