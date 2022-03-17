package ir.ALIM24.batterymanager.Screens

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.ALIM24.batterymanager.databinding.ActivityBatteryHealthBinding


class BatteryHealth : AppCompatActivity() {

    private lateinit var  binding: ActivityBatteryHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBatteryHealthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerReceiver(
            batteryInfoReceiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
        )
    }

    private var batteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH , 0)
//            val batteryHealthF = batteryHealth.toFloat()
            val healthProgressbarB = binding.healthProgressbar

            //if for change health animation and progressBar animation color

//            if (batteryHealthF > 70){
//
//            }else if(batteryHealthF < 71 && batteryHealthF > 40){
//
//            }else{
//
//            }
            healthProgressbarB.progressMax = 100F
            healthProgressbarB.setProgressWithAnimation(batteryHealth.toFloat() , 3000)
            binding.batteryHealthText.text = "$batteryHealth %"

        }




    }
}