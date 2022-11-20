package com.itnovikov.chatbasedonacitivities.ui.ChatList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itnovikov.chatbasedonacitivities.core.BaseActivity
import com.itnovikov.chatbasedonacitivities.R
import com.itnovikov.chatbasedonacitivities.databinding.ActivityChatListBinding
import com.itnovikov.chatbasedonacitivities.ui.Chat.ChatActivity
import com.itnovikov.chatbasedonacitivities.ui.Login.LoginActivity
import com.itnovikov.chatbasedonacitivities.ui.VideoPlayer.VideoPlayerActivity

class ChatListActivity : BaseActivity() {

    private lateinit var currentUserId: String

    private val binding by lazy { ActivityChatListBinding.inflate(layoutInflater) }
    private val viewModel: ChatListViewModel by viewModels()
    private val adapter = ChatListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID).toString()
        initRV()
        observeViewModel()
        configureButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_signOut) {
            viewModel.signOut()
        }
        return super.onOptionsItemSelected(item)
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

        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, ChatListActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            return intent
        }
    }

    private fun initRV() {
        binding.recyclerViewChatList.adapter = adapter
        initCallbacks()
    }

    private fun initCallbacks() {
        adapter.setOnItemClick {
            startActivity(ChatActivity.newIntent(this, currentUserId, it.id))
        }
    }

    private fun observeViewModel() {
        viewModel.getAuthorizedUser().observe(this) {
            if (it == null) {
                startActivity(LoginActivity.newIntent(this))
                finish()
            }
        }

        viewModel.getUsers().observe(this) {
            adapter.submitList(it)
        }

        viewModel.getError().observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun configureButton() {
        binding.buttonPlayVideo.setOnClickListener {
            startActivity(VideoPlayerActivity.newIntent(this))
        }
    }
}