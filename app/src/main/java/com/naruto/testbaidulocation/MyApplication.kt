package com.naruto.testbaidulocation

import android.app.Application
import com.baidu.location.LocationClient
import com.baidu.mapapi.SDKInitializer

/**
 * @Description
 * @Author Naruto Yang
 * @CreateDate 2024/7/11 星期四
 * @Note
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SDKInitializer.setAgreePrivacy(applicationContext, true)
        SDKInitializer.initialize(applicationContext)
        LocationClient.setAgreePrivacy(true)
    }
}