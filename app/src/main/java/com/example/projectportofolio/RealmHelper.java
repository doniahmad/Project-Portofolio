package com.example.projectportofolio;

import android.util.Log;

import com.example.projectportofolio.model.NoteModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void save(final NoteModel noteModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(NoteModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    noteModel.setId(nextId);
                    NoteModel model = realm.copyToRealm(noteModel);
                }

            }
        });
    }

    // untuk memanggil semua data
    public List<NoteModel> getAllDatabase(){
        RealmResults<NoteModel> results = realm.where(NoteModel.class).findAll();
        return results;
    }

    // untuk meng-update data
    public void update(final Integer id, final String title, final String desc){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NoteModel model = realm.where(NoteModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setTitleNote(title);
                model.setDescNote(desc);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("succes", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    // untuk menghapus data
    public void delete(Integer id){
        final RealmResults<NoteModel> model = realm.where(NoteModel.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteAllFromRealm();
            }
        });
    }
}
