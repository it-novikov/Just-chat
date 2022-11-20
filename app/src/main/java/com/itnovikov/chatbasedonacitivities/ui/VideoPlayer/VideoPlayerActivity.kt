package com.itnovikov.chatbasedonacitivities.ui.VideoPlayer

import android.content.Context
import android.content.Intent
import android.widget.MediaController
import android.os.Bundle
import androidx.activity.viewModels
import com.itnovikov.chatbasedonacitivities.core.BaseActivity
import com.itnovikov.chatbasedonacitivities.databinding.ActivityVideoPleerBinding

class VideoPlayerActivity : BaseActivity() {

    private val binding by lazy { ActivityVideoPleerBinding.inflate(layoutInflater) }
    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupVideoPlayer()
        setMediaController()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, VideoPlayerActivity::class.java)
        }
    }

    private fun setupVideoPlayer() {
        val path = viewModel.getPath()
        binding.videoViewPlayer.setVideoURI(path)
        binding.videoViewPlayer.start()
    }

    private fun setMediaController() {
        val mediaController = MediaController(this)
        binding.videoViewPlayer.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoViewPlayer)
    }
}