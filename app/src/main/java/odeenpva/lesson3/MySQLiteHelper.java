package odeenpva.lesson3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pva701 on 27.09.14.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_TIME = "time";
    public static final String DATABASE_NAME = "history.db";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_HISTORY + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_WORD + " text not null,"
            + COLUMN_TIME + " text not null);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
