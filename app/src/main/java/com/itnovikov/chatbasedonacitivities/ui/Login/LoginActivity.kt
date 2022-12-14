package com.itnovikov.chatbasedonacitivities.ui.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.core.BaseActivity
import com.itnovikov.chatbasedonacitivities.databinding.ActivityLoginBinding
import com.itnovikov.chatbasedonacitivities.ui.ChatList.ChatListActivity
import com.itnovikov.chatbasedonacitivities.ui.Registration.RegistrationActivity
import com.itnovikov.chatbasedonacitivities.ui.ResetPass.ResetPassActivity

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        configureButtons()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.getError().observe(this) {
            if (it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.getUser().observe(this) {
            if (it != null) {
                startActivity(ChatListActivity.newIntent(this, it.uid))
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
            startActivity(RegistrationActivity.newIntent(this))
        }

        binding.buttonForgotPassword.setOnClickListener {
            startActivity(ResetPassActivity.newIntent(this))
        }
    }
}