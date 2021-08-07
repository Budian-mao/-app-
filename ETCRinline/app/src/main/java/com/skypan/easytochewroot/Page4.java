package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page4 extends Activity {
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
        setContentView(R.layout.page4);

        mListView = findViewById(R.id.kind_list1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了" + position + "项", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Page4.this,Item.class));
            }
        });
        findViewById(R.id.but1_m1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Page4.this,Main_Page.class));
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
        Cursor cursor =  db.rawQuery("SELECT * FROM iteminfo WHERE kind = ?",
                new String[]{"桌游"});
        //Cursor cursor = db.query("iteminfo",null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                mMap = new HashMap<>();
                mMap.put("id",cursor.getInt(0));
                mMap.put("userid",cursor.getString(1));
                mMap.put("title",cursor.getString(2));
                mMap.put("kind",cursor.getString(3));
                mMap.put("info",cursor.getString(4));
                mMap.put("price",cursor.getString(5));
                mMap.put("image", mImageId[1]);
                cursor.moveToNext();
                data.add(mMap); // 加入到列表中
            }
        }
        cursor.close();
        aAdapter = new SimpleAdapter(this, data, R.layout.listview_item_my, new String[]{"title", "image","kind","info","price"}, new int[]{R.id.title, R.id.item_image,R.id.kind,R.id.item_info,R.id.price});
        mListView.setAdapter(aAdapter);
    }
}
