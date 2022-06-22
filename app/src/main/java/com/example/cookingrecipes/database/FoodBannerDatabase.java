package com.example.cookingrecipes.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.cookingrecipes.database.entity.FoodBanner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FoodBanner.class}, version = 1, exportSchema = true)
public abstract class FoodBannerDatabase extends RoomDatabase {

    // foodBannerDao() default by java utk get object FoodBannerDao
    public abstract FoodBannerDao foodBannerDao();

    // Implementasi Singleton
    // (instancenya DBnya 1/akses memorinya 1 aja/biar DB nya ga banyak, ga race condition)
    public static volatile FoodBannerDatabase INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWorker = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FoodBannerDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            // synchronize > lock kelasnya
            synchronized (FoodBannerDatabase.class){
                // Sesudah di lock di pastikan masih blm ada yang buat instancenya
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , FoodBannerDatabase.class, "database_cooking_recipe"
                            ).build();
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
