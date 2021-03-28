package com.udacity.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.udacity.R
import com.udacity.databinding.ActivityDetailBinding
import com.udacity.util.Constants

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fileNameTextView.text = intent.getStringExtra("FILE_NAME")
        binding.statusTextView.text = intent.getStringExtra("STATUS")
        binding.statusTextView.setTextColor(
            if (intent.getStringExtra("STATUS") == Constants.SUCCESS) Color.GREEN else Color.RED)

        binding.okayButton.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.cancel(1)
    }
}