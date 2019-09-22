package men.ngopi.zain.catatanku.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import men.ngopi.zain.catatanku.local.entity.NoteEntity;

@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
public abstract class CatatankuDatabase extends RoomDatabase {
    public abstract CatatankuDao catatankuDao();

    private static volatile CatatankuDatabase INSTANCE;

    public static CatatankuDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CatatankuDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CatatankuDatabase.class, "catatanku_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
