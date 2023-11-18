package com.pisces.flex

import android.app.Application
import com.pisces.litho.LithoBuildTool

class FlexApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        LithoBuildTool.init(this)
    }
}