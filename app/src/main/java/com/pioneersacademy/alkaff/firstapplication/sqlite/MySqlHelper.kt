package com.pioneersacademy.alkaff.firstapplication.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.location.Address

class MySqlHelper(context: Context) : SQLiteOpenHelper(context, "MyDatabase1", null, 2) {

    companion object {
         val TABLE_NAME_COMAPNY:String  = "COMPANY"
         val TABLE_NAME_HIGHEST:String  = "HIGHEST"
         val ID_COLUMN:String  = "_id"
         val NAME_COLUMN:String  = "NAME"
         val AGE_COLUMN:String  = "AGE"
         val VALUE_COLUMN:String  = "VALUE"
         val ADDRESS_COLUMN:String  = "ADDRESS"
         val SALARY_COLUMN:String  = "SALARY"
    }

    private  val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${TABLE_NAME_COMAPNY} ( ${ID_COLUMN} INTEGER PRIMARY KEY AUTOINCREMENT, ${NAME_COLUMN} TEXT NOT NULL," +
                " ${AGE_COLUMN} INT NOT NULL, ${ADDRESS_COLUMN} NVARCHAR(500),  ${SALARY_COLUMN} REAL)"


    private val SQL_CREATE_HIGHEST =
        "CREATE TABLE ${TABLE_NAME_HIGHEST} (" +
                " ${ID_COLUMN} INTEGER  PRIMARY KEY AUTOINCREMENT ," +
                " ${VALUE_COLUMN} INTEGER NOT NULL) ;";

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME_COMAPNY}"
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

    fun addHighestScore(hs:Int):Long {
        val mdb:SQLiteDatabase = this.writableDatabase
        val values = ContentValues().apply {
            put(VALUE_COLUMN,hs)
        }
        return  mdb?.insert(TABLE_NAME_HIGHEST,null,values)
    }

    fun getHighestScore():Int {
        val mdb:SQLiteDatabase = this.readableDatabase
        var cursor:Cursor? = null
        try{
             cursor = mdb.rawQuery("Select Max(VALUE) from HIGHEST;",null)
            if(cursor.moveToFirst())
            {
                return cursor.getInt(0)
            }
        }catch (e:SQLiteException)
        {
            return  0
        }
        return  0
        // Select max(Value) from Highest ;

    }

    fun addCompany(name:String,age:Int,address:String,salary:Double): Long? {
        val db = this.writableDatabase
        val v = ContentValues().apply {
            put(NAME_COLUMN,name)
            put(AGE_COLUMN,age)
            put(ADDRESS_COLUMN,address)
            put(SALARY_COLUMN,salary)
        }
        return  db?.insert(TABLE_NAME_COMAPNY,null,v)
    }

    fun getCompany(): Cursor? {
        val db = this.readableDatabase
        return  db.query(TABLE_NAME_COMAPNY,null,null,null,null,null,null)
    }
}