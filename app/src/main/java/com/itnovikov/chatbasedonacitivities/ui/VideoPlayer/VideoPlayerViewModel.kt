package com.itnovikov.chatbasedonacitivities.ui.VideoPlayer

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel

class VideoPlayerViewModel(application: Application) : AndroidViewModel(application) {

    fun getPath(): Uri {
        val onlinePath = "https://clck.ru/32j8ts"
        return Uri.parse(onlinePath)
    }
}