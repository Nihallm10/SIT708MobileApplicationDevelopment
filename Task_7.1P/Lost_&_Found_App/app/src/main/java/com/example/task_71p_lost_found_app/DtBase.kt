package com.example.task_71p_lost_found_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DtBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {


        const val DATABASE_NAME = "LostFoundDB"
        const val DATABASE_VERSION = 2  // Increased version for upgrade
        const val TABLE_ITEMS = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_DATE = "date"
        const val COLUMN_ITEM_TYPE = "itemType" // "lost" or "found"
        const val COLUMN_PHONE = "phone" // New column for phone number
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_ITEMS_TABLE = "CREATE TABLE $TABLE_ITEMS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_LOCATION TEXT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_ITEM_TYPE TEXT," +
                "$COLUMN_PHONE TEXT)"
        db.execSQL(CREATE_ITEMS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    fun insertItems(name: String, description: String, location: String, date: String, itemType: String, phone: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_DESCRIPTION, description)
        contentValues.put(COLUMN_LOCATION, location)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_ITEM_TYPE, itemType)
        contentValues.put(COLUMN_PHONE, phone)
        val result = db.insert(TABLE_ITEMS, null, contentValues)
        db.close()
        return result != -1L // returns true if insertion is successful
    }

    fun deleteItem(id: String?): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_ITEMS, "$COLUMN_ID = ?", arrayOf(id))
        db.close()
        return result > 0
    }

    fun getAllItems(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_ITEMS", null)
    }
}