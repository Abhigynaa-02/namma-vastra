package com.namhavastra.app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GalleryDao {
    @Insert
    void insert(GalleryEntity galleryEntity);

    @Query("SELECT * FROM gallery ORDER BY timestamp DESC")
    List<GalleryEntity> getAllSarees();

    @Query("SELECT COUNT(*) FROM gallery")
    int getCount();
}
