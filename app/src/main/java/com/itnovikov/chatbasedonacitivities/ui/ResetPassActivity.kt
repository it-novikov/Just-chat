package com.itnovikov.chatbasedonacitivities.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.BaseActivity
import com.itnovikov.chatbasedonacitivities.databinding.ActivityResetPassBinding

class ResetPassActivity : BaseActivity() {

    private val binding by lazy { ActivityResetPassBinding.inflate(layoutInflater) }
    private val viewModel: ResetPassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        configureButton()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ResetPassActivity::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.getError().observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun configureButton() {
        binding.buttonResetPassword.setOnClickListener {
            val mail = binding.editTextResetEmail.text.toString().trim()
            viewModel.resetPassword(mail)
            finish()
        }
    }
}