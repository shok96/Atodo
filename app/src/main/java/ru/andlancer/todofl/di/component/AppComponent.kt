package ru.andlancer.todofl.di.component

import android.content.Context

import dagger.Component
import ru.andlancer.todofl.di.module.ContextModule



@Component(modules = [ContextModule::class])
interface AppComponent {

    fun context(): Context


}