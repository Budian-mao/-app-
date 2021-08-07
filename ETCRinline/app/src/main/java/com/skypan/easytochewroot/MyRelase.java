package com.skypan.easytochewroot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRelase extends Activity {
    String TABLENAME = "iteminfo";
    private String personid = login.post_userid;
    private Button but1_m1;
    private SimpleAdapter aAdapter = null;
    private ListView mListView = null;
    private List<Map<String, Object>> mListItems = null;
    private Map<String, Object> mMap = null;
    private int[] mImageId = new int[] {R.drawable.pic_01, R.drawable.pic_02, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03,
            R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, };
    /* 文字列表数组 */
    private String[] mTitle = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mKind = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mInfo = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mPrice = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrelase);
        but1_m1=(Button)findViewById(R.id.but1_m1);
        but1_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyRelase.this,Myaty.class));
            }
        });
        mListView = findViewById(R.id.kind_list1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyRelase.this);
                builder.setTitle("警告");
                builder.setMessage("世界上最遥远的距离是没有网");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        startActivity(new Intent(MyRelase.this,Myaty.class));
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyRelase.this,Main_Page.class));
                    }
                });
                //一样要show
                builder.show();
            }
        });
        init();
    }
    private void init(){
        mListView = findViewById(R.id.kind_list1);
        mListItems = new ArrayList<>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.kind_list1);
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.rawQuery("SELECT * FROM iteminfo WHERE userID = ?",new String[]{personid.toString()});
        //Cursor cursor = db.query(TABLENAME,null,null,null,null,null,null,null); // 数据库查询
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



    /*
    private void init() {
        mListView = findViewById(R.id.kind_list1);
        mListItems = new ArrayList<>();
        for (int i = 0; i < mImageId.length; i++) {
            mMap = new HashMap<>();
            mMap.put("image", mImageId[i]);
            mMap.put("title", mTitle[i]);
            mMap.put("kind",mKind[i]);
            mMap.put("info",mInfo[i]);
            mMap.put("price",mPrice[i]);
            mListItems.add(mMap);
        }
        aAdapter = new SimpleAdapter(this, mListItems, R.layout.listview_item_my, new String[]{"title", "image","kind","info","price"}, new int[]{R.id.title, R.id.item_image,R.id.kind,R.id.item_info,R.id.price});
        mListView.setAdapter(aAdapter);
    }*/
}
