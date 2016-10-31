package com.chase.apps.pantry.repository.food.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Potato;
import com.chase.apps.pantry.repository.food.PotatoRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class PotatoRepositoryImpl extends SQLiteOpenHelper implements PotatoRepository {
    public static final String TABLE_POTATO = "potato";
    private SQLiteDatabase database;

    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_MANUFACTURER = "manufacturer";
    private static final String COLUMN_BrandName = "brandName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_POTATO + "("
            + COLUMN_BARCODE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MANUFACTURER + " TEXT NOT NULL,"
            + COLUMN_BrandName + " TEXT NOT NULL,"
            + COLUMN_PRICE + " TEXT NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL);";

    public PotatoRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public PotatoRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PotatoRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public Potato findById(String barcode, String type)
    {
        SQLiteDatabase database =  this.getReadableDatabase();
        Cursor cursor = database.query(
                TABLE_POTATO,
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
            final Potato potato = new Potato.Builder()
                    .barcode(cursor.getString(0))
                    .manufacturer(cursor.getString(1))
                    .brandName(cursor.getString(2))
                    .price(cursor.getString(3))
                    .type(cursor.getString(5))
                    .build();

            return potato;
        }
        else {
            return null;
        }
    }

    @Override
    public Potato save(Potato potato) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, potato.getBarcode());
        values.put(COLUMN_MANUFACTURER, potato.getManufacturer());
        values.put(COLUMN_BrandName, potato.getBrandName());
        values.put(COLUMN_PRICE, potato.getPrice());
        values.put(COLUMN_TYPE, potato.getType());

        Long barcode = database.insertOrThrow(TABLE_POTATO, null, values);

        final Potato insertedEntity = new Potato.Builder()
                .copy(potato)
                .barcode(barcode.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Potato update(Potato potato) {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BARCODE, potato.getBarcode());
        values.put(COLUMN_MANUFACTURER, potato.getManufacturer());
        values.put(COLUMN_BrandName, potato.getBrandName());
        values.put(COLUMN_PRICE, potato.getPrice());
        values.put(COLUMN_TYPE, potato.getType());
        database.update(
                TABLE_POTATO,
                values,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(potato.getBarcode())}
        );

        return potato;
    }

    @Override
    public Potato delete(Potato potato){
        open();
        database.delete(
                TABLE_POTATO,
                COLUMN_BARCODE + " =? ",
                new String[]{String.valueOf(potato.getBarcode())}
        );

        return potato;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = database.delete(TABLE_POTATO, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Potato> findAll() {
        open();
        String selectAll = " SELECT * FROM " + TABLE_POTATO;
        Set<Potato> potatoServices = new HashSet<>();

        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                final Potato potato = new Potato.Builder()
                        .barcode(cursor.getString(0))
                        .manufacturer(cursor.getString(1))
                        .brandName(cursor.getString(2))
                        .price(cursor.getString(3))
                        .type(cursor.getString(5))
                        .build();

                potatoServices.add(potato);

            }while (cursor.moveToNext());
        }

        return potatoServices;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_POTATO);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POTATO);
        onCreate(db);
    }
}


