package com.skypan.easytochewroot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String dbname="mydb";
    public DatabaseHelper(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //账号userId，密码passWord，姓名name，专业subject，电话phone，QQ号qq
        db.execSQL("create table if not exists users" +
                "(userId varchar(20) primary key," +
                "passWord varchar(20) not null," +
                "name varchar(20)," +
                "subject varchar(20)," +
                "phone varchar(15)," +
                "bill integer,"+
                "qq varchar(15))");
        //信息发送者Msgsenderid, 信息接收者Msgrecipientid，信息内容 comment，信息时间time
        /*db.execSQL("create table if not exists message(" +
                "msgsenderid varchar(100)," +
                "msgrecipientid varchar(100)," +
                "comment varchar(1000)," +
                "time DATETIME)");*/
        //商品编号id，发布者账号userId，标题title，类别kind，内容info，价格price，图片image
        db.execSQL("create table if not exists iteminfo(" +
                "id integer primary key  AUTOINCREMENT," +
                "userId varchar(100)," +
                "title varchar(200)," +
                "kind varchar(100)," +
                "info varchar(1000)," +
                "price varchar(100)," +
                "image blob," +
                "stars integer,"+
                "time DATETIME," +
                "contact varchar(50))");
        //评论者账号userId，评论商品编号itemId，评论内容comment，评论时间time
        db.execSQL("create table if not exists comments(" +
                "userId varchar(100)," +
                "itemId integer," +
                "comment varchar(1000)," +
                "time DATETIME)");
        //信息发送者Msgsenderid, 信息接收者Msgrecipientid，信息内容 comment，信息时间time
        db.execSQL("create table if not exists message(" +
                "msgsenderid varchar(100)," +
                "msgrecipientid varchar(100)," +
                "comment varchar(1000)," +
                "time DATETIME)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

