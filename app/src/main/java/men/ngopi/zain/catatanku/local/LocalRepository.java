package men.ngopi.zain.catatanku.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import men.ngopi.zain.catatanku.local.entity.NoteEntity;
import men.ngopi.zain.catatanku.local.room.CatatankuDao;
import men.ngopi.zain.catatanku.local.room.CatatankuDatabase;

public class LocalRepository {
    private ExecutorService executorService;
    private CatatankuDao catatankuDao;

    public LocalRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();

        CatatankuDatabase database = CatatankuDatabase.getDatabase(application);
        catatankuDao = database.catatankuDao();
    }

    public LiveData<List<NoteEntity>> getNotes() {
        return catatankuDao.getNotes();
    }

    public void insert(final NoteEntity noteEntity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                catatankuDao.insert(noteEntity);
            }
        });
    }

    public void update(final NoteEntity noteEntity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                catatankuDao.update(noteEntity);
            }
        });
    }

    public void delete(final NoteEntity noteEntity) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                catatankuDao.delete(noteEntity);
            }
        });
    }
}