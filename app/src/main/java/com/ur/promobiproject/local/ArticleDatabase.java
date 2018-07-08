package com.ur.promobiproject.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Ishan on 07-07-2018.
 */

@Database(entities = {ArticleEntity.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDAO articleDAO();
}
