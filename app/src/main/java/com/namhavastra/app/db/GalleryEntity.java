package com.namhavastra.app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gallery")
public class GalleryEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String imagePath;
    public String description;
    public String material;
    public String weaverName;
    public double price;
    public long timestamp;
}
