package com.yangkai.androiduikj.toolBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yangkai.androiduikj.MainActivity;
import com.yangkai.androiduikj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToolBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
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

    }

    @OnClick(R.id.tb_btn1)
    void tbBtn1(View view){
        startActivity(new Intent(ToolBarActivity.this, ToolBar1Activity.class));
    }

    @OnClick(R.id.tb_btn2)
    void tbBtn2(View view){
        startActivity(new Intent(ToolBarActivity.this, ToolBar2Activity.class));
    }

    @OnClick(R.id.tb_btn3)
    void tbBtn3(View view){

    }
}
