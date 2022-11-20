package com.itnovikov.chatbasedonacitivities.ui.Chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.core.BaseActivity
import com.itnovikov.chatbasedonacitivities.data.Message
import com.itnovikov.chatbasedonacitivities.databinding.ActivityChatBinding

class ChatActivity : BaseActivity() {

    private lateinit var currentUserId: String
    private lateinit var incomingUserId: String

    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(application, currentUserId, incomingUserId)
    }
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID).toString()
        incomingUserId = intent.getStringExtra(EXTRA_INCOMING_USER_ID).toString()
        initRV()
        observeViewModel()
        configureButton()
        checkEditTextSendMessage()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setNetworkStatus(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setNetworkStatus(false)
    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "current_id"
        private const val EXTRA_INCOMING_USER_ID = "incoming_id"

        fun newIntent(context: Context, currentUserId: String, incomingUserId: String): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            intent.putExtra(EXTRA_INCOMING_USER_ID, incomingUserId)
            return intent
        }
    }

    private fun initRV() {
        adapter = ChatAdapter(currentUserId)
        binding.recyclerViewChat.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getIncomingUser().observe(this) {
            val name = it.name
            val networkStatus = if (it.online) {
                "Online"
            } else {
                "Offline"
            }
            supportActionBar?.title = "$name [$networkStatus]"
        }

        viewModel.getIsSendMessage().observe(this) {
            if (it) {
                binding.editTextSendMessage.setText("")
            }
        }

        viewModel.getError().observe(this) {
            if (it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.getMessageList().observe(this) {
            adapter.submitList(it)
        }
    }

    private fun configureButton() {
        binding.imageViewSendIcon.setOnClickListener {
            val text = binding.editTextSendMessage.text.toString().trim()
            val message = Message(text, currentUserId, incomingUserId)
            viewModel.sendMessage(message)
        }
    }

    private fun checkEditTextSendMessage() {
        binding.editTextSendMessage.addTextChangedListener {
            if (binding.editTextSendMessage.text.isEmpty()) {
                binding.imageViewSendIcon.visibility = View.INVISIBLE
            } else {
                binding.imageViewSendIcon.visibility = View.VISIBLE
            }
        }
    }
}