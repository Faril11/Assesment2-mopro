package org.d3if0006.assesment2_mopro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0006.assesment2_mopro.model.Outfits


@Database(entities = [Outfits::class], version = 1, exportSchema = false)
abstract class OutfitsDb : RoomDatabase() {
    abstract val dao: OutfitsDao
    companion object {
        @Volatile
        private var INSTANCE: OutfitsDb? = null
        fun getInstance(context: Context): OutfitsDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OutfitsDb::class.java,
                        "Outfits_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }}