package com.example.recyclerview.activity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerview.R;

import com.example.recyclerview.adapter.RecyclerAdapter;
import com.example.recyclerview.interfaces.RecyclerViewClickInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends BaseActivity implements RecyclerViewClickInterface {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<String> moviesList;
    ArrayList<String> archiveList;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        moviesList = new ArrayList<>();
        archiveList = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerAdapter = new RecyclerAdapter(moviesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        moviesList.add("Iron Man");
        moviesList.add("The Incredible Hulk");
        moviesList.add("Iron Man 2");
        moviesList.add("Thor");
        moviesList.add("Captain America: The First Avenger");
        moviesList.add("The Avengers");
        moviesList.add("Iron Man 3");
        moviesList.add("Thor: The Dark World");
        moviesList.add("Captain America: The Winter Soldier");
        moviesList.add("Guardians of the Galaxy");
        moviesList.add("Avengers: Age of Ultron");
        moviesList.add("Ant-Man");
        moviesList.add("Captain America: Civil War");
        moviesList.add("Doctor Strange");
        moviesList.add("Guardians of the Galaxy Vol. 2");
        moviesList.add("Spider-Man: Homecoming");
        moviesList.add("Thor: Ragnarok");
        moviesList.add("Black Panther");
        moviesList.add("Avengers: Infinity War");
        moviesList.add("Ant-Man and the Wasp");
        moviesList.add("Captain Marvel");
        moviesList.add("Avengers: Endgame");
        moviesList.add("Spider-Man: Far From Home");


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                moviesList.add("Black Widow (2020)");
                moviesList.add("The Eternals (2020)");
                moviesList.add("Shang-Chi and the Legend of the Ten Rings (2021)");
                moviesList.add("Doctor Strange in the Multiverse of Madness (2021)");
                moviesList.add("Thor: Love and Thunder (2021)");

                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    String deletedItem = "";
    String archivedItem = "";

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedItem = moviesList.get(pos);
                    moviesList.remove(pos);
                    //recyclerAdapter.notifyItemRemoved(pos);
                    recyclerAdapter.notifyDataSetChanged();
                    Snackbar.make(recyclerView, deletedItem, Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    moviesList.add(pos, deletedItem);
                                    //recyclerAdapter.notifyItemInserted(pos);
                                    recyclerAdapter.notifyDataSetChanged();
                                }
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:

                    archivedItem = moviesList.get(pos);
                    archiveList.add(archivedItem);
                    moviesList.remove(pos);
                    //recyclerAdapter.notifyItemRemoved(pos);
                    recyclerAdapter.notifyDataSetChanged();
                    Snackbar.make(recyclerView, archivedItem, Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    moviesList.add(pos, archivedItem);
                                    archiveList.remove(archiveList.lastIndexOf(archivedItem));
                                    //recyclerAdapter.notifyItemInserted(pos);
                                    recyclerAdapter.notifyDataSetChanged();
                                }
                            }).show();
                    break;

            }

        }


        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.teal_200))
                    .addSwipeRightActionIcon(R.drawable.ic_archive)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };





    @Override
    public void onItemClick(int pos) {

        Toast.makeText(this, moviesList.get(pos), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLongClick(int pos) {


    }
}