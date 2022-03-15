package ir.ALIM24.batterymanager.Utils

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.util.*

class BatteryUsage(context: Context) {

    private val mycontext = context

    init {
        //Check if permission enable
        if (getUsageStateList().isEmpty()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)
        }
    }

    fun getUsageStateList(): List<UsageStats> {

        val usm = getUsageStateManager(mycontext)
        val calender = Calendar.getInstance()
        val endTime = calender.timeInMillis
        calender.add(Calendar.YEAR, -1)
        val startTime = calender.timeInMillis

        return usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)

    }

    fun getTotalTime() : Long {
        var totalTime: Long = 0
        for (item in getUsageStateList()) {
            totalTime += item.totalTimeInForeground
        }
        return totalTime
    }

    private fun getUsageStateManager(context: Context): UsageStatsManager {
        return context.getSystemService("usagestats") as UsageStatsManager
    }

}