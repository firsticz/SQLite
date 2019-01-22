package com.example.first.lab10.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.first.lab10.Model.Friend;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, Friend.DATABASE_NAME, null, Friend.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIEND_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                Friend.TABLE,
                Friend.Column.ID,
                Friend.Column.FIRST_NAME,
                Friend.Column.LAST_NAME,
                Friend.Column.TEL,
                Friend.Column.EMAIL,
                Friend.Column.DESCRIPTION);

        Log.i(TAG, CREATE_FRIEND_TABLE);

        // create friend table
        db.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Friend.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }



    //CRUD ( CREATE, READ, UPDATE, DELETE )

    //CREATE
    public void addFriend(Friend friend) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(Friend.Column.ID, friend.getId());
        values.put(Friend.Column.FIRST_NAME, friend.getFirstName());
        values.put(Friend.Column.LAST_NAME, friend.getLastName());
        values.put(Friend.Column.TEL, friend.getTel());
        values.put(Friend.Column.EMAIL, friend.getEmail());
        values.put(Friend.Column.DESCRIPTION, friend.getDescription());

        sqLiteDatabase.insert(Friend.TABLE, null, values);

        sqLiteDatabase.close();
    }

    //READ
    public Friend getFriend(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query( Friend.TABLE,
                null,
                Friend.Column.ID + " = ? ",
                new String[] { id },
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        Friend friend = new Friend();

        friend.setId((int) cursor.getLong(0));
        friend.setFirstName(cursor.getString(1));
        friend.setLastName(cursor.getString(2));
        friend.setTel(cursor.getString(3));
        friend.setEmail(cursor.getString(4));
        friend.setDescription(cursor.getString(5));

        return friend;
    }
    public List<String> getFriendList() {
        List<String> friends = new ArrayList<>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (Friend.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            friends.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return friends;
    }

    /**
     * Call when need List<Friend> instead of List<String>
     * @return List of friends.
     */
    public List<Friend> getAllFriend() {

        String QUERY_ALL_FRIEND = "SELECT * FROM " + Friend.TABLE;

        List<Friend> friends = new ArrayList<Friend>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(QUERY_ALL_FRIEND, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            Friend friend = new Friend();
            friend.setId((int) cursor.getLong(0));
            friend.setFirstName(cursor.getString(1));
            friend.setLastName(cursor.getString(2));
            friend.setEmail(cursor.getString(3));
            friend.setDescription(cursor.getString(4));

            friends.add(friend);

            cursor.moveToNext();
        }

        sqLiteDatabase.close();


        return friends;
    }


    //UPDATE
    public void updateFriend(Friend friend) {

        sqLiteDatabase  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Friend.Column.ID, friend.getId());
        values.put(Friend.Column.FIRST_NAME, friend.getFirstName());
        values.put(Friend.Column.LAST_NAME, friend.getLastName());
        values.put(Friend.Column.EMAIL, friend.getEmail());
        values.put(Friend.Column.DESCRIPTION, friend.getDescription());

        int row = sqLiteDatabase.update(Friend.TABLE,
                values,
                Friend.Column.ID + " = ? ",
                new String[] { String.valueOf(friend.getId()) });

        sqLiteDatabase.close();
    }

    //DELETE
    public void deleteFriend(String id) {

        sqLiteDatabase = this.getWritableDatabase();

/*        sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
                new String[] { String.valueOf(friend.getId()) });*/
        sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }
}
