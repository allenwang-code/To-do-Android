package allenwang.todolist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MainFragment fragment = new MainFragment();
        navigateToFragement(fragment);

        MenuItem AddItem = menu.findItem(R.id.add_item);
        AddItem.setOnMenuItemClickListener(fragment);
        return true;
    }

    @Override
    public void onBackPressed() {
        boolean isHavingStack = getSupportFragmentManager().getBackStackEntryCount() > 1;
        if (isHavingStack) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().show();
        } else {
            //popUpToCheckLeaving();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    protected void backToMainFragement() {
        navigateToFragement(new MainFragment(), null);
    }

    protected void navigateToFragement(android.support.v4.app.Fragment fragment) {
        navigateToFragement(fragment, null);
    }

    protected void navigateToFragement(android.support.v4.app.Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFragment_container,
                fragment,
                fragment.getClass().getSimpleName()
        );
        ft.addToBackStack(null);
        ft.commit();
    }
}

