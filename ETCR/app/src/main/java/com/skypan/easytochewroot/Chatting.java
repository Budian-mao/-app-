package com.skypan.easytochewroot;

import android.app.Activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.skypan.easytochewroot.Msg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Chatting extends Activity {
    private String loginid = login.post_userid;
    private String chatuserid = Item.item_userid;
    private String title = Message_list.message_title;
   // private String title = "myc";
    private ListView msgListView;//定义ListView
    private EditText inputText;//定义EditText
    private Button send;//定义Button
    private MsgAdapter adapter;//定义MsgAdapter
    private List<Msg> msgList = new ArrayList<Msg>();//实例化一个泛型为Msg的List
    private Button but1_m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        DatabaseHelper dbhelper1 = new DatabaseHelper(this);
        SQLiteDatabase db=dbhelper1.getReadableDatabase();
        but1_m1=(Button)findViewById(R.id.but1_m1);
        but1_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chatting.this,Message_list.class));
            }
        });
        TextView Title = (TextView)findViewById(R.id.tt);
        //if(chatuserid==null&&title!=null)
        Title.setText(title);
        //else
        //Title.setText(chatuserid);
        //初始化数据
        initMsgs();

        //调用MsgAdapter的构造方法实例化adapter，
        adapter = new MsgAdapter(Chatting.this, R.layout.msg_item, msgList);
        //分别实例化EditText、Button、ListView
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        //为ListView加适配器；
        msgListView.setAdapter(adapter);
        //设置send的点击事件，响应用户操作
        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //定义内容获取用户在EditText中输入的字符串
                String content = inputText.getText().toString();

                //如果字符串不为空,把数据添加到 msgList中
                if(!"".equals(content))
                {
                    Msg msg = new Msg(content , Msg.TYPE_SEND);
                    msgList.add(msg);

                    //动态更新ListView
                    adapter.notifyDataSetChanged();

                    //将列表移动到刚发的消息处，即msgList的最大位置
                    msgListView.setSelection(msgList.size());

                    //把输入框置空
                    inputText.setText("");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    //存到数据库里
                    new Thread(()->{
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody formBody = new FormBody.Builder()

                                .add("send_id",loginid)
                                .add("receive_id",title)
                                .add("text",content)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://121.43.181.253:8080/message/new")
                                .post(formBody)
                                .build();
                        try (Response response = okHttpClient.newCall(request).execute()) {
                            Looper.prepare();
                            if (Boolean.parseBoolean(response.body().string()))
                            {

                                Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                                Intent intent_=new Intent(Chatting.this,Chatting.class);
                                startActivity(intent_);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        });
    }

    private void initMsgs() {
        // TODO Auto-generated method stub
        //从远程数据库中读取数据
        //比如说读出来的还是一个list
        //那么要做到的是遍历这个list，根据login——id 处于的位置,判断是发送的还是接受的
        String userid = login.post_userid;

        //遍历
        //如果是作为发送人


        Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("id1",loginid)
                    .add("id2",title)
                    .build();
            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/message/chat")
                    .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {

                List<Message> data1 = JSONArray.parseArray(response.body().string(),Message.class);


                for (int i = 0; i < data1.size(); ++ i)
                {
                    Message temp1=data1.get(i);
                    if(temp1.getSend_id().equals(loginid))
                    {   // 为列表项赋值
                        Msg msg1 = new Msg(temp1.getText(),Msg.TYPE_SEND);//往msgList中添加发送数据

                        msgList.add(msg1);
                    }
                    else
                    {
                        Msg msg1 = new Msg(temp1.getText(),Msg.TYPE_RECEIVED);//往msgList中添加发送数据

                        msgList.add(msg1);
                    }
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

      // Msg msg2 = new Msg("Hello,Who is that?",Msg.TYPE_SEND);//往msgList中添加发送数据

     //   msgList.add(msg2);
        //反之
      //  Msg msg1 = new Msg("Hello guy!",Msg.TYPE_RECEIVED);//往msgList中添加接收数据
      //  msgList.add(msg1);
                 /*Msg msg1 = new Msg("Hello guy!",Msg.TYPE_RECEIVED);//往msgList中添加接收数据
                 msgList.add(msg1);
                 msgList.add(new Msg("Hello!",Msg.TYPE_RECEIVED));
                 Msg msg2 = new Msg("Hello,Who is that?",Msg.TYPE_SEND);//往msgList中添加发送数据
                 msgList.add(msg2);
                Msg msg3 = new Msg("This is Tom,Nice talking to you.",Msg.TYPE_RECEIVED);//往msgList中添加接收数据
                 msgList.add(msg3);*/
    }


}