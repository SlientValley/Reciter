package com.just.valley.reciter.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.just.valley.reciter.R;
import com.just.valley.reciter.adapter.ContentsAdapter;
import com.just.valley.reciter.adapter.SortAdapter;
import com.just.valley.reciter.banner.GlideImageLoader;
import com.just.valley.reciter.broadcast.MyBroadcastReceiver;
import com.just.valley.reciter.custom.CircleImageView;
import com.just.valley.reciter.entity.ContentFormat;
import com.just.valley.reciter.entity.SortFormat;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private View mActionBar;
    private Button searchBTN;
    private ImageView cameraBTN;
    private View searchComponent;
    private ImageView firstPage;
    private ImageView sortPage;
    private ImageView infoPage;
    private RecyclerView rememberedContentView;
    private RecyclerView sortedContentView;
    private ContentsAdapter contentsAdapter;
    private SortAdapter sortAdapter;
    private View firstPageContent;
    private View sortPageContent;
    private View infoPageContent;
    private View appBarLayout;

    private LocalBroadcastManager localBroadcastManager;
    private MyBroadcastReceiver localReceiver;

    String e = "My father was a self-taught mandolin player. He was one of the best string instrument players in our town. He could not read music, " +
            "but if he heard a tune a few times, he could play it. When he was younger, he was a member of a small country music band. " +
            "They would play at local dances and on a few occasions would play for the local radio station." +
            " He often told us how he had auditioned and earned a position in a band that featured Patsy Cline as their lead singer. " +
            "He told the family that after he was hired he never went back. Dad was a very religious man. He stated that there " +
            "was a lot of drinking and cursing the day of his audition and he did not want to be around that type of environment.";
    String c = "我父亲是个自学成才的曼陀林琴手，他是我们镇最优秀的弦乐演奏者之一。他看不懂乐谱，但是如果听几次曲子，他就能演奏出来。" +
            "当他年轻一点的时候，他是一个小乡村乐队的成员。他们在当地舞厅演奏，有几次还为当地广播电台演奏。他经常告诉我们，自己如何试演，" +
            "如何在佩茜?克莱恩作为主唱的乐队里占一席之位。他告诉家人，一旦被聘用就永不回头。父亲是一个很严谨的人，他讲述了他试演的那天，" +
            "很多人在喝酒，咒骂，他不想呆在那种环境里。";

    private ContentFormat[] textContent = {
            new ContentFormat("My Father",e,"1234",12),
            new ContentFormat("父爱无边",c,"4543654",100)
    };
    private SortFormat[] sortContents = {
            new SortFormat("未闻",10),
            new SortFormat("未见",34)
    };
    public List<SortFormat> sortList = new ArrayList<>();
    private List<ContentFormat> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFindViewById();
        setContents();
        loadSort();
        setBanner();
        registerLocalBroadcastListener();

    }

    private void registerLocalBroadcastListener(){
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.just.valley.ADD_NEW_SORT_ITEM");
        localReceiver = new MyBroadcastReceiver();
        localReceiver.setListener(new MyBroadcastReceiver.ActionListener() {
            @Override
            public void receive(Context context, Intent intent) {
                //在这里打开对话框
                openDialog();
            }
        });
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    private void openDialog(){
        new MaterialDialog.Builder(MainActivity.this)
                .title("新建分类")
                .inputRange(1,8)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("输入您的分类名","",new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        SortFormat sortFormat = new SortFormat(input.toString(),0);
                        sortList.add(sortList.size()-1,sortFormat);
                        sortAdapter = new SortAdapter(sortList);
                        sortedContentView.setAdapter(sortAdapter);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    private void mFindViewById(){
        mActionBar = findViewById(R.id.m_actionbar);
        searchBTN = (Button) findViewById(R.id.actionbar_search);
        cameraBTN = (ImageView) findViewById(R.id.actionbar_camera);
        searchComponent = findViewById(R.id.func_search);
        firstPage = (ImageView) findViewById(R.id.first_page);
        sortPage = (ImageView) findViewById(R.id.sort_page);
        infoPage = (ImageView) findViewById(R.id.info_page);
        rememberedContentView = (RecyclerView) findViewById(R.id.content_remembered);
        sortedContentView = (RecyclerView) findViewById(R.id.sort_catalogue);

        firstPageContent = findViewById(R.id.layout_content_page);
        sortPageContent = findViewById(R.id.layout_sort_page);
        infoPageContent = findViewById(R.id.layout_info_page);

        appBarLayout = findViewById(R.id.app_bar_layout);

        firstPage.setOnClickListener(this);
        sortPage.setOnClickListener(this);
        infoPage.setOnClickListener(this);
        searchBTN.setOnClickListener(this);
        cameraBTN.setOnClickListener(this);
    }

    private void setBanner(){
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.banner0);
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner2);
        banner.setImages(images);
        //设置banner动画效果
        //banner.setBannerAnimation(Transformer.DepthPage);

        banner.setDelayTime(3000);
        banner.start();
    }

    private void setContents(){
        //这里设置获取到的需要背诵的文本，并加载到UI控件中。
        //textContent =
        contentList.clear();

        for (int i=0;i<20;i++){
            Random random = new Random();
            int index = random.nextInt(textContent.length);
            contentList.add(textContent[index]);
        }
        GridLayoutManager layoutManage = new GridLayoutManager(this,1);
        rememberedContentView.setLayoutManager(layoutManage);
        contentsAdapter = new ContentsAdapter(contentList);
        contentsAdapter.getItemViewType(0);
        rememberedContentView.setAdapter(contentsAdapter);
    }

    private void loadSort(){
        sortList.clear();
        for (int i = 0; i < sortContents.length; i++) {
            sortList.add(sortContents[i]);
        }
        SortFormat newBuild = new SortFormat("新建分类");
        sortList.add(newBuild);

        GridLayoutManager layoutManage = new GridLayoutManager(this,2);
        sortedContentView.setLayoutManager(layoutManage);
        sortAdapter = new SortAdapter(sortList);
        sortedContentView.setAdapter(sortAdapter);

    }

    private void loginAndLoad(){

        //加载头像
        CircleImageView header = (CircleImageView) findViewById(R.id.user_header);
        Glide.with(getApplicationContext()).load(R.drawable.header).into(header);

    }

    @Override
    public void onClick(View view) {
        int item = view.getId();
        switch (item){
            case R.id.first_page: {
                appBarLayout.setVisibility(View.VISIBLE);
                firstPage.setImageResource(R.drawable.home_pressed);
                sortPage.setImageResource(R.drawable.sort);
                infoPage.setImageResource(R.drawable.menu_home);
                firstPageContent.setVisibility(View.VISIBLE);
                sortPageContent.setVisibility(View.GONE);
                infoPageContent.setVisibility(View.GONE);
                searchComponent.setVisibility(View.VISIBLE);

                break;
            }
            case R.id.sort_page: {
                appBarLayout.setVisibility(View.GONE);
                firstPage.setImageResource(R.drawable.home);
                sortPage.setImageResource(R.drawable.sort_pressed);
                infoPage.setImageResource(R.drawable.menu_home);
                firstPageContent.setVisibility(View.GONE);
                sortPageContent.setVisibility(View.VISIBLE);
                infoPageContent.setVisibility(View.GONE);
                searchComponent.setVisibility(View.VISIBLE);

                break;
            }
            case R.id.info_page: {
                appBarLayout.setVisibility(View.GONE);
                firstPage.setImageResource(R.drawable.home);
                sortPage.setImageResource(R.drawable.sort);
                infoPage.setImageResource(R.drawable.menu_home_pressed);
                firstPageContent.setVisibility(View.GONE);
                sortPageContent.setVisibility(View.GONE);
                infoPageContent.setVisibility(View.VISIBLE);
                searchComponent.setVisibility(View.GONE);
                loginAndLoad();

                break;
            }
            case R.id.actionbar_search:{
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.actionbar_camera:{
                Toast.makeText(getApplicationContext(),"功能待实现",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
