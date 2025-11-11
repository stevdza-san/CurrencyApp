package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val meta: MetaData,
    val data: Map<String, CurrencyDto>
)

@Serializable
data class MetaData(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String
)

data class Currency(
    val id: Long = 0L,
    val code: String,
    val value: Double,
)

@Serializable
data class CurrencyDto(
    val code: String,
    val value: Double,
)

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        code = code,
        value = value
    )
}