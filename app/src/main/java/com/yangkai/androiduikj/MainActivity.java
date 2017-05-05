package com.yangkai.androiduikj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yangkai.androiduikj.base.BaseViewHolder;
import com.yangkai.androiduikj.base.RecycleViewAdapter;
import com.yangkai.androiduikj.notification.NotificationActivity;
import com.yangkai.androiduikj.toolBar.ToolBarActivity;
import com.yangkai.androiduikj.utils.ToastUtil;
import com.yangkai.androiduikj.widget.FullyLinearLayoutManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    private List<String> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initRV();
    }

    private void initRV(){
        HorizontalDividerItemDecoration dividerItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.line)
                .margin(R.dimen.divider_left_margin,R.dimen.divider_right_margin)
                .sizeResId(R.dimen.list_divider)
                .build();
        getList();
        FullyLinearLayoutManager mLayoutManager = new FullyLinearLayoutManager(this);
        mainRv.setLayoutManager(mLayoutManager);
        mainRv.addItemDecoration(dividerItemDecoration);
        RecycleViewAdapter mainAdapter = new RecycleViewAdapter<String>(R.layout.item_main_layout, mLists) {
            @Override
            protected void convert(BaseViewHolder holder, final String o) {
                holder.setText(R.id.item_main_btn, o);

                holder.setOnClickListener(R.id.item_main_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("ToolBar相关".equals(o)){
                            ToastUtil.makeText("ToolBar相关").show();
                            startActivity(new Intent(MainActivity.this, ToolBarActivity.class));
                        } else  if ("Notification相关".equals(o)){
                            ToastUtil.makeText("Notification相关").show();
                            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                        }

                    }
                });

            }
        };
        mainAdapter.setOnRecyclerViewItemChildClickListener(new RecycleViewAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(RecycleViewAdapter adapter, View view, int position) {
            }
        });

        mainRv.setAdapter(mainAdapter);


    }

    private void getList(){
        mLists.add("ToolBar相关");
        mLists.add("Notification相关");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
