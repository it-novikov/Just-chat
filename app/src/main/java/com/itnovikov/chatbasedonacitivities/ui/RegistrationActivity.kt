package com.itnovikov.chatbasedonacitivities.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.BaseActivity
import com.itnovikov.chatbasedonacitivities.databinding.ActivityRegistrationBinding

class RegistrationActivity : BaseActivity() {

    private val binding by lazy { ActivityRegistrationBinding.inflate(layoutInflater) }
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        configureButton()
    }

    private fun observeViewModel() {
        viewModel.getError().observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.getAuthorizedUser().observe(this) {
            if (it != null) {
                startActivity(Intent(this, ChatListActivity::class.java))
                finish()
            }
        }
    }

    private fun configureButton() {
        binding.buttonSignUp.setOnClickListener {
            val mail = binding.editTextRegEmail.text.toString().trim()
            val password = binding.editTextRegPassword.text.toString().trim()
            val firstName = binding.editTextFirstName.text.toString().trim()
            val lastName = binding.editTextSecondName.text.toString().trim()
            viewModel.createAccount(mail, password, firstName, lastName)
        }
    }
}