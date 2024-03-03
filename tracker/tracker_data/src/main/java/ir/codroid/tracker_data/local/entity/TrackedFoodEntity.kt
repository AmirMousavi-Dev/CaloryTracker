package ir.codroid.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trackedFoodEntity")
data class TrackedFoodEntity(
    val name: String,
    val carb: Int,
    val protein: Int,
    val fat: Int,
    val imgUrl: String?,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int,
    @PrimaryKey val id: Int? = null
)
