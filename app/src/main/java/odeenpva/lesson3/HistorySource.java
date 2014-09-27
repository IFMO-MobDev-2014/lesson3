package odeenpva.lesson3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by pva701 on 27.09.14.
 */
public class HistorySource {
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_WORD, MySQLiteHelper.COLUMN_TIME};
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public HistorySource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public HistoryItem insertWord(String word) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_WORD, word);
        String dateStr = dateFormat.format(new Date());
        values.put(MySQLiteHelper.COLUMN_TIME, dateStr);
        long id = database.insertOrThrow(MySQLiteHelper.TABLE_HISTORY, null, values);
        Log.i("INFO", "id = " + id);
        return new HistoryItem((int)id, word, new Date());
    }

    public ArrayList<HistoryItem> getAllWords() {
        ArrayList <HistoryItem> res = new ArrayList<HistoryItem>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_HISTORY,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HistoryItem word = cursorToComment(cursor);
            res.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    private HistoryItem cursorToComment(Cursor cursor) {
        HistoryItem res = new HistoryItem();
        res.setId(cursor.getInt(0));
        res.setWord(cursor.getString(1));
        try {
            res.setTime(dateFormat.parse(cursor.getString(2)));
        } catch (Exception e) {
            Log.i("TIME", "wrong cast");
            e.printStackTrace();
        }
        return  res;
    }
}
