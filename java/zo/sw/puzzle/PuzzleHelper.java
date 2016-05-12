package zo.sw.puzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Swaathi on 22/04/2016.
 */
public class PuzzleHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="puzdatabase.db";
    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_TABLE="puztable";

    public static final String KEY_ID="puz_id";
    public static final String KEY_ANSWER="answers";
    public static final String KEY_CLUE="clues";
    public static final String KEY_LENGTH="length";
    public int count=1;

    public PuzzleHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String DATABASE_CREATE="create table if not exists "+DATABASE_TABLE+"( "+KEY_ID+" integer primary key autoincrement, "+KEY_CLUE+" text not null, "+KEY_ANSWER+" text not null, "+KEY_LENGTH+" integer not null);";
        db.execSQL("DROP TABLE IF EXISTS puztable");
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
    public void addPuz(DataEncapsulator encapsulator)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_CLUE, encapsulator.getCLUE());
        values.put(KEY_ANSWER,encapsulator.getANSWER());
        values.put(KEY_LENGTH,encapsulator.getLENGTH());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_TABLE, null, values);
        Log.i("In addPuz()", "Inserted Row " + count);
        count++;
        db.close();
    }
    public void deleteRecords()
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from "+DATABASE_TABLE);
    }
    /*public String getPuz(DataEncapsulator encapsulator)
    {
        String clueval="";
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT "+KEY_CLUE+", "+KEY_ANSWER+" FROM "+table_name+" WHERE "+KEY_TITLE+"="+encapsulator.getTITLE();
        Cursor cursor=db.rawQuery(query, null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            if (cursor.getString(cursor.getColumnIndex(KEY_CLUE))!=null) {
                clueval = cursor.getString(cursor.getColumnIndex("clues"));
            }
           // ansval=cursor.getString(cursor.getColumnIndex("answers"));
           // titleval=cursor.getString(cursor.getColumnIndex("title"));

        }
        cursor.close();
        return clueval;
    }*/
    /*public Cursor skipPuz(Context context)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(true, DATABASE_TABLE,null,null,null,null, null, null, null);
        cursor.moveToNext();
        return cursor;
    }*/
}
