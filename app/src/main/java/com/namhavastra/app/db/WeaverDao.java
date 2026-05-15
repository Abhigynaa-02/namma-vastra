package com.namhavastra.app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WeaverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeaverEntity weaver);

    @Query("SELECT * FROM weavers LIMIT 1")
    WeaverEntity getProfile();

    @Query("DELETE FROM weavers")
    void deleteAll();
}
