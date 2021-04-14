package ru.andlancer.todofl

import android.app.Application
import ru.andlancer.todofl.di.component.DaggerAppComponent
import ru.andlancer.todofl.di.module.ContextModule



class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initYandexSDK()
        initDI()

    }

    private fun initYandexSDK(){

    }

    private fun initDI(){
        DI.appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()

    }

    companion object{

    }

}