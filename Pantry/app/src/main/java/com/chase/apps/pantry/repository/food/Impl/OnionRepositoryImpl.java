package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Onion;
import com.chase.apps.pantry.repository.food.OnionRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class OnionRepositoryImpl extends SQLiteOpenHelper implements OnionRepository {
    public static final String TABLE_ONION = "onion";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_ONION + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public OnionRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public OnionRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public OnionRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Onion findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_ONION,
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
            final Onion onion = new Onion.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return onion;
        }
        else {
            return null;
        }
    }

    @Override
    public Onion save(Onion onion) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, onion.getBarcode());
        values.put(COLUMN_MANUFACTURER, onion.getManufacturer());
        values.put(COLUMN_BrandName, onion.getBrandName());
        values.put(COLUMN_PRICE, onion.getPrice());
        values.put(COLUMN_TYPE, onion.getType());

        Long barcode = database.insertOrThrow(TABLE_ONION, null, values);

        final Onion insertedEntity = new Onion.Builder()
                .copy(onion)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Onion update(Onion onion) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, onion.getBarcode());
        values.put(COLUMN_MANUFACTURER, onion.getManufacturer());
        values.put(COLUMN_BrandName, onion.getBrandName());
        values.put(COLUMN_PRICE, onion.getPrice());
        values.put(COLUMN_TYPE, onion.getType());
        database.update(
                TABLE_ONION,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(onion.getBarcode())}
        );

        return onion;
    }

    @Override
    public Onion delete(Onion onion){
        open();
        database.delete(
                TABLE_ONION,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(onion.getBarcode())}
        );

        return onion;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_ONION, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Onion> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_ONION;
        Set<Onion> onionServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Onion onion = new Onion.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                onionServices.add(onion);

            }while (cursor.moveToNext());
        }

        return onionServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_ONION);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONION);
        onCreate(db);
    }
}


