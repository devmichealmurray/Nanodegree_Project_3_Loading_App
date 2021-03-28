package com.udacity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityMainBinding
import com.udacity.util.Constants
import com.udacity.util.NotificationHelper


private const val TAG = "Main Activity"

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var downloadFileName = "filename"

    private lateinit var binding: ActivityMainBinding
    private var isDownloading: Boolean = false

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        checkPermissions()
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        binding.loadingButton.setOnClickListener { radioGroup() }

    }

    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS.toString(),
                    "/" + "load_app"
                )
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.loadingButton.hasCompletedDownload()
            isDownloading = false
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val cursor =
                downloadManager.query(id?.let { DownloadManager.Query().setFilterById(it) })

            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    context?.let {
                        NotificationHelper
                            .sendNotification(it, downloadFileName, "SUCCESS!")
                    }
                } else {
                    context?.let {
                        NotificationHelper
                            .sendNotification(it, downloadFileName, "FAILED")
                    }
                }

            }
        }
    }


    /**
     *  Finish the permissions functions
     */

    private fun checkPermissions() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()

        }

    }

    private fun requestPermissions() {
        ActivityCompat
            .requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.REQ_CODE_PERMISSION
            )
    }


    private fun radioGroup() {
        if (binding.radioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(R.string.no_selection_warning), Toast.LENGTH_LONG).show()

        } else {
            val selectedOption = binding.radioGroup.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(selectedOption)

            when (radioButton) {
                binding.glideRadioButton -> {
                    downloadFileName = getString(R.string.glide_radio_button_text)
                    download(Constants.GLIDE)
                }
                binding.loadAppRadioButton -> {
                    downloadFileName = getString(R.string.loadApp_radio_button_text)
                    download(Constants.LOAD)
                }
                else -> {
                    downloadFileName = getString(R.string.retrofit_radio_button_text)
                    download(Constants.RETROFIT)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}
