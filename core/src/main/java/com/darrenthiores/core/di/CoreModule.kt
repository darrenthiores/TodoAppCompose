package com.darrenthiores.core.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.darrenthiores.core.data.local.TodoDb
import com.darrenthiores.core.BuildConfig
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.local.TodoDao
import com.darrenthiores.core.data.repository.ITodoRepository
import com.darrenthiores.core.data.repository.TodoRepository
import com.darrenthiores.core.model.data.TodoEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.concurrent.Executors

val databaseModule = module {

    factory { get<TodoDb>().todoDao() }

    single {

        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSWORD.toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            TodoDb::class.java,
            "Movie.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                fillWithStartingData(get())
            }
        })
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    }

}

fun fillWithStartingData(todoDao: TodoDao) {

    Executors.newSingleThreadExecutor().execute {
        todoDao.preInsert(
            TodoEntity(
                title = "This is sample 1",
                description = "This is just a sample, delete or update it"
            ),
            TodoEntity(
                title = "This is sample 2",
                description = "This is just a sample, delete or update it"
            ),
            TodoEntity(
                title = "This is sample 3",
                description = "This is just a sample, delete or update it"
            )
        )
    }

}

val repositoryModule = module {

    single { LocalDataSource(get()) }

    single<ITodoRepository> { TodoRepository(get()) }

}