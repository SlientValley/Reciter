package com.just.valley.reciter.act;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.just.valley.reciter.R;
import com.just.valley.reciter.adapter.ContentsAdapter;
import com.just.valley.reciter.entity.ContentFormat;

import java.util.ArrayList;

public class SortActivity extends AppCompatActivity {

    public final static String SORT_NAME = "name";
    public final static String CONTENTS = "contents";

    private Intent recvData;
    private ContentsAdapter contentsAdapter;
    private RecyclerView rememberedContentView;

    private ArrayList<ContentFormat> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        recvData = getIntent();

        rememberedContentView = (RecyclerView) findViewById(R.id.content_remembered);
        setContents();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(recvData.getStringExtra(SORT_NAME));
    }

    private void setContents(){
        //这里设置获取到的需要背诵的文本，并加载到UI控件中。

        contentList = recvData.getParcelableArrayListExtra(CONTENTS);
        GridLayoutManager layoutManage = new GridLayoutManager(this,1);
        rememberedContentView.setLayoutManager(layoutManage);
        contentsAdapter = new ContentsAdapter(contentList);
        rememberedContentView.setAdapter(contentsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_sort,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:

                break;
            default:
                finish();
        }
        return true;
    }
}
