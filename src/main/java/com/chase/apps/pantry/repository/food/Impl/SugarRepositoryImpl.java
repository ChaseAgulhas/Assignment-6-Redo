package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Sugar;
import com.chase.apps.pantry.repository.food.SugarRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class SugarRepositoryImpl extends SQLiteOpenHelper implements SugarRepository {
    public static final String TABLE_SUGAR = "sugar";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_SUGAR + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public SugarRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public SugarRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SugarRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public void open()
    {
        database = this.getWritableDatabase();
        onCreate(database);
    }

    public void close()
    {
        this.close();
    }

    @Override
    public Sugar findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_SUGAR,
                new String[]{
                        COLUMN_BARCODE,
                        COLUMN_MANUFACTURER,
                        COLUMN_BrandName,
                        COLUMN_PRICE,
                        COLUMN_TYPE
                },
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(barcode)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst())
        {
            final Sugar sugar = new Sugar.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return sugar;
        }
        else {
            return null;
        }
    }

    @Override
    public Sugar save(Sugar sugar) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, sugar.getBarcode());
        values.put(COLUMN_MANUFACTURER, sugar.getManufacturer());
        values.put(COLUMN_BrandName, sugar.getBrandName());
        values.put(COLUMN_PRICE, sugar.getPrice());
        values.put(COLUMN_TYPE, sugar.getType());

        Long barcode = database.insertOrThrow(TABLE_SUGAR, null, values);

        final Sugar insertedEntity = new Sugar.Builder()
                .copy(sugar)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Sugar update(Sugar sugar) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, sugar.getBarcode());
        values.put(COLUMN_MANUFACTURER, sugar.getManufacturer());
        values.put(COLUMN_BrandName, sugar.getBrandName());
        values.put(COLUMN_PRICE, sugar.getPrice());
        values.put(COLUMN_TYPE, sugar.getType());
        database.update(
                TABLE_SUGAR,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(sugar.getBarcode())}
        );

        return sugar;
    }

    @Override
    public Sugar delete(Sugar sugar){
        open();
        database.delete(
                TABLE_SUGAR,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(sugar.getBarcode())}
        );

        return sugar;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_SUGAR, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Sugar> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_SUGAR;
        Set<Sugar> sugarServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Sugar sugar = new Sugar.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                sugarServices.add(sugar);

            }while (cursor.moveToNext());
        }

        return sugarServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGAR);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGAR);
        onCreate(db);
    }
}


