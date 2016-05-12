package zo.sw.puzzle;

import android.content.Intent;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyPuzzles extends AppCompatActivity {
    private ArrayList<File> fileList;
    ListView listView;


            
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_puzzles);

        listView=(ListView)findViewById(R.id.lv);
        listView.setClickable(true);
        fileList = new ArrayList<>();
        File root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        getfile(root);

	ArrayAdapter<File> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,fileList);
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {


            String selectedFile = arg0.getItemAtPosition(position).toString().trim();
            selectedFile = ((TextView) (v.findViewById(android.R.id.text1))).getText().toString();
            //  File file = new File(selectedFile);
            //String path = file.getAbsolutePath();
            Toast.makeText(MyPuzzles.this.getApplicationContext(), "File selected : " + selectedFile, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyPuzzles.this, Game.class);
            intent.putExtra("path", selectedFile);
            MyPuzzles.this.startActivity(intent);
        }
    });
        
    }

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File aListFile : listFile) {

                if (aListFile.isDirectory()) {
                    //fileList.add(listFile[i]);
                    getfile(aListFile);

                } else {
                    if (aListFile.getName().endsWith(".puz"))

                    {
                        fileList.add(aListFile);
                    }
                }

            }
        }
        return fileList;
    }
   
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_puzzles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
