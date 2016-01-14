package de.greenrobot.dao.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.sample.dao.DaoMaster;
import de.greenrobot.dao.sample.dao.DaoSession;
import de.greenrobot.dao.sample.dao.Note;
import de.greenrobot.dao.sample.dao.NoteDao;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxGreenDao";

    private DaoSession daoSession;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this, "note.db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        Note note = new Note();
        note.setText("hello world!");

        noteDao.rxInsert(note)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.e(TAG, "#-----doOnNext---->>>" + aLong);
                    }
                })
                .flatMap(new Func1<Long, Observable<List<Note>>>() {
                    @Override
                    public Observable<List<Note>> call(Long aLong) {
                        return noteDao.rxQueryBuilder().list();
                    }
                })
                .flatMap(new Func1<List<Note>, Observable<Note>>() {
                    @Override
                    public Observable<Note> call(List<Note> notes) {
                        return Observable.from(notes);
                    }
                })
                .flatMap(new Func1<Note, Observable<String>>() {
                    @Override
                    public Observable<String> call(Note note) {
                        return Observable.just(note.getText());
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "#-----onCompleted---->>>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "#-----onError---->>>");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "#------onNext--->>>" + s);
                    }
                });

    }
}
