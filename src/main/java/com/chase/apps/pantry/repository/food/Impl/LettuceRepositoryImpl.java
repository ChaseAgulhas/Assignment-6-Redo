package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Lettuce;
import com.chase.apps.pantry.repository.food.LettuceRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class LettuceRepositoryImpl extends SQLiteOpenHelper implements LettuceRepository {
    public static final String TABLE_LETTUCE = "lettuce";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_LETTUCE + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public LettuceRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public LettuceRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public LettuceRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Lettuce findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_LETTUCE,
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
            final Lettuce lettuce = new Lettuce.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return lettuce;
        }
        else {
            return null;
        }
    }

    @Override
    public Lettuce save(Lettuce lettuce) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, lettuce.getBarcode());
        values.put(COLUMN_MANUFACTURER, lettuce.getManufacturer());
        values.put(COLUMN_BrandName, lettuce.getBrandName());
        values.put(COLUMN_PRICE, lettuce.getPrice());
        values.put(COLUMN_TYPE, lettuce.getType());

        Long barcode = database.insertOrThrow(TABLE_LETTUCE, null, values);

        final Lettuce insertedEntity = new Lettuce.Builder()
                .copy(lettuce)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Lettuce update(Lettuce lettuce) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, lettuce.getBarcode());
        values.put(COLUMN_MANUFACTURER, lettuce.getManufacturer());
        values.put(COLUMN_BrandName, lettuce.getBrandName());
        values.put(COLUMN_PRICE, lettuce.getPrice());
        values.put(COLUMN_TYPE, lettuce.getType());
        database.update(
                TABLE_LETTUCE,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(lettuce.getBarcode())}
        );

        return lettuce;
    }

    @Override
    public Lettuce delete(Lettuce lettuce){
        open();
        database.delete(
                TABLE_LETTUCE,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(lettuce.getBarcode())}
        );

        return lettuce;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_LETTUCE, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Lettuce> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_LETTUCE;
        Set<Lettuce> lettuceServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Lettuce lettuce = new Lettuce.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                lettuceServices.add(lettuce);

            }while (cursor.moveToNext());
        }

        return lettuceServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_LETTUCE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LETTUCE);
        onCreate(db);
    }
}


