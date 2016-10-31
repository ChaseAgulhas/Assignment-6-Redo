package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Tomato;
import com.chase.apps.pantry.repository.food.TomatoRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class TomatoRepositoryImpl extends SQLiteOpenHelper implements TomatoRepository {
    public static final String TABLE_TOMATO = "tomato";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_TOMATO + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public TomatoRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public TomatoRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TomatoRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Tomato findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_TOMATO,
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
            final Tomato tomato = new Tomato.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return tomato;
        }
        else {
            return null;
        }
    }

    @Override
    public Tomato save(Tomato tomato) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, tomato.getBarcode());
        values.put(COLUMN_MANUFACTURER, tomato.getManufacturer());
        values.put(COLUMN_BrandName, tomato.getBrandName());
        values.put(COLUMN_PRICE, tomato.getPrice());
        values.put(COLUMN_TYPE, tomato.getType());

        Long barcode = database.insertOrThrow(TABLE_TOMATO, null, values);

        final Tomato insertedEntity = new Tomato.Builder()
                .copy(tomato)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Tomato update(Tomato tomato) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, tomato.getBarcode());
        values.put(COLUMN_MANUFACTURER, tomato.getManufacturer());
        values.put(COLUMN_BrandName, tomato.getBrandName());
        values.put(COLUMN_PRICE, tomato.getPrice());
        values.put(COLUMN_TYPE, tomato.getType());
        database.update(
                TABLE_TOMATO,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(tomato.getBarcode())}
        );

        return tomato;
    }

    @Override
    public Tomato delete(Tomato tomato){
        open();
        database.delete(
                TABLE_TOMATO,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(tomato.getBarcode())}
        );

        return tomato;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_TOMATO, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Tomato> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_TOMATO;
        Set<Tomato> tomatoServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Tomato tomato = new Tomato.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                tomatoServices.add(tomato);

            }while (cursor.moveToNext());
        }

        return tomatoServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_TOMATO);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOMATO);
        onCreate(db);
    }
}


