package com.businessappstation.superstarmmjdispensaries.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by pavelhunko@gmail.com on 04/May/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_IMAGE = "image";

    private static final String CREATE_TABLE_PRODUCTS = "create table " + TABLE_PRODUCTS + "(" + COLUMN_NAME + " text primary key not null, " + COLUMN_URL + " text not null, " + COLUMN_IMAGE + " BLOB);";

    private static final String HOLY_GRAIL_LIQUID_NAME = "Holy Grail CBD E-liquid";
    private static final String HOLY_GRAIL_LIQUID_URL = "https://superstarvaporpens.com/product/holy-grail-cbd-e-liquid/";
    //private static final Byte HOLY_GRAIL_LIQUID_IMG;

    private static final String SUPERSTAR_PREMIUM_ELIQUID_NAME = "";
    private static final String SUPERSTAR_PREMIUM_ELIQUID_URL = "https://superstarvaporpens.com/product-category/superstar-premium-e-liquid/";
    //private static final String SUPERSTAR_PREMIUM_ELIQUID_IMG = "";

    private static final String SUPERSTAR_VAPOR_PEN_NAME = "Superstar Vapor Pen";
    private static final String SUPERSTAR_VAPOR_PEN_URL = "https://superstarvaporpens.com/product-category/superstar-vapor-pen/";
    //private static final String SUPERSTAR_VAPOR_PEN_IMG = "";

    private static final String SUPERSTAR_VAPOR_PEN_ACCS_NAME = "Superstar Vapor Pen Accessories";
    private static final String SUPERSTAR_VAPOR_PEN_ACCS_URL = "https://superstarvaporpens.com/product-category/superstar-vapor-pen-accessories/";
    //private static final String SUPERSTAR_VAPOR_PEN_ACCS_IMG = "";

    private static final String SUPERSTAR_CLOTHING_AND_APPAREL_NAME = "Superstar Clothing & Apparel";
    private static final String SUPERSTAR_CLOTHING_AND_APPAREL_URL = "https://superstarvaporpens.com/product-category/superstar-clothing-apperal/";
    //private static final String SUPERSTAR_CLOTHING_AND_APPAREL_IMG = "";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initializeProductsTable(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_PRODUCTS);
        //insert products
        //insertProduct(db, );
        //insertProduct(db, );
        //insertProduct(db, );

    }

    private long insertProduct(SQLiteDatabase db, String name, String desc, String url, byte[] img){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_URL, url);
        values.put(DBHelper.COLUMN_IMAGE, img);
        long id = db.insert(DBHelper.TABLE_PRODUCTS, null, values);
        return id;
    }
}
