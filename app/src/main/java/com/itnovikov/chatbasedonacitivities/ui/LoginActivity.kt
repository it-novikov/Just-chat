package com.itnovikov.chatbasedonacitivities.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.BaseActivity
import com.itnovikov.chatbasedonacitivities.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        configureButtons()
    }

    private fun observeViewModel() {
        viewModel.getError().observe(this) {
            if (it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.getUser().observe(this) {
            if (it != null) {
                startActivity(Intent(this, ChatListActivity::class.java))
                finish()
            }
        }
    }

    private fun configureButtons() {
        binding.buttonSignIn.setOnClickListener {
            val mail = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            viewModel.signIn(mail, password)
        }

        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.buttonForgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPassActivity::class.java))
        }
    }
}