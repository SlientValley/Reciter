package com.just.valley.reciter.act;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.just.valley.reciter.R;
import com.just.valley.reciter.utils.ChineseOrEnglish;

public class ReciterActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String ARTICLE_TITLE = "title";
    public final static String ARTICLE_TEXT = "text";

    private TextView articleTitle;
    private TextView articleText;
    private TextView text;
    private TextView textModel1;
    private TextView textModel2;
    private Intent recvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciter);

        recvData = getIntent();
        findById();
        convertText2TargetText(recvData.getStringExtra(ARTICLE_TEXT));

        articleTitle.setText(recvData.getStringExtra(ARTICLE_TITLE));
        articleText.setText(recvData.getStringExtra(ARTICLE_TEXT));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("学习");
    }

    private void findById(){
        articleTitle = (TextView) findViewById(R.id.article_title);
        articleText = (TextView) findViewById(R.id.article_text);
        text = (TextView) findViewById(R.id.text);
        textModel1 = (TextView) findViewById(R.id.text_model1);
        textModel2 = (TextView) findViewById(R.id.text_model2);


        text.setOnClickListener(this);
        textModel1.setOnClickListener(this);
        textModel2.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            default:
                finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.text:
                articleText.setText(recvData.getStringExtra(ARTICLE_TEXT));
                break;
            case R.id.text_model1:
                articleText.setText(mText1);
                break;
            case R.id.text_model2:
                articleText.setText(mText2);
                break;
        }
    }

    private String mText1;
    private String mText2;

    private void convertText2TargetText(String text){
        //处理文章并把处理后的model存入mText1与mText2中
        String[] text1 = new String[text.length()];
        String[] text2 = new String[text.length()];

        if(ChineseOrEnglish.isChinese(text)){
            for (int i = 0; i <text.length() ; i++) {
                if (i%2 == 0){
                    text1[i] = "X";
                    text2[i] = String.valueOf(text.charAt(i));
                }else {
                    text2[i] = "X";
                    text1[i] = String.valueOf(text.charAt(i));
                }
            }
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < text1.length ; i++) {
                sb1.append(text1[i]);
                sb2.append(text2[i]);
            }
            mText1 = sb1.toString();
            mText2 = sb2.toString();
        }else {
            text1 = text.split(" ");
            text2 = text.split(" ");
            for (int i = 0; i < text1.length ; i++) {
                if (i%2 == 0){
                    text1[i] = "X";
                }else {
                    text2[i] = "X";
                }
            }
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < text1.length ; i++) {
                sb1.append(text1[i] + " ");
                sb2.append(text2[i] + " ");
            }
            mText1 = sb1.toString();
            mText2 = sb2.toString();
        }

    }
}
