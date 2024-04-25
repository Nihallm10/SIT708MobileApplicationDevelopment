package com.example.task_manager_app_41p

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "TaskManager.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Tasks"
        const val COLUMN_ID = "id"
        const val COLUMN_TASK_TITLE = "task_title"
        const val COLUMN_TASK_DESCRIPTION = "task_description"
        const val COLUMN_DUE_DATE = "due_date"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TASK_TITLE TEXT,"
                + "$COLUMN_TASK_DESCRIPTION TEXT,"
                + "$COLUMN_DUE_DATE TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(taskTitle: String, taskDescription: String, dueDate: String): Boolean {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TASK_TITLE, taskTitle)
        contentValues.put(COLUMN_TASK_DESCRIPTION, taskDescription)
        contentValues.put(COLUMN_DUE_DATE, dueDate)
        val db = this.writableDatabase
        val rowId = db.insert(TABLE_NAME, null, contentValues)
        return rowId != -1L
    }

    fun getAllTasks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteTask(taskId: String): Boolean {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(taskId))
        return rowsAffected > 0
    }

    fun updateTask(taskId: String, taskTitle: String, taskDescription: String, dueDate: String): Boolean {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TASK_TITLE, taskTitle)
        contentValues.put(COLUMN_TASK_DESCRIPTION, taskDescription)
        contentValues.put(COLUMN_DUE_DATE, dueDate)
        val db = this.writableDatabase
        val rowsAffected = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(taskId))
        return rowsAffected > 0
    }
}

