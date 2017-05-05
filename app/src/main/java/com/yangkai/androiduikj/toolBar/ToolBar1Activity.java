package com.yangkai.androiduikj.toolBar;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yangkai.androiduikj.R;
import com.yangkai.androiduikj.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolBar1Activity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.tb_1)
    Toolbar tb1;
    PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar1);
        ButterKnife.bind(this);

        // App Logo
//        tb1.setLogo(R.drawable.app_icon);
        // 主标题,默认为app_label的名字
        tb1.setTitle("Title");
        tb1.setTitleTextColor(Color.YELLOW);
        // 副标题
        tb1.setSubtitle("Sub title");
        tb1.setSubtitleTextColor(Color.parseColor("#80ff0000"));
        //侧边栏的按钮
        tb1.setNavigationIcon(android.R.drawable.ic_menu_agenda);
        //取代原本的actionbar
        setSupportActionBar(tb1);
        //设置NavigationIcon的点击事件,需要放在setSupportActionBar之后设置才会生效,
        //因为setSupportActionBar里面也会setNavigationOnClickListener
        tb1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.makeText("click NavigationIcon").show();
            }
        });
        //设置toolBar上的MenuItem点击事件
        tb1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        ToastUtil.makeText("click edit").show();
                        break;
                    case R.id.action_share:
                        ToastUtil.makeText("click share").show();
                        break;
                    case R.id.action_overflow:
                        //弹出自定义overflow
                        popUpMyOverflow();
                        return true;
                }
                return true;
            }
        });
        //ToolBar里面还可以包含子控件
        tb1.findViewById(R.id.btn_diy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.makeText("点击自定义按钮").show();
            }
        });
        tb1.findViewById(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.makeText("点击自定义标题").show();
            }
        });
    }
    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tb_1, menu);
        return true;
    }

    /**
     * 弹出自定义的popWindow
     */
    public void popUpMyOverflow() {
        //获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度+toolbar的高度
        int yOffset = frame.top + tb1.getHeight();
        if (null == mPopupWindow) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.action_overflow_popwindow, null);
            //popView即popupWindow的布局，ture设置focusAble.
            mPopupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            mPopupWindow.setOutsideTouchable(true);
            //设置一个动画。
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在右上角。
            mPopupWindow.showAtLocation(tb1, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
            //设置item的点击监听
            popView.findViewById(R.id.ll_item1).setOnClickListener(this);
            popView.findViewById(R.id.ll_item2).setOnClickListener(this);
            popView.findViewById(R.id.ll_item3).setOnClickListener(this);
        } else {
            mPopupWindow.showAtLocation(tb1, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item1:
                ToastUtil.makeText("哈哈").show();
                break;
            case R.id.ll_item2:
                ToastUtil.makeText("呵呵").show();
                break;
            case R.id.ll_item3:
                ToastUtil.makeText("嘻嘻").show();
                break;
        }
        //点击PopWindow的item后,关闭此PopWindow
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
