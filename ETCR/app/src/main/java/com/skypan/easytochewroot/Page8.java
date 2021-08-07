package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Page8 extends Activity {
    private String personid = login.post_userid;
    private Context mContext;
    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;
    private ListView mListView = null;
    private List<Map<String, Object>> mListItems = null;
    private Map<String, Object> mMap = null;

    private SimpleAdapter aAdapter = null;

    private SearchView searchView;
    private ListView listView;
    private Object[] names;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mAllList = new ArrayList<String>();
    private int[] mImageId = new int[] {R.drawable.pic_01, R.drawable.pic_02, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03,
            R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page8);

        mListView = findViewById(R.id.kind_list1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了" + position + "项", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Page8.this,Item.class));
            }
        });
        findViewById(R.id.but1_m1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page8.this,Main_Page.class));
            }
        });
        init();
    }
    private void init(){
        mListView = findViewById(R.id.kind_list1);
        mListItems = new ArrayList<>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("kind","学习辅导")
                    .build();

            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/merch/kind")
                    .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                List<Goods> data1 = JSONArray.parseArray(response.body().string(),Goods.class);

                for (int i = 0; i < data1.size(); ++ i){
                    mMap = new HashMap<>();
                    Goods temp= data1.get(i);
                    mMap.put("id",temp.getGoodid());
                    mMap.put("userid",temp.getPost_userid());
                    mMap.put("title",temp.getTitle());
                    mMap.put("kind",temp.getKind());
                    mMap.put("info",temp.getInfo());
                    mMap.put("price",temp.getPrice());
                    String picturechar1=temp.getPicturechar();
                    byte[] bitmapArray = Base64.decode(picturechar1, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                    mMap.put("image", bitmap);
                    data.add(mMap); // 加入到列表中
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


        aAdapter = new SimpleAdapter(this, data, R.layout.listview_item_my, new String[]{"title", "image","kind","info","price"}, new int[]{R.id.title, R.id.item_image,R.id.kind,R.id.item_info,R.id.price});
        aAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView image = (ImageView) view;
                    image.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        mListView.setAdapter(aAdapter);
    }
}
