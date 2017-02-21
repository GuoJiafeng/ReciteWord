package com.gjf.reciteword;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by BlackBeard丶 on 2016/11/21.
 */
public class Word extends DataSupport {

    private int id;
    private  String english;
    private String  chinese;
    private Date publishdate;


    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    @Override
    public String toString() {
        return  id+","+english+","+english;
    }


}
