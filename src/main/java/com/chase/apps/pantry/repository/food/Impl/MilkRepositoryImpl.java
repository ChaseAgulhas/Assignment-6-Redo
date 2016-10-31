package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Milk;
import com.chase.apps.pantry.repository.food.MilkRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class MilkRepositoryImpl extends SQLiteOpenHelper implements MilkRepository {
    public static final String TABLE_MILK = "milk";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_MILK + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public MilkRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public MilkRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MilkRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Milk findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_MILK,
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
            final Milk milk = new Milk.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return milk;
        }
        else {
            return null;
        }
    }

    @Override
    public Milk save(Milk milk) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, milk.getBarcode());
        values.put(COLUMN_MANUFACTURER, milk.getManufacturer());
        values.put(COLUMN_BrandName, milk.getBrandName());
        values.put(COLUMN_PRICE, milk.getPrice());
        values.put(COLUMN_TYPE, milk.getType());

        Long barcode = database.insertOrThrow(TABLE_MILK, null, values);

        final Milk insertedEntity = new Milk.Builder()
                .copy(milk)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Milk update(Milk milk) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, milk.getBarcode());
        values.put(COLUMN_MANUFACTURER, milk.getManufacturer());
        values.put(COLUMN_BrandName, milk.getBrandName());
        values.put(COLUMN_PRICE, milk.getPrice());
        values.put(COLUMN_TYPE, milk.getType());
        database.update(
                TABLE_MILK,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(milk.getBarcode())}
        );

        return milk;
    }

    @Override
    public Milk delete(Milk milk){
        open();
        database.delete(
                TABLE_MILK,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(milk.getBarcode())}
        );

        return milk;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_MILK, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Milk> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_MILK;
        Set<Milk> milkServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Milk milk = new Milk.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                milkServices.add(milk);

            }while (cursor.moveToNext());
        }

        return milkServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_MILK);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MILK);
        onCreate(db);
    }
}


