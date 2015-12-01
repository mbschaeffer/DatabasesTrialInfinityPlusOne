package com.kiskiarea.databasestrialinfinityplusone;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Melissa on 11/15/2015.
 */
public class DBAdapter
{

    //static final String DATABASE_NAME = "mydb22";
    static final String DATABASE_NAME = "MyPeriodicDB";

    //static final String DATABASE_TABLE = "contacts";
    static final String DATABASE_TABLE = "periodic_table";

    static final int DATABASE_VERSION = 1;

    static final String KEY_ATOMIC_NUMBER = "_atomic_number";
    static final String KEY_ATOMIC_WEIGHT = "atomic_weight";
    static final String KEY_NAME = "name";
    static final String KEY_SYMBOL = "symbol";
    static final String KEY_BPOINT = "boiling_point";
    static final String KEY_MPOINT = "melting_point";
    static final String KEY_DENSITY = "density";
    static final String KEY_PHASE = "phase";


    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;



    static final String DATABASE_CREATE = "create table periodic_table (_atomic_number integer " +
            "primary key, " + "atomic_weight numeric, name text, symbol text);";



    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try{
                db.execSQL(DATABASE_CREATE);
                Log.w("DBAdapter","CREATING THE DATATEBASE");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("DBAdapter", "Upgrading from v " + oldVersion + " version to " + newVersion);
        }

    }

    //-----Opens the database------
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //-----Closes the database
    public void close()
    {
        DBHelper.close();
    }


    //---------retrieves all the elements
    public Cursor getAllElements()
    {
        return db.query(DATABASE_TABLE, new String[]{KEY_NAME, KEY_ATOMIC_NUMBER, KEY_ATOMIC_WEIGHT,
                        KEY_SYMBOL, KEY_BPOINT, KEY_MPOINT, KEY_DENSITY, KEY_PHASE},
                null, null, null, null, null);
    }

    //---------retrieves a particular element
    public Cursor getElement(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE,
                new String[]{KEY_NAME, KEY_ATOMIC_NUMBER, KEY_ATOMIC_WEIGHT,
                        KEY_SYMBOL, KEY_BPOINT, KEY_MPOINT, KEY_DENSITY, KEY_PHASE}, KEY_ATOMIC_NUMBER
                            + "=" + rowId, null, null, null, null,null);
        if(mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Element findElement(String elementname) throws SQLException
    {
        String query = "Select * FROM " + DATABASE_TABLE + " WHERE " + KEY_NAME + " " +
                "= \"" + elementname + "\"";


        db = DBHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Element element = new Element();

        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();

            element.set_atomic_number(Integer.parseInt(cursor.getString(0)));
            element.set_atomic_weight(Double.parseDouble(cursor.getString(1)));
            element.set_name(cursor.getString(2));
            element.set_symbol(cursor.getString(3));
            element.set_melting_point(Integer.parseInt(cursor.getString(4)));
            element.set_boiling_point(Integer.parseInt(cursor.getString(5)));
            element.set_density(Integer.parseInt(cursor.getString(6)));
            element.set_phase(cursor.getString(7));
            cursor.close();
        }
        else
        {
            element = null;
        }
        db.close();

        return element;
    }




}
