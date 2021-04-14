package ru.andlancer.todofl.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ContextModule @Inject constructor(val context: Context) {

    @Provides
    //@Singleton
    fun context(): Context = context.applicationContext


}