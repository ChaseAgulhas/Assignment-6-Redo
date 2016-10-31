package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Bread;
import com.chase.apps.pantry.repository.food.BreadRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class BreadRepositoryImpl extends SQLiteOpenHelper implements BreadRepository {
    public static final String TABLE_BREAD = "bread";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_BREAD + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public BreadRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public BreadRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BreadRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Bread findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_BREAD,
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
            final Bread bread = new Bread.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return bread;
        }
        else {
            return null;
        }
    }

    @Override
    public Bread save(Bread bread) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, bread.getBarcode());
        values.put(COLUMN_MANUFACTURER, bread.getManufacturer());
        values.put(COLUMN_BrandName, bread.getBrandName());
        values.put(COLUMN_PRICE, bread.getPrice());
        values.put(COLUMN_TYPE, bread.getType());

        Long barcode = database.insertOrThrow(TABLE_BREAD, null, values);

        final Bread insertedEntity = new Bread.Builder()
                .copy(bread)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Bread update(Bread bread) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, bread.getBarcode());
        values.put(COLUMN_MANUFACTURER, bread.getManufacturer());
        values.put(COLUMN_BrandName, bread.getBrandName());
        values.put(COLUMN_PRICE, bread.getPrice());
        values.put(COLUMN_TYPE, bread.getType());
        database.update(
                TABLE_BREAD,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(bread.getBarcode())}
        );

        return bread;
    }

    @Override
    public Bread delete(Bread bread){
        open();
        database.delete(
                TABLE_BREAD,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(bread.getBarcode())}
        );

        return bread;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_BREAD, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Bread> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_BREAD;
        Set<Bread> breadServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Bread bread = new Bread.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                breadServices.add(bread);

            }while (cursor.moveToNext());
        }

        return breadServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_BREAD);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BREAD);
        onCreate(db);
    }
}

