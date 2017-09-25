package com.example.shrad.shopsqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shrad on 17/09/2017.
 */

/**
 * This class establishes connection with Database & creates the table &
 * updates them upon request when the respective button are pressed in the Main & Main2 Activity.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Shopdb.db";
    private static final String TABLE_NAME="ShopOwnerInfoTable";
    private static final String COl_1 ="id";
    private static final String COl_2 ="ownerFirstName";
    private static final String COl_3 ="ownerLastName";
    private static final String COl_4 ="ownerMobileNo";
    private static final String COl_5 ="ownerEmailId";
    private boolean foundData;

    private static final String TABLE_NAME2="ShopDetailsTable";
    private static final String COl2_1 ="shop_id";
    private static final String COl2 ="shopName";
    private static final String COl2_3 ="shopRegNo";
    private static final String COl2_4 ="shopGSTNo";
    private static final String COl2_5 ="shopPhoneno";
    private static final String COl2_6 ="shopWebsiteLink";
    private static final String COl2_7 ="shopStreetAddress";
    private static final String COl2_8 ="shopSuburb";
    private static final String COl2_9 ="shopCity";
    private static final String COl2_10 ="shopCountry";
    private static final String COl2_11="shopPostcode";
    private static final String tableSql ="CREATE TABLE "+ TABLE_NAME +"( id INTEGER PRIMARY KEY AUTOINCREMENT, ownerFirstName TEXT, " +
            "ownerLastName TEXT, ownerMobileNo INTEGER, ownerEmailId TEXT) ";
    private static final  String table2sql ="CREATE TABLE " + TABLE_NAME2 +"("+COl2_1+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            "shopName TEXT, shopRegNo INTEGER, shopGSTNo INTEGER, shopPhoneno INTEGER," +
            "shopWebsiteLink TEXT, shopStreetAddress TEXT, shopSuburb TEXT," +
            "shopCity TEXT,  shopCountry TEXT,shopPostcode INTEGER) ";


    /**
     * calling the super DatabaseHelper constructor with our database name & database version.
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        myDb.execSQL(tableSql);
        myDb.execSQL(table2sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDb, int i, int i1) {
        myDb.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        myDb.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(myDb);
    }

    //Inserts data into ShopOwnersInfoTable
    public boolean insertData(String firstName, String lastName, String mobile_number, String emailID){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,1);
        contentValues.put(COl_2,firstName);
        contentValues.put(COl_3,lastName);
        contentValues.put(COl_4, mobile_number);
        contentValues.put(COl_5,emailID);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //deletes record from shopOwnersInfoTable
    public Integer deleteData(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id = ?",new String[]{id});
    }


    //Receives res object for all data from ShopOwnersInfoTable
    public Cursor getAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME,null);
        if (res.getCount()>0)
            foundData =true;
        else
            foundData=false;
        return  res;
    }

    //Updates data into ShopOwnersInfoTable
    public boolean updataData(String id, String firstName, String lastName, String mobile_number, String emailID){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_1,id);
        contentValues.put(COl_2,firstName);
        contentValues.put(COl_3,lastName);
        contentValues.put(COl_4, mobile_number);
        contentValues.put(COl_5,emailID);
        db.update(TABLE_NAME,contentValues,"id= ?",new String[] {id});
        return true;
    }

    public boolean foundData(){
        return  this.foundData;
    }

    //deletes record from shopDetailsTable
    public Integer deleteShopData(String id){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME2,"shop_id = ?",new String[]{id});
    }

    //Updates data into ShopDetailsTable
    public boolean updataShopData(String id,String shopName, String shopRegNo, String shopGSTNo, String shopPhoneNo,
                                  String shopWebsiteLink, String streetAddress, String suburb, String city,
                                  String country, String postcode){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl2_1,id);
        contentValues.put(COl2,shopName);
        contentValues.put(COl2_3,shopRegNo);
        contentValues.put(COl2_4,shopGSTNo);
        contentValues.put(COl2_5,shopPhoneNo);
        contentValues.put(COl2_6,shopWebsiteLink);
        contentValues.put(COl2_7,streetAddress);
        contentValues.put(COl2_8,suburb);
        contentValues.put(COl2_9,city);
        contentValues.put(COl2_10,country);
        contentValues.put(COl2_11,postcode);

        db.update(TABLE_NAME2,contentValues,"id= ?",new String[] {id});
        return true;
    }

    //Inserts data into ShopDetailsInfoTable
    public boolean insertShopData(String shopName, String shopRegNo, String shopGSTNo, String shopPhoneNo,
                                  String shopWebsiteLink, String streetAddress, String suburb, String city,
                                  String country, String postcode){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,1);
        contentValues.put(COl2,shopName);
        contentValues.put(COl2_3,shopRegNo);
        contentValues.put(COl2_4,shopGSTNo);
        contentValues.put(COl2_5,shopPhoneNo);
        contentValues.put(COl2_6,shopWebsiteLink);
        contentValues.put(COl2_7,streetAddress);
        contentValues.put(COl2_8,suburb);
        contentValues.put(COl2_9,city);
        contentValues.put(COl2_10,country);
        contentValues.put(COl2_11,postcode);

        long result = db.insert(TABLE_NAME2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //This method gets all the data from the shopdetails table

    public Cursor getAllShopData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME2,null);
        if (res.getCount()>0)
            foundData =true;
        else
            foundData=false;
        return  res;
    }





}
