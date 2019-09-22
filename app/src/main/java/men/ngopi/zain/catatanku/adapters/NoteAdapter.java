package men.ngopi.zain.catatanku.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import men.ngopi.zain.catatanku.AddNoteActivity;
import men.ngopi.zain.catatanku.R;
import men.ngopi.zain.catatanku.local.entity.NoteEntity;

import static men.ngopi.zain.catatanku.AddNoteActivity.OPEN_NOTE;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<NoteEntity> noteEntities = new ArrayList<>();

    public void setNoteEntities(List<NoteEntity> noteEntities) {
        this.noteEntities = noteEntities;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, int position) {
        final NoteEntity noteEntity = noteEntities.get(position);
        holder.bindViewHolder(noteEntity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), AddNoteActivity.class);
                intent.putExtra(OPEN_NOTE, noteEntity);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Geser catatan untuk menghapusnya", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteEntities.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_note_item);
            tvNote = itemView.findViewById(R.id.tv_body_note_item);
        }

        void bindViewHolder(NoteEntity noteEntity) {
            tvTitle.setText(noteEntity.getTitle());
            tvNote.setText(noteEntity.getNote());
        }
    }
}
