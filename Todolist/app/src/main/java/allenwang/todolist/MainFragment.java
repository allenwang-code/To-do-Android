package allenwang.todolist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by allenwang.
 */
public class MainFragment extends android.support.v4.app.Fragment
        implements CardviewAdapter.AdapterCallback, MenuItem.OnMenuItemClickListener {
    private static final String TAG = MainFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private CardviewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<TodoItem> todoItems = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CardviewAdapter(getActivity(), this, todoItems);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ApiInterface apiInterface = ApiServiceGenerator.createService(ApiInterface.class);
        Call<ArrayList<TodoItem>> call = apiInterface.getToDoList();

        call.enqueue(new Callback<ArrayList<TodoItem>>() {
            @Override
            public void onResponse(Call<ArrayList<TodoItem>> call, Response<ArrayList<TodoItem>> response) {
                if (response.isSuccessful()) {

                    todoItems = response.body();

                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            ToDoTable toDoTable = new ToDoTable();
                            toDoTable.save();
                        }
                    }).build();
                    transaction.execute();


                    ((CardviewAdapter) mRecyclerView.getAdapter()).updateData(todoItems);
                    mRecyclerView.getAdapter().notifyDataSetChanged();

                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<TodoItem>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onArticleClick(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        ((MainActivity) getActivity()).navigateToFragement(new ContentFragment(), bundle);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ContentFragment fragment = new ContentFragment();
        ((MainActivity) getActivity()).navigateToFragement(fragment);
        return false;
    }
}
