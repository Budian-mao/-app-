package com.skypan.easytochewroot;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;
import android.os.Handler;
/*import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2};
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
        this.initDots();

        System.out.println("onCreate");
    }

    public void initView() {
        /*
        * Inflater英文意思是膨胀，在Android中应该是扩展的意思吧。
          LayoutInflater的作用类似于 findViewById(),
          不同点是LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化！
          而 findViewById()是找具体某一个xml下的具体 widget控件(如:Button,TextView等)。
        *
        * */
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one, null));  // 加载视图1
        views.add(inflater.inflate(R.layout.two, null));  // 加载视图2

        vpAdapter = new ViewPagerAdapter(views, this);   // 创建我们的 adapter
        vp = (ViewPager) findViewById(R.id.viewpage);
        vp.setAdapter(vpAdapter);                        // viewpage绑定 adapter
        // ViewPager 监听自己


        // 进入按钮
        this.bt = (Button) views.get(1).findViewById(R.id.btn_start);
        this.bt.setOnClickListener(new View.OnClickListener() {  // 监听
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main_Page.class);  // 进去MainActivity
                startActivity(i);
                finish();  //销毁当前的Activity
            }
        });
    }


    private void initDots() {                           // 初始化 我们的导航点
        this.dots = new ImageView[this.views.size()];
        for (int i=0; i<this.views.size(); i++) {
            dots[i] = (ImageView) this.findViewById(this.ids[i]);
        }
    }


    // 监听 页面滑动的方法
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i=0; i<this.ids.length; i++) {
            if (position == i) {
                this.dots[i].setImageResource(R.drawable.login_selectd);  // 变成选中的
            } else {
                this.dots[i].setImageResource(R.drawable.login_point);    //变成不选中的
            }
        }
    }

    //选中
    public void onPageSelected(int position) {

    }

    // 滑动状态改变
    public void onPageScrollStateChanged(int state) {

    }
}










/*class LaunchSimpleAdapter extends PagerAdapter{
    private Context mContext;
    private ArrayList<View>mViewList = new ArrayList<View>();

    public LaunchSimpleAdapter(Context context,int[]imageArray){
        mContext=context;
        for(int i = 0;i<imageArray.length;i++) {
           View view = LayoutInflater.from(context).inflate(R.layout.activity_main,null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);

            iv_launch.setImageResource(imageArray[i]);
            for(int j = 0;j<imageArray.length;j++){
                RadioButton radio = new RadioButton(mContext);
                radio.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                radio.setButtonDrawable(R.drawable.launch_guide);
                radio.setPadding(10,10,10,10);
                rg_indicate.addView(radio);
            }
            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);
            if(i == imageArray.length -1){
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"欢迎您开启美好生活",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            mViewList.add(view);

        }
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0==arg1;
    }
    public void destroyItem(ViewGroup container, int position){
        container.removeView(mViewList.get(position));
    }
    public Object instantiateItem(ViewGroup container,int position){
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}*/