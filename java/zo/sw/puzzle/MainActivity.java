package zo.sw.puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.*;


public class MainActivity extends AppCompatActivity {

    Button mypuzbtn;
    Button resbtn;
    static Context appContext;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        appContext=getApplicationContext();
        mypuzbtn=(Button)findViewById(R.id.mypuzzles_btn);

        mypuzbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(MainActivity.this, MyPuzzles.class);
                                            startActivity(intent);

                                        }
                                    });

    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
