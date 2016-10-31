package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Meat;
import com.chase.apps.pantry.repository.food.MeatRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class MeatRepositoryImpl extends SQLiteOpenHelper implements MeatRepository {
    public static final String TABLE_MEAT = "meat";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_MEAT + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public MeatRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public MeatRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MeatRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Meat findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_MEAT,
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
            final Meat meat = new Meat.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return meat;
        }
        else {
            return null;
        }
    }

    @Override
    public Meat save(Meat meat) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, meat.getBarcode());
        values.put(COLUMN_MANUFACTURER, meat.getManufacturer());
        values.put(COLUMN_BrandName, meat.getBrandName());
        values.put(COLUMN_PRICE, meat.getPrice());
        values.put(COLUMN_TYPE, meat.getType());

        Long barcode = database.insertOrThrow(TABLE_MEAT, null, values);

        final Meat insertedEntity = new Meat.Builder()
                .copy(meat)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Meat update(Meat meat) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, meat.getBarcode());
        values.put(COLUMN_MANUFACTURER, meat.getManufacturer());
        values.put(COLUMN_BrandName, meat.getBrandName());
        values.put(COLUMN_PRICE, meat.getPrice());
        values.put(COLUMN_TYPE, meat.getType());
        database.update(
                TABLE_MEAT,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(meat.getBarcode())}
        );

        return meat;
    }

    @Override
    public Meat delete(Meat meat){
        open();
        database.delete(
                TABLE_MEAT,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(meat.getBarcode())}
        );

        return meat;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_MEAT, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Meat> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_MEAT;
        Set<Meat> meatServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Meat meat = new Meat.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                meatServices.add(meat);

            }while (cursor.moveToNext());
        }

        return meatServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAT);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAT);
        onCreate(db);
    }
}


