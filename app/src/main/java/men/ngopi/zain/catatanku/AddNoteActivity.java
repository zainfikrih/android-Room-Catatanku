package men.ngopi.zain.catatanku;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import men.ngopi.zain.catatanku.local.LocalRepository;
import men.ngopi.zain.catatanku.local.entity.NoteEntity;

public class AddNoteActivity extends AppCompatActivity {

    public static final String OPEN_NOTE = "open_note";
    private NoteEntity mNoteEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        final LocalRepository repository = new LocalRepository(getApplication());

        Toolbar toolbar = findViewById(R.id.toolbar_add_note);
        final TextInputEditText etTitle = findViewById(R.id.et_title);
        final TextInputEditText etNote = findViewById(R.id.et_note);
        MaterialButton btnAdd = findViewById(R.id.btn_add);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.containsKey(OPEN_NOTE)){
                mNoteEntity = extras.getParcelable(OPEN_NOTE);
                etTitle.setText(mNoteEntity != null ? mNoteEntity.getTitle() : null);
                etNote.setText(mNoteEntity != null ? mNoteEntity.getNote() : null);
            }
        }

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etNote.getText()) && !TextUtils.isEmpty(etTitle.getText())) {
                    NoteEntity noteEntity = new NoteEntity();
                    noteEntity.setNote(etNote.getText().toString());
                    noteEntity.setTitle(etTitle.getText().toString());
                    if(mNoteEntity != null){
                        noteEntity.setId(mNoteEntity.getId());
                        repository.update(noteEntity);
                    } else {
                        repository.insert(noteEntity);
                    }
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "Lengkapi Judul dan Isi Catatan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
