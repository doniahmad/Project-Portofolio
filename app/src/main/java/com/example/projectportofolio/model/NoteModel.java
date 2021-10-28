package com.example.projectportofolio.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NoteModel extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String titleNote,descNote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    public String getDescNote() {
        return descNote;
    }

    public void setDescNote(String descNote) {
        this.descNote = descNote;
    }
}
