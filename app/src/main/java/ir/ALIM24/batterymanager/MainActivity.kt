package ir.ALIM24.batterymanager

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ir.ALIM24.batterymanager.databinding.ActivityMainBinding
import ir.ALIM24.batterymanager.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerReceiver(
            batteryInfoReceiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
        )

    }

    private var batteryInfoReceiver: BroadcastReceiver = object  : BroadcastReceiver(){
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent){
            val batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL , 0)
            val batteryPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED , 0)
            binding.tempText.text =
                (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE , 0)/10).toString() + " °C"
            binding.volText.text =
                (intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE , 0)/1000).toString() + " volt"
            if (batteryPlugged == 0 ){
                binding.plugText.text = "plug-out"
            }else{
                binding.plugText.text = "plug-in"
            }
            binding.techText.text =
                intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)


            binding.circularProgressBar.progressMax = 100F
            binding.circularProgressBar.setProgressWithAnimation(batteryLevel.toFloat() , 2000)
            binding.btlevelText.text = "$batteryLevel %"
        }
    }

}