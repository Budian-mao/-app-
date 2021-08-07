package com.skypan.easytochewroot;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main_Page extends AppCompatActivity {
    protected static Integer item_position = 0;
    protected static String item_title = " ";
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


    /* 图片ID数组 */
    private int[] mImageId = new int[] {R.drawable.pic_01, R.drawable.pic_02, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03,
            R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, };
    /* 文字列表数组 */
    private String[] mTitle = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mKind = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mInfo = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };
    private String[] mPrice = new String[] {"数字 0", "数字 1", "数字 2", "数字 3", "数字 4", "数字 5", "数字 6", "数字 7", "数字 8", "数字 9", };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        mContext = Main_Page.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);
        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.pic_01, "推荐"));
        mData.add(new Icon(R.drawable.pic_02, "二手书"));
        mData.add(new Icon(R.drawable.pic_03, "自行车"));
        mData.add(new Icon(R.drawable.pic_04, "桌游"));
        mData.add(new Icon(R.drawable.pic_05, "化妆品"));
        mData.add(new Icon(R.drawable.pic_06, "文具用品"));
        mData.add(new Icon(R.drawable.pic_07, "快递代取"));
        mData.add(new Icon(R.drawable.pic_08, "学习辅导"));
        mData.add(new Icon(R.drawable.pic_09, "电子产品"));
        mData.add(new Icon(R.drawable.pic_10, "软件权限"));

        mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };

        grid_photo.setAdapter(mAdapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(mContext, "你点击了" + position + "项", Toast.LENGTH_SHORT).show();*/
                if(position==0){startActivity(new Intent(Main_Page.this,Page1.class));}
                if(position==1){startActivity(new Intent(Main_Page.this,Page2.class));}
                if(position==2){startActivity(new Intent(Main_Page.this,Page3.class));}
                if(position==3){startActivity(new Intent(Main_Page.this,Page4.class));}
                if(position==4){startActivity(new Intent(Main_Page.this,Page5.class));}
                if(position==5){startActivity(new Intent(Main_Page.this,Page6.class));}
                if(position==6){startActivity(new Intent(Main_Page.this,Page7.class));}
                if(position==7){startActivity(new Intent(Main_Page.this,Page8.class));}
                if(position==8){startActivity(new Intent(Main_Page.this,Page9.class));}
                if(position==9){startActivity(new Intent(Main_Page.this,Page10.class));}

            }
        });

        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了" + position + "项", Toast.LENGTH_SHORT).show();
                Object listItem = mListView.getItemAtPosition(position);
                Map entry = (Map)listItem;
                Object aa = entry.get("title");
                item_title = aa.toString();
                item_position = position;
                startActivity(new Intent(Main_Page.this,Item.class));
            }
        });

        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this,Myaty.class));
            }
        });
        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this,Main_Page.class));
            }
        });
        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Page.this,Message_list.class));
            }
        });
        init();


        /*listView=(ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mTitle));
        //设置ListView启动过滤
        listView.setTextFilterEnabled(true);

        searchView=(SearchView) findViewById(R.id.search);
        //设置默认是否缩小为图标
        searchView.setIconifiedByDefault(false);
        //设置是否显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置sv内的默认显示的提示文本
        searchView.setQueryHint("查找");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }
                else {
                    listView.setFilterText(newText);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Main_Page.this,"您的选择是:"+query,Toast.LENGTH_SHORT).show();
                return false;
            }


        });*/
    }

    private void init(){
        mListView = findViewById(R.id.listView);
        mListItems = new ArrayList<>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.listView);
        Map<String, Object> item;  // 列表项内容用Map存储


        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.query("iteminfo",null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                mMap = new HashMap<>();
                mMap.put("id",cursor.getInt(0));
                mMap.put("userid",cursor.getString(1));
                mMap.put("title",cursor.getString(2));
                mMap.put("kind",cursor.getString(3));
                mMap.put("info",cursor.getString(4));
                mMap.put("price",cursor.getString(5));
                //在这里直接放bitmap
                mMap.put("image", mImageId[1]);
                cursor.moveToNext();
                data.add(mMap); // 加入到列表中
            }
        }
        cursor.close();
        aAdapter = new SimpleAdapter(this, data, R.layout.listview_item_my, new String[]{"title", "image","kind","info","price"}, new int[]{R.id.title, R.id.item_image,R.id.kind,R.id.item_info,R.id.price});
        mListView.setAdapter(aAdapter);

    }


    /*private void init() {
        mListView = findViewById(R.id.listView);

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

        aAdapter = new SimpleAdapter(this, mListItems, R.layout.listview_item, new String[]{"title", "image","price"}, new int[]{R.id.title, R.id.item_image,R.id.price});
        mListView.setAdapter(aAdapter);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuLogin:
                startActivity(new Intent(Main_Page.this,Searching.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}



