package com.chase.apps.pantry.repository.user.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.UserRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-09-21.
 */
public class UserRepositoryImpl extends SQLiteOpenHelper implements UserRepository {

    public static final String TABLE_USER = "user";
    private SQLiteDatabase database;

    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_DATEOFBIRTH = "dateofbirth";
    public static final String COLUMN_EMAIL = "email";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "("
            + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_SURNAME + " TEXT NOT NULL,"
            + COLUMN_DATEOFBIRTH + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT NOT NULL);";

    public UserRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public UserRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public UserRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
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
    public User findById(String userID, String surname)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(
                TABLE_USER,
                new String[]{
                        COLUMN_USERID,
                        COLUMN_NAME,
                        COLUMN_SURNAME,
                        COLUMN_DATEOFBIRTH,
                        COLUMN_EMAIL},
                COLUMN_USERID + " =? ",
                new String[]{String.valueOf(userID)},
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst())
        {
            final User user = new User.Builder()
                    .userID(cursor.getString(0))
                    .name(cursor.getString(1))
                    .surname(cursor.getString(2))
                    .dateOfBirth(cursor.getString(3))
                    .email(cursor.getString(4))
                    .build();
            return user;
        }
        else
        {
            return null;
        }
    }

    @Override
    public User save(User user)
    {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERID, user.getUserID());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_SURNAME, user.getSurname());
        values.put(COLUMN_DATEOFBIRTH, user.getDateOfBirth());
        values.put(COLUMN_EMAIL, user.getUserEmail());

        Long userID = database.insertOrThrow(TABLE_USER, null, values);

        final User insertedEntity = new User.Builder()
                .copy(user)
                .userID(userID.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public User update(User user)
    {
        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERID, user.getUserID());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_SURNAME, user.getSurname());
        values.put(COLUMN_DATEOFBIRTH, user.getDateOfBirth());
        values.put(COLUMN_EMAIL, user.getUserEmail());

        database.update(
                TABLE_USER,
                values,
                COLUMN_USERID + " =? ",
                new String[]{String.valueOf(user.getUserID())}
        );

        return user;
    }

    @Override
    public User delete(User user)
    {
        open();
        database.delete(
                TABLE_USER,
                COLUMN_USERID + " =? ",
                new String[]{String.valueOf(user.getUserID())}
        );

        return user;
    }

    @Override
    public Set<User> findAll()
    {
        open();
        String selectAll = " SELECT * FROM " + TABLE_USER;
        Set<User> users = new HashSet<>();
        //open();
        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do {

                final User user = new User.Builder()
                        .userID(cursor.getString(0))
                        .name(cursor.getString(1))
                        .surname(cursor.getString(2))
                        .dateOfBirth(cursor.getString(3))
                        .email(cursor.getString(4))
                        .build();

                users.add(user);

            }while(cursor.moveToNext());
        }

        return users;
    }

    @Override
    public int deleteAll()
    {
        open();
        int rowsDeleted = database.delete(TABLE_USER, null, null);
        //close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }
}