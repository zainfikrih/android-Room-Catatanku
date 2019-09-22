package men.ngopi.zain.catatanku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import men.ngopi.zain.catatanku.adapters.NoteAdapter;
import men.ngopi.zain.catatanku.local.LocalRepository;
import men.ngopi.zain.catatanku.local.entity.NoteEntity;

public class MainActivity extends AppCompatActivity {

    private LocalRepository localRepository;
    private NoteAdapter adapter;
    private List<NoteEntity> mNoteEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localRepository = new LocalRepository(getApplication());

        FloatingActionButton fabAddNote = findViewById(R.id.fab_main);
        RecyclerView rvNote = findViewById(R.id.rv_main);
        final ImageView ivEmpty = findViewById(R.id.img_empty);
        adapter = new NoteAdapter();

        ivEmpty.setVisibility(View.VISIBLE);

        localRepository.getNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                if (noteEntities != null) {
                    if (noteEntities.size() > 0) {
                        mNoteEntities = noteEntities;
                        adapter.setNoteEntities(mNoteEntities);
                        adapter.notifyDataSetChanged();
                        ivEmpty.setVisibility(View.GONE);
                    } else {
                        ivEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvNote.setLayoutManager(llm);
        rvNote.setHasFixedSize(true);
        rvNote.setItemAnimator(new DefaultItemAnimator());
        rvNote.setAdapter(adapter);

        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(addIntent);
            }
        });

        SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(rvNote);
    }

    class SwipeController extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            localRepository.delete(mNoteEntities.get(position));
//            adapter.notifyDataSetChanged();
        }
    }
}
