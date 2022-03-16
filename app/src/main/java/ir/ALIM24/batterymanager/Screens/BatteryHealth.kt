package ir.ALIM24.batterymanager.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.ALIM24.batterymanager.R
import ir.ALIM24.batterymanager.databinding.ActivityBatteryHealthBinding

class BatteryHealth : AppCompatActivity() {

    private lateinit var  binding: ActivityBatteryHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBatteryHealthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}