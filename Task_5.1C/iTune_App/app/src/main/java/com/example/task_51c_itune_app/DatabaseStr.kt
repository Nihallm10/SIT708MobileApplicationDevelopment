package com.example.task_51c_itune_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseStr(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ItunesAndroid.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val TABLE_PLAYLIST = "playlist"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FULL_NAME = "fullname"
        private const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_URL = "url"

        private const val SQL_CREATE_USERS_TABLE =
            "CREATE TABLE $TABLE_USERS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_FULL_NAME STRING," +
                    "$COLUMN_USERNAME TEXT," +
                    "$COLUMN_PASSWORD TEXT)"

        private const val SQL_CREATE_PLAYLIST_TABLE =
            "CREATE TABLE $TABLE_PLAYLIST (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USERNAME TEXT," +
                    "$COLUMN_URL TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USERS_TABLE)
        db.execSQL(SQL_CREATE_PLAYLIST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    fun insertUser(fullName: String, username: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_FULL_NAME, fullName)
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUser(username: String): Cursor? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD)
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        return db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null)
    }

    fun insertPlaylistUrl(username: String, url: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_URL, url)
        return db.insert(TABLE_PLAYLIST, null, values)
    }

    fun getPlaylistItems(username: String): Cursor? {
        val db = readableDatabase
        val projection = arrayOf("id AS _id", COLUMN_URL)
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        return db.query(TABLE_PLAYLIST, projection, selection, selectionArgs, null, null, null)
    }
}

