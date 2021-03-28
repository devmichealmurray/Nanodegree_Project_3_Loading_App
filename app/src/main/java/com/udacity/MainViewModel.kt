package com.udacity

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val _downloadRequest by lazy { MutableLiveData<DownloadManager.Request>() }
    val downloadRequest: LiveData<DownloadManager.Request> get() = _downloadRequest

}