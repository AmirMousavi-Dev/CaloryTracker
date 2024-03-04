package ir.codroid.tracker_data.mapper

import ir.codroid.tracker_data.remote.dto.ProductDto
import ir.codroid.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun ProductDto.toTrackableFood(): TrackableFood? {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = productName ?: return null,
        carbsPre100g = carbsPer100g,
        proteinPre100g = proteinPer100g,
        fatPre100g = fatPer100g,
        caloriesPre100g = caloriesPer100g,
        imageUrl = imageFrontThumbUrl
    )
}