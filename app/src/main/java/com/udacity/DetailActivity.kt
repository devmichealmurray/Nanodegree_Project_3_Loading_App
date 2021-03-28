package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.AppBarConfiguration
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fileName = intent.getStringExtra("FILE_NAME")
        val status = intent.getStringExtra("STATUS")

        binding.fileNameTextView.text = fileName.toString()
        binding.statusTextView.text = status.toString()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.cancel(1)
    }
}