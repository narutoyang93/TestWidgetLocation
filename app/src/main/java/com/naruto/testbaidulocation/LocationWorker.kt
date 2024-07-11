package com.naruto.testbaidulocation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

/**
 * @Description
 * @Author Naruto Yang
 * @CreateDate 2024/7/11 星期四
 * @Note
 */
private const val TAG = "LocationWorker"

class LocationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val client = LocationClient(applicationContext)
        client.locOption = LocationClientOption()
        client.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(result: BDLocation) {
                client.stop()
                result.run {
                    Log.i(TAG, "onReceiveLocation: 定位成功：经纬度=($latitude, $longitude)")
                    toast("定位成功：经纬度=($latitude, $longitude)")
                }
            }

            override fun onLocDiagnosticMessage(p0: Int, p1: Int, p2: String?) {
                super.onLocDiagnosticMessage(p0, p1, p2)
                client.stop()
                val msg = "定位失败：locType=$p0;diagnosticType=$p1;diagnosticMessage=$p2"
                Log.e(TAG, "onLocDiagnosticMessage: $msg")
                toast(msg)
            }
        })
        client.start()
        return Result.success()
    }

    private fun toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun launch(context: Context) {
            val request = OneTimeWorkRequest.from(LocationWorker::class.java)
            WorkManager.getInstance(context.applicationContext).enqueue(request)
        }
    }
}