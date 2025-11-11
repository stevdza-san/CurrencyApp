package domain

import domain.model.Currency
import domain.model.RequestState
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertCurrencyData(currency: Currency)
    fun readCurrencyData(): Flow<RequestState<List<Currency>>>
    suspend fun cleanUp()
}