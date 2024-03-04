package ir.codroid.tracker_domain.model

data class TrackableFood(
    val name: String,
    val imageUrl: String?,
    val caloriesPre100g: Int,
    val carbsPre100g: Int,
    val proteinPre100g: Int,
    val fatPre100g: Int,
)
