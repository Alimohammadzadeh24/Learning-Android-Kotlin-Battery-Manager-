package ir.ALIM24.batterymanager.Screens

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ir.ALIM24.batterymanager.Utils.BatteryUsage
import ir.ALIM24.batterymanager.Model.BatteryModel
import ir.ALIM24.batterymanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val batteryUsage = BatteryUsage(this)

        val batteryPercentArray: MutableList<BatteryModel> = ArrayList()

        for (item in batteryUsage.getUsageStateList()) {
            if (item.totalTimeInForeground > 0) {
                val bm = BatteryModel()
                bm.packageName = item.packageName
                bm.percentUsage = (
                        item.totalTimeInForeground
                        .toFloat() / batteryUsage.getTotalTime()
                        .toFloat() * 100
                ).toInt()
                batteryPercentArray += bm
            }
        }

        val sortedList = batteryPercentArray.sortedWith(compareBy{it.percentUsage}).reversed()

        for (item in sortedList){
            Log.e("3636",item.packageName + " : " + item.percentUsage)
        }

        registerReceiver(
            batteryInfoReceiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
        )

        binding.btnCheck.setOnClickListener {
            startActivity(Intent(this@MainActivity,BatteryHealth::class.java))
        }

    }


    private var batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            val batteryPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            val batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH , 0)
            binding.tempText.text =
                (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10).toString() + " °C"
            binding.volText.text =
                (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000).toString() + " volt"
            if (batteryPlugged == 0) {
                binding.plugText.text = "plug-out"
            } else {
                binding.plugText.text = "plug-in"
            }
            binding.techText.text =
                intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)


            binding.circularProgressBar.progressMax = 100F
            binding.circularProgressBar.setProgressWithAnimation(batteryLevel.toFloat(), 2000)
            binding.batteryLevelText.text = "$batteryLevel %"
        }


    }

}