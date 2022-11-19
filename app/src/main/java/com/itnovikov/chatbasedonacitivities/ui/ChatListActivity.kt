package com.itnovikov.chatbasedonacitivities.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.itnovikov.chatbasedonacitivities.BaseActivity
import com.itnovikov.chatbasedonacitivities.R
import com.itnovikov.chatbasedonacitivities.databinding.ActivityChatListBinding

class ChatListActivity : BaseActivity() {

    private val binding by lazy { ActivityChatListBinding.inflate(layoutInflater) }
    private val viewModel: ChatListViewModel by viewModels()
    private val adapter = ChatListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRV()
        observeViewModel()
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

    private fun initRV() {
        binding.recyclerViewChatList.adapter = adapter
        initCallbacks()
    }

    private fun initCallbacks() {

    }

    private fun observeViewModel() {
        viewModel.getAuthorizedUser().observe(this) {
            if (it == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.getUsers().observe(this) {
            adapter.submitList(it)
        }
    }
}