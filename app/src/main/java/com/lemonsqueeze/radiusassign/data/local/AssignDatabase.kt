package com.lemonsqueeze.radiusassign.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto
import com.lemonsqueeze.radiusassign.utils.DataConverter

@Database(entities = [RemoteDataDto::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AssignDatabase: RoomDatabase() {
    abstract fun dao(): AssignDao

    companion object {
        private var instance: AssignDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AssignDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, AssignDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
    }
}