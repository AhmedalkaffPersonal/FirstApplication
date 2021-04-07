package com.pioneersacademy.alkaff.firstapplication.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySqlHelper(context: Context) : SQLiteOpenHelper(context, "MyDatabase", null, 2) {

    private val TABLE_NAME:String  = "COMPANY"
    private val TABLE_NAME_HIGHEST:String  = "HIGHEST"
    private val ID_COLUMN:String  = "_ID"
    private val NAME_COLUMN:String  = "NAME"
    private val AGE_COLUMN:String  = "AGE"
    private val VALUE_COLUMN:String  = "VALUE"
    private val ADDRESS_COLUMN:String  = "ADDRESS"
    private val SALARY_COLUMN:String  = "SALARY"
    private  val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${TABLE_NAME} ( ${ID_COLUMN} INTEGER PRIMARY KEY AUTOINCREMENT, ${NAME_COLUMN} TEXT NOT NULL," +
                " ${AGE_COLUMN} INT NOT NULL, ${ADDRESS_COLUMN} NVARCHAR(500),  ${SALARY_COLUMN} REAL)"


    private val SQL_CREATE_HIGHEST =
        "CREATE TABLE ${TABLE_NAME_HIGHEST} (" +
                " ${ID_COLUMN} INTEGER  PRIMARY KEY AUTOINCREMENT ," +
                " ${VALUE_COLUMN} INTEGER NOT NULL) ;";

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    private val SQL_DELETE_HIGHEST = "DROP TABLE IF EXISTS ${TABLE_NAME_HIGHEST}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
        db?.execSQL(SQL_CREATE_HIGHEST)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.execSQL(SQL_DELETE_HIGHEST)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }
}