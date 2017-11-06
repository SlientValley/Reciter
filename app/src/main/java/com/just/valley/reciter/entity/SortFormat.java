package com.just.valley.reciter.entity;

/**
 *
 *适配分类内容
 * Created by Valley on 2017/10/28.
 */

public class SortFormat {

    private String sortName;
    private String numArticle;

    public SortFormat(String sortName,int numArticle){
        this.sortName = sortName;
        this.numArticle = String.valueOf(numArticle);
    }
    public SortFormat(String sortName){
        this.sortName = sortName;
        numArticle = null;
    }

    public String getSortName() {
        return sortName;
    }

    public String getNumArticle() {
        return numArticle;
    }
}
