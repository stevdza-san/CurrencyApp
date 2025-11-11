package data.local

import app.cash.sqldelight.Query
import com.stevdza_san.CurrencyDatabase
import domain.LocalRepository
import domain.model.Currency
import domain.model.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SqlDelightImpl(
    databaseDriverFactory: DatabaseDriverFactory,
) : LocalRepository {

    private val database = CurrencyDatabase(databaseDriverFactory.createDriver())
    private val queries = database.currencyDatabaseQueries

    override suspend fun insertCurrencyData(currency: Currency) {
        withContext(Dispatchers.IO) {
            queries.insertOrReplaceCurrency(
                code = currency.code,
                rate = currency.value
            )
        }
    }

    override fun readCurrencyData(): Flow<RequestState<List<Currency>>> = callbackFlow {
        val listener = Query.Listener {
            try {
                val currencyList = queries.getAllCurrencies().executeAsList()

                val currencies = currencyList.map { currencyTable ->
                    Currency(
                        id = currencyTable.id,
                        code = currencyTable.code,
                        value = currencyTable.rate
                    )
                }
                trySend(RequestState.Success(data = currencies))
            } catch (exception: Exception) {
                trySend(RequestState.Error(message = exception.message ?: "Unknown error"))
            }
        }

        val query = queries.getAllCurrencies()
        query.addListener(listener)
        listener.queryResultsChanged() // Emit initial value

        awaitClose {
            query.removeListener(listener)
        }
    }

    override suspend fun cleanUp() {
        withContext(Dispatchers.IO) {
            queries.deleteAllCurrencies()
        }
    }
}
