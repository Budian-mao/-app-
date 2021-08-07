package com.skypan.easytochewroot;

/*import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Searching extends Activity {
    private ListView myListView;//列表
    private List<String> itemList;//数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);
        initData();
        myListView = (ListView) findViewById(R.id.lv);
        //ArrayAdapter中有三个参数
        //Context context 指定现在的上下文
        //int textViewResourceId 指定一个包含t    extview的布局文件,显示每行的信息
        //List<String> objects 在listview上显示的数据信息
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Searching.this,
                android.R.layout.simple_list_item_1, itemList);//适配器

    }
    public void initData() {
        itemList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            itemList.add("测试数据" + i);
        }
    }
}
*/
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Searching extends Activity  {
    private Map<String, Object> mMap = null;
    private SearchView searchView;
    private ListView listView;
    private ListView mListView;
    //首先应该从数据库中获取数据并把它写进mSource中
    private String[] mSource;
    //private String[] mSource;
    private String[] mStrings = mSource;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        DatabaseHelper database2 = new DatabaseHelper(this);
        SQLiteDatabase db1 = database2.getReadableDatabase();
        Map<String, Object> item;  // 列表项内容用Map存储
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db1.query("iteminfo",null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                mMap = new HashMap<>();
                mMap.put("title",cursor.getString(2));
                cursor.moveToNext();
                data.add(mMap); // 加入到列表中
            }
        }
        cursor.close();
        String[] pp = new String[data.size()];
        for(int i = 0;i<data.size();i++) {
            Map<String,Object> x = data.get(i);
            String xx =(String) x.get("title");
            pp[i] = xx;
        }
        mSource = pp;
        mStrings = mSource;
        //mSource = data.toArray(new String[data.size()]);
        //mStrings = mSource;
        /*
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = database.getWritableDatabase();
        Map<String, Object> item;  // 列表项内容用Map存储
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // 列表
        Cursor cursor = db.query("iteminfo",null,null,null,null,null,null,null); // 数据库查询
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                mMap = new HashMap<>();
                mMap.put("title",cursor.getString(2));
                cursor.moveToNext();
                data.add(mMap); // 加入到列表中
            }
        }
        cursor.close();
        String[] toBeStored = data.toArray(new String[data.size()]);
        mSource = toBeStored;
        mStrings = mSource;*/

        listView=(ListView) findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings));
        //设置ListView启动过滤
        //listView.setTextFilterEnabled(true);


        searchView=(SearchView) findViewById(R.id.sv);
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
                    mStrings=mSource;
                    listView.setAdapter(new ArrayAdapter<String>(Searching.this, android.R.layout.simple_list_item_1,mStrings));
                    listView.clearTextFilter();
                }
                else {
                    filterData(newText);
                    listView.setAdapter(new ArrayAdapter<String>(Searching.this, android.R.layout.simple_list_item_1,mStrings));
                    //listView.setFilterText(newText);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Searching.this,"你搜索的是:"+query,Toast.LENGTH_SHORT).show();
                return false;
            }


        });


        mListView = findViewById(R.id.lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Searching.this,Item.class));
            }
        });
    }
    private void filterData(String filterStr) {
        mStrings=null;
        List<String> list=new ArrayList<String>();
        //Toast.makeText(Searching.this,"你搜索的是:"+filterStr,Toast.LENGTH_SHORT).show();
        for(int i =0;i<mSource.length;i++){
            if(mSource[i].indexOf(filterStr)!=-1){
                list.add(mSource[i]);
            }
        }
        String[] strings = new String[list.size()];
        list.toArray(strings);
        /*for(int j = 0; j < list.size();j++) {
            strings[j]=list.get(j);
        }*/
        mStrings=strings;
        int len = mStrings.length;
        return;
    }

}



















/*
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class Searching extends Activity implements
        SearchView.OnQueryTextListener {
    ListView listView;
    SearchView searchView;
    Object[] names;
    ArrayAdapter<String> adapter;
    ArrayList<String> mAllList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);
        initActionbar();
        names = loadData();
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, names));

        //listView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        //SearchView去掉（修改）搜索框的背景 修改光标
        //setSearchViewBackground(searchView);
    }

    public void initActionbar() {
        // 自定义标题栏
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTitleView = mInflater.inflate(R.layout.custom_action_bar_layout,
                null);
        getActionBar().setCustomView(
                mTitleView,
                new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
        searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
    }

    public Object[] loadData() {
        mAllList.add("aa");
        mAllList.add("ddfa");
        mAllList.add("qw");
        mAllList.add("sd");
        mAllList.add("fd");
        mAllList.add("cf");
        mAllList.add("re");
        return mAllList.toArray();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Object[] obj = searchItem(newText);
        updateLayout(obj);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }


    public Object[] searchItem(String name) {
        ArrayList<String> mSearchList = new ArrayList<String>();
        for (int i = 0; i < mAllList.size(); i++) {
            int index = mAllList.get(i).indexOf(name);
            // 存在匹配的数据
            if (index != -1) {
                mSearchList.add(mAllList.get(i));
            }
        }
        return mSearchList.toArray();
    }

    public void updateLayout(Object[] obj) {
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, obj));
    }

    // android4.0 SearchView去掉（修改）搜索框的背景 修改光标
    /*public void setSearchViewBackground(SearchView searchView) {
        try {
            Class<?> argClass = searchView.getClass();
            // 指定某个私有属性
            Field ownField = argClass.getDeclaredField("mSearchPlate"); // 注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
            // 所以不能用BitmapDrawable
            // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.ic_baseline_search_24));

            // 指定某个私有属性
            Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
            mQueryTextView.setAccessible(true);
            Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass()
                    .getSuperclass().getSuperclass().getSuperclass();

            // mCursorDrawableRes光标图片Id的属性
            // 这个属性是TextView的属性，所以要用mQueryTextView（SearchAutoComplete）的父类（AutoCompleteTextView）的父
            // 类( EditText）的父类(TextView)
            Field mCursorDrawableRes = mTextViewClass
                    .getDeclaredField("mCursorDrawableRes");

            // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(mQueryTextView.get(searchView),
                    R.drawable.ic_baseline_search_24);// 注意第一个参数持有这个属性(mQueryTextView)的对象(mSearchView)
            // 光标必须是一张图片不能是颜色，因为光标有两张图片，一张是第一次获得焦点的时候的闪烁的图片，一张是后边有内容时候的图片，如果用颜色填充的话，就会失去闪烁的那张图片，颜色填充的会缩短文字和光标的距离（某些字母会背光标覆盖一部分）。
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
*/


