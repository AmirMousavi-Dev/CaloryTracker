package ir.codroid.core.domain.use_case

class FilterOutDigitsUseCase {
    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}