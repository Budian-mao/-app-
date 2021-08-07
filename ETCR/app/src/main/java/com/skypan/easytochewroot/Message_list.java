package com.skypan.easytochewroot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.skypan.easytochewroot.SwipeAdapter;
import com.skypan.easytochewroot.WXMessage;
import com.skypan.easytochewroot.SwipeListView;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Message_list extends Activity {
    private List<WXMessage> data = new ArrayList<WXMessage>();
    private SwipeListView mListView;
    protected static String message_title = " ";
    private String loginid = login.post_userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Message_list.this,Myaty.class));
            }
        });
        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Message_list.this,Main_Page.class));
            }
        });
        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Message_list.this,Message_list.class));
            }
        });
        initData();
        initView();
    }

    private void initData() {
        /*Databaseelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM message WHERE msgsenderid=?",new String[]{loginid});
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                WXMessage msg = null;
                msg = new WXMessage(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                cursor.moveToNext();
                data.add(msg); // 加入到列表中
            }
        }
        cursor.close();
*/

        Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("id",loginid)
                    .build();
            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/message/search")
                    .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                List<String> data1 = JSONArray.parseArray(response.body().string(),String.class);

                Map<String, Object> item;  // 列表项内容用Map存储
                for (int i = 0; i < data1.size(); ++ i){

                    WXMessage msg = null;
                    String tt =data1.get(i) ;//这里就是tt等于list值
                    msg = new WXMessage();

                    msg.setTitle(tt);
                    msg.setIcon_id(R.drawable.qq_icon);
                    data.add(msg);
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



        //需要传递的值是login.id（用户的id）
        //需要从远程数据库得到数据的部分，这里得到的数据是一个名单（获取可以用list）
        //获取一个list之后需要做的是遍历这个list，把list里的名字读成string数组
        /*for(int i=0;i<3;i++){
            WXMessage msg = null;
            String tt = "abc";//这里就是tt等于list值
            msg = new WXMessage();

            msg.setTitle(tt);
            msg.setIcon_id(R.drawable.qq_icon);
            data.add(msg);
        }
        */

    }

    /**
     * 初始化界面
     */
    private void initView() {

        mListView = (SwipeListView)findViewById(R.id.messagelistview);
        SwipeAdapter mAdapter = new SwipeAdapter(this,data,mListView.getRightViewWidth());
        mAdapter.setOnRightItemClickListener(new SwipeAdapter.onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, int position) {

                Toast.makeText(Message_list.this, "删除第  " + (position+1)+" 对话记录",
                        Toast.LENGTH_SHORT).show();
                data.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Message_list.this, "item onclick " + position, Toast.LENGTH_SHORT)
                        .show();
                WXMessage mm = data.get(position);
                message_title = mm.getTitle();
                startActivity(new Intent(Message_list.this,Chatting.class));

            }
        });
        mListView.setAdapter(mAdapter);
    }
}
