package com.example.restapi

import android.app.Application
import com.example.restapi.repository.AppContainer
import com.example.restapi.repository.KontakContainer

class KontakAplication  :  Application(){
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KontakContainer()
    }
}