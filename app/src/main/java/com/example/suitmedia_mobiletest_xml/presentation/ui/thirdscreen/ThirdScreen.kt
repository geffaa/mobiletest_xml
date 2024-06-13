package com.example.suitmedia_mobiletest_xml.presentation.ui.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia_mobiletest_xml.R
import com.example.suitmedia_mobiletest_xml.presentation.ui.UserAdapter
import com.example.suitmedia_mobiletest_xml.presentation.viewmodel.UserViewModel
import com.example.suitmedia_mobiletest_xml.presentation.ui.utils.DividerItemDecoration

class ThirdScreen : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        userAdapter = UserAdapter { user ->
            val intent = Intent()
            intent.putExtra("SELECTED_USER", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, intent)
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        recyclerView.addItemDecoration(DividerItemDecoration(this))

        userViewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
        }

        userViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayout.setOnRefreshListener {
            userViewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        userViewModel.loadMoreData(recyclerView)
    }
}