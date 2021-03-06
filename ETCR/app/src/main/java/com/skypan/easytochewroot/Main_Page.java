package com.skypan.easytochewroot;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;



import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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

import com.alibaba.fastjson.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Main_Page extends AppCompatActivity {
    protected static Integer item_position = 0;
    protected static String item_title = " ";
    protected static String item_price = " ";
    protected static String item_info = " ";
    protected static String item_contact = " ";
    protected static String item_userid = " ";
    protected static Bitmap item_image;
    protected static String item_id = " ";

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
    private List<Goods> data2;

    /* ??????ID?????? */
    private int[] mImageId = new int[] {R.drawable.pic_01, R.drawable.pic_02, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03,
            R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, R.drawable.pic_03, };
    /* ?????????????????? */
    private String[] mTitle = new String[] {"?????? 0", "?????? 1", "?????? 2", "?????? 3", "?????? 4", "?????? 5", "?????? 6", "?????? 7", "?????? 8", "?????? 9", };
    private String[] mKind = new String[] {"?????? 0", "?????? 1", "?????? 2", "?????? 3", "?????? 4", "?????? 5", "?????? 6", "?????? 7", "?????? 8", "?????? 9", };
    private String[] mInfo = new String[] {"?????? 0", "?????? 1", "?????? 2", "?????? 3", "?????? 4", "?????? 5", "?????? 6", "?????? 7", "?????? 8", "?????? 9", };
    private String[] mPrice = new String[] {"?????? 0", "?????? 1", "?????? 2", "?????? 3", "?????? 4", "?????? 5", "?????? 6", "?????? 7", "?????? 8", "?????? 9", };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        mContext = Main_Page.this;
        grid_photo = (GridView) findViewById(R.id.grid_photo);
        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.pic_01, "??????"));
        mData.add(new Icon(R.drawable.pic_02, "?????????"));
        mData.add(new Icon(R.drawable.pic_03, "?????????"));
        mData.add(new Icon(R.drawable.pic_04, "??????"));
        mData.add(new Icon(R.drawable.pic_05, "?????????"));
        mData.add(new Icon(R.drawable.pic_06, "????????????"));
        mData.add(new Icon(R.drawable.pic_07, "????????????"));
        mData.add(new Icon(R.drawable.pic_08, "????????????"));
        mData.add(new Icon(R.drawable.pic_09, "????????????"));
        mData.add(new Icon(R.drawable.pic_10, "????????????"));

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
                /*Toast.makeText(mContext, "????????????" + position + "???", Toast.LENGTH_SHORT).show();*/
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
                Toast.makeText(mContext, "????????????" + position + "???", Toast.LENGTH_SHORT).show();
                Object listItem = mListView.getItemAtPosition(position);
                Goods temp=data2.get(position);
                item_contact=temp.getContact();
                Map entry = (Map)listItem;
                Object aa = entry.get("title");
                Object bb = entry.get("price");
                Object cc = entry.get("info");
                Object dd=entry.get("id");
                Object im = entry.get("image");
                Object ui = entry.get("userid");
                Bitmap kk = (Bitmap)entry.get("image");
                item_image = kk;
                item_title = aa.toString();
                item_price = bb.toString();
                item_info = cc.toString();
                item_userid = ui.toString();
                item_id=dd.toString();
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
        //??????ListView????????????
        listView.setTextFilterEnabled(true);

        searchView=(SearchView) findViewById(R.id.search);
        //?????????????????????????????????
        searchView.setIconifiedByDefault(false);
        //??????????????????????????????
        searchView.setSubmitButtonEnabled(true);
        //??????sv?????????????????????????????????
        searchView.setQueryHint("??????");
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
                Toast.makeText(Main_Page.this,"???????????????:"+query,Toast.LENGTH_SHORT).show();
                return false;
            }


        });*/
    }

    private void init(){
        mListView = findViewById(R.id.listView);
        mListItems = new ArrayList<>();
        //DatabaseHelper database = new DatabaseHelper(this);
        //SQLiteDatabase db = database.getWritableDatabase();
        ListView listView = (ListView)findViewById(R.id.listView);
        Map<String, Object> item;  // ??????????????????Map??????
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(); // ??????
       // Cursor cursor = db.query("iteminfo",null,null,null,null,null,null,null); // ???????????????
       /* if (cursor.moveToFirst()){
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
                data.add(mMap); // ??????????????????
            }
        }
        cursor.close();*/


       Thread t= new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody formBody = new FormBody.Builder().build();
            Request request = new Request.Builder()
                    .url("http://121.43.181.253:8080/merch/each")
                   // .post(formBody)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {

               // String mmp="[{\"title\":\"1\",\"post_userid\":\"22\",\"kind\":\"33\",\"time\":\"44\",\"price\":\"55\",\"contact\":\"66\",\"info\":\"77\",\"picturechar\":\"88\"}]";
               // String test =response.body().string();
                //List<Goods> data1 = JSONArray.parseArray(mmp,Goods.class);
               // List<Goods> data1 = new ArrayList<Goods>();
              List<Goods> data1 = JSONArray.parseArray(response.body().string(),Goods.class);
                data2=data1;

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
                    byte[] bitmapArray =Base64.decode(picturechar1, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                    mMap.put("image", bitmap);
                    data.add(mMap); // ??????????????????
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
        while(t.isAlive() == true){//t.getState() != State.TERMINATED??????????????????????????????
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


        String pause="pause";

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