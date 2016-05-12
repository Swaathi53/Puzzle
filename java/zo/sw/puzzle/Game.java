package zo.sw.puzzle;

import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends Activity {

    private static final String TEXTVIEW_STATE_KEY="TEXTVIEW_STATE_KEY";
    private static final String EDITTEXT_STATE_KEY="EDITTEXT_STATE_KEY";

    PuzzleHelper mydb;
    DataEncapsulator encapsulator;
    SQLiteDatabase db;
    Cursor c;
    int position,length;
    String filepath,clue,answer;
    Uri filepathURI;
    TextToPuzzle parse;
    Button cluebtn;
    Button skipbtn;
    TextView cluetext;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db=openOrCreateDatabase("puzdatabase.db", Context.MODE_PRIVATE,null);
        mydb = new PuzzleHelper(this.getApplicationContext());
        encapsulator = new DataEncapsulator();
        cluebtn = (Button) findViewById(R.id.button);
        skipbtn = (Button) findViewById(R.id.button2);
        cluetext = (TextView) findViewById(R.id.textView6);
        editText=(EditText)findViewById(R.id.editText);
        String text;

        if(savedInstanceState!=null&&savedInstanceState.containsKey(TEXTVIEW_STATE_KEY)&&savedInstanceState.containsKey(EDITTEXT_STATE_KEY))
        {
            text=savedInstanceState.getString(TEXTVIEW_STATE_KEY);
            cluetext.setText(text);

        }
        else {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                if (getIntent().getData() != null) {
                    filepathURI = getIntent().getData();
                    filepath = filepathURI.toString();
                    Toast.makeText(this, filepath, Toast.LENGTH_LONG).show();
                }
            } else {
                filepath = extras.getString("path");
                try {
                    parse = new TextToPuzzle(this, filepath);
                } catch (Exception e) {
                    Log.i("Exception in Game.java", e.getLocalizedMessage());
                    //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "File Parsing.." + filepath, Toast.LENGTH_SHORT).show();
            }
        }
        String query="SELECT * FROM puztable";
        c=db.rawQuery(query, null);
        c.moveToFirst();
        Toast.makeText(getApplicationContext(),"Press CLUE Button",Toast.LENGTH_LONG).show();

         editText.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {

                 if (editText.getText().toString().equals(answer)) {
                     editText.setTextColor(getResources().getColor(R.color.green));
                     Toast.makeText(getApplicationContext(), "Great! Let's move to the next puzzle!", Toast.LENGTH_LONG).show();
                     nextPuz();
                 }
             }
         });
    }

    @Override
    protected void onPause()
    {
        position=c.getPosition();
        super.onPause();
    }
    @Override
    public void onResume()
    {
        c.moveToPosition(position);
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){

        super.onSaveInstanceState(saveInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        }

    public void displayClue(View view) {

        clue = c.getString(1);
        answer = c.getString(2);
        length = c.getInt(3);
        cluetext.setText(clue);
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setText("");
    }
    public void skipPuzzle(View view)

    {
        if (!c.isLast()) {
            c.moveToNext();
            cluetext.setText("Press CLUE Button");
        } else
            Toast.makeText(this, "End of file reached. Move to Previous", Toast.LENGTH_SHORT).show();
    }
    public void nextPuz()
    {
        if(!c.isLast()) {
            c.moveToNext();
            cluetext.setText("Great! You cracked it! Press CLUE Button");
            editText.setTextColor(getResources().getColor(R.color.green));
            //editText.setText("");
            }
    }
    public void movePrevious(View view) {
        if (!c.isFirst()) {
            c.moveToPrevious();
            cluetext.setText("Press CLUE Button");
        } else
            Toast.makeText(this, "Start of file reached. Move to Next", Toast.LENGTH_SHORT).show();

    }

}
