package ir.codroid.core.domain.model

sealed class GoalType(val name: String) {
    data object LoseWeight : GoalType("lose_weight")
    data object KeepWeight : GoalType("keep_weight")
    data object GainWeight : GoalType("gain_weight")

    companion object {
        fun fromString(name: String): GoalType = when (name) {
            "lose_weight" -> LoseWeight
            "keep_weight" -> KeepWeight
            "gain_weight" -> GainWeight
            else -> KeepWeight
        }
    }
}