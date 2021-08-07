package com.skypan.easytochewroot;

import java.util.List;

import com.skypan.easytochewroot.Msg;

import android.content.Context;
  import android.view.LayoutInflater;
  import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ArrayAdapter;
 import android.widget.LinearLayout;
 import android.widget.TextView;

 public class MsgAdapter extends ArrayAdapter<Msg> {

             //设置私有变量
             private int resourceId;
             //建立三个参数的构造方法
             public MsgAdapter(Context context, int textViewResourceId , List<Msg> objects) {

                 //实现了ArrayAdapter类的构造方法
                 super(context, textViewResourceId,objects);
                 resourceId = textViewResourceId;
             }

             //position就是你选择的 item的第几条从0开始
             //convertView就是item上的布局layout或者组件
             //ViewGroup parent 就是设置adapter的那个组件里面封装一个viewGroup用来盛放item
             @Override
     public View getView(int position, View convertView, ViewGroup parent) {
                 // TODO Auto-generated method stub
                 Msg msg = getItem(position);//获取当前项的Msg实例；
                 View view ;//定义一个View
                 ViewHolder viewHolder ;
         //        定义一个ViewHolder，ViewHolder是一个内部类，
         //        就是一个持有者的类，他里面一般没有方法，只有属性，
         //        作用就是一个临时的储存器，把你getView方法中每次返回的View存起来，可以下次再用。
         //        这样做的好处就是不必每次都到布局文件中去拿到你的View，提高了效率

                 //判断convertView对象是否为空，如果为空就重新加载；
                 if(convertView == null)
                     {
                         //使用Layoutinflater为这个子项加载我们传入的布局(resourceId)，
                         view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                         //实例化内部类viewHolder
                         viewHolder = new ViewHolder();
                         //将控件的实例都存在ViewHolder中
                         viewHolder.leftLayout =(LinearLayout) view.findViewById(R.id.left_layout);
                         viewHolder.rightLayout =(LinearLayout) view.findViewById(R.id.right_layout);
                         viewHolder.leftMag = (TextView) view.findViewById(R.id.left_msg);
                         viewHolder.rightMag = (TextView) view.findViewById(R.id.right_msg);
                         //把viewHolder通过View的setTag方法存放起来；
                         view.setTag(viewHolder);
                     }
                 else//如果不为空，则重新调用convertView；不必重新加载
                 {
                         view = convertView;
                         viewHolder = (ViewHolder) view.getTag();//取出viewHolder，也就取出了已经加载过得数据
                     }

                 if(msg.getType() == msg.TYPE_RECEIVED)//如果是接收的消息则左侧显示布局和文字，右侧隐藏
                     {
                         viewHolder.leftLayout.setVisibility(View.VISIBLE);
                         viewHolder.rightLayout.setVisibility(View.GONE);
                         viewHolder.leftMag.setText(msg.getContent());
                     }
                 else if(msg.getType() == msg.TYPE_SEND)//如果是发送的消息则右侧显示布局和文字，左侧隐藏
                     {
                         viewHolder.rightLayout.setVisibility(View.VISIBLE);
                         viewHolder.rightMag.setText(msg.getContent());
                         viewHolder.leftLayout.setVisibility(View.GONE);
                     }
                 //返回一个View
                 return view;
             }
     class ViewHolder//临时存储器，把getView方法中每次返回的View存起来，可以下次再用
     {
                LinearLayout leftLayout ;
                 LinearLayout rightLayout ;
                 TextView leftMag;
                 TextView rightMag;

             }
 }
