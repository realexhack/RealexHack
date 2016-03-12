package realexhack.realexhack;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by josekalladanthyil on 11/03/16.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_home:
                Log.d("Menu", "Going to nav home");
                Intent act1 = new Intent(this.getApplicationContext(),MainActivity.class);
                startActivity(act1);;
                return true;
            case R.id.nav_shopping_trolley:
                Log.d("Menu", "Going to trolley");
                Intent act2 = new Intent(this.getApplicationContext(),ShoppingTrolley.class);
                startActivity(act2);;
                return true;
            case R.id.nav_map:
                Log.d("Menu", "Going to nav map");
                Intent act3 = new Intent(this.getApplicationContext(),MapActivity.class);
                startActivity(act3);;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
