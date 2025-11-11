package di

import com.russhwolf.settings.Settings
import data.local.PreferencesImpl
import data.local.SqlDelightImpl
import data.remote.api.CurrencyApiServiceImpl
import domain.CurrencyApiService
import domain.LocalRepository
import domain.PreferencesRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.HomeViewModel
import org.koin.core.module.Module

expect fun platformModule(): Module

val appModule = module {
    single { Settings() }
    single<LocalRepository> { SqlDelightImpl(databaseDriverFactory = get()) }
    single<PreferencesRepository> { PreferencesImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferences = get()) }
    factory {
        HomeViewModel(
            preferences = get(),
            localDb = get(),
            api = get()
        )
    }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(appModule, platformModule())
    }
}