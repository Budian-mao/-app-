package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
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

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Item extends Activity {
    //private Integer item_position = Main_Page.item_position+1;
    protected static String item_userid = Main_Page.item_userid;
    private String title=Main_Page.item_title;
    private String price=Main_Page.item_price;
    private String info=Main_Page.item_info;
    private Bitmap image = Main_Page.item_image;
    private String post_userid = login.post_userid;
    private String userid = Main_Page.item_userid;
    private String contact=Main_Page.item_contact;
    private String itemid=Main_Page.item_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
     //   final DatabaseHelper dbtest = new DatabaseHelper(this);
        final Intent intent = getIntent();
        //final SQLiteDatabase db = dbtest.getWritableDatabase();
        //ImageView image = (ImageView)findViewById(R.id.imageView);
        TextView Price = (TextView)findViewById(R.id.item_price);
        TextView Title = (TextView)findViewById(R.id.item_title) ;
        TextView Info = (TextView)findViewById(R.id.item_info);
        TextView Contact = (TextView)findViewById(R.id.contact);
        ImageView Image = (ImageView)findViewById(R.id.item_image);
        Image.setImageBitmap(image);
        Title.setText(title);
        Price.setText(price);
        Info.setText(info);
        Contact.setText(contact);
       // Cursor cursor = db.query("iteminfo",null,"title=?",new String[]{title},null,null,null,null); // 根据接收到的id进行数据库查询
        Log.i("商品的title是",title);
        /*if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                itemid = cursor.getString(0);
                userid = cursor.getString(1);
                Title.setText(cursor.getString(2));
                Price.setText(cursor.getString(5));
                Info.setText(cursor.getString(4));
                Contact.setText(cursor.getString(8));
                cursor.moveToNext();
            }
        }

*/

        findViewById(R.id.want).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Item.this,Chatting1.class));

            }
        });
        findViewById(R.id.but1_m1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Item.this,Main_Page.class));
            }
        });


        ListView commentList = (ListView)findViewById(R.id.commentList);

        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
     /*   Cursor cursor_ = db.query("comments",null,"itemId=?",new String[]{itemid},null,null,null,null); // 数据库查询
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
        */

        Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
            .add("itemId",itemid)
            .build();
            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/comment/list")
                     .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                List<Comments> data1 = JSONArray.parseArray(response.body().string(),Comments.class);

                Map<String, Object> item;  // 列表项内容用Map存储
                for (int i = 0; i < data1.size(); ++ i){
                    item = new HashMap<String, Object>();
                    // 为列表项赋值
                    Comments temp=data1.get(i);
                    item.put("userId",temp.getUserId());
                    item.put("comment",temp.getComment());
                    item.put("time",temp.getTime());
                    data.add(item); // 加入到列表中
                }



                String pause="pause";
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
               /*
                values.put("userId",post_userid);
                values.put("itemId",itemid);
                values.put("comment",submit_comment);
                values.put("time",time);
                db.insert("comments",null,values);
                */

                new Thread(()->{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("userId",post_userid)
                            .add("itemId",itemid)
                            .add("comment",submit_comment)
                            .add("time",time)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://121.43.181.253:8080/comment/add")
                            .post(formBody)
                            .build();
                    try (Response response = okHttpClient.newCall(request).execute()) {
                        Looper.prepare();
                        if (Boolean.parseBoolean(response.body().string()))
                        {
                            Log.i("1","评论成功");
                            Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                            Intent intent_=new Intent(Item.this,Item.class);
                            intent_.putExtra("id",intent.getStringExtra("id"));
                            startActivity(intent_);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();


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
