package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Water;
import com.chase.apps.pantry.repository.food.WaterRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class WaterRepositoryImpl extends SQLiteOpenHelper implements WaterRepository {
    public static final String TABLE_WATER = "water";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_WATER + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public WaterRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public WaterRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public WaterRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Water findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_WATER,
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
            final Water water = new Water.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return water;
        }
        else {
            return null;
        }
    }

    @Override
    public Water save(Water water) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, water.getBarcode());
        values.put(COLUMN_MANUFACTURER, water.getManufacturer());
        values.put(COLUMN_BrandName, water.getBrandName());
        values.put(COLUMN_PRICE, water.getPrice());
        values.put(COLUMN_TYPE, water.getType());

        Long barcode = database.insertOrThrow(TABLE_WATER, null, values);

        final Water insertedEntity = new Water.Builder()
                .copy(water)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Water update(Water water) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, water.getBarcode());
        values.put(COLUMN_MANUFACTURER, water.getManufacturer());
        values.put(COLUMN_BrandName, water.getBrandName());
        values.put(COLUMN_PRICE, water.getPrice());
        values.put(COLUMN_TYPE, water.getType());
        database.update(
                TABLE_WATER,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(water.getBarcode())}
        );

        return water;
    }

    @Override
    public Water delete(Water water){
        open();
        database.delete(
                TABLE_WATER,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(water.getBarcode())}
        );

        return water;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_WATER, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Water> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_WATER;
        Set<Water> waterServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Water water = new Water.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                waterServices.add(water);

            }while (cursor.moveToNext());
        }

        return waterServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER);
        onCreate(db);
    }
}


