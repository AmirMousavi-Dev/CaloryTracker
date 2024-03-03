package ir.codroid.core.domain.model

sealed class Gender(val name: String) {
    data object Male : Gender("male")
    data object Female : Gender("female")

    companion object{
        fun fromString(name: String) : Gender = when(name) {
            "male" -> Male
            "female" -> Female
            else -> Male
        }
    }
}