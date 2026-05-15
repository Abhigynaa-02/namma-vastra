package com.namhavastra.app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weavers")
public class WeaverEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String village;
    public String speciality;
    public String story;
    public int yearsExp;
}
