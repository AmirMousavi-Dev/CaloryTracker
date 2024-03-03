package ir.codroid.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.codroid.tracker_data.local.entity.TrackedFoodEntity

@Database(
    entities = [TrackedFoodEntity::class],
    version = 1
)
abstract class TrackerDataBase : RoomDatabase() {
    abstract val trackerDao: TrackerDao
}