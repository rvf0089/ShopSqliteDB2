package com.example.shrad.shopsqlitedb;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/18.
 */

public class Information implements Serializable {
    private String name;
    private static String addresss;
    private String phonenumber;

    public Information(String name, String addresss, String phonenumber){
        this.name=name;
        this.addresss=addresss;
        this.phonenumber=phonenumber;
    }

    public Information(){
        this.name="StarBucks";
        this.addresss= "Westgate Shopping Centre, 2-20 Fernhill Drive, Auckland 0614";
        this.phonenumber="09-832 8541";
    }

    public String getName() {
        return name;
    }

    public  static String getAddresss() {
        return "2-20 Fernhill Drive, Auckland 0614";
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
