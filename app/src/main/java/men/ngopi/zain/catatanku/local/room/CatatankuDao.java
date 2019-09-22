package men.ngopi.zain.catatanku.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import men.ngopi.zain.catatanku.local.entity.NoteEntity;

@Dao
public interface CatatankuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteEntity noteEntity);

    @Update()
    void update(NoteEntity noteEntity);

    @Delete()
    void delete(NoteEntity noteEntity);

    @Query("SELECT * from NoteEntity ORDER BY id DESC")
    LiveData<List<NoteEntity>> getNotes();
}
