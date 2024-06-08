package com.example.suitmedia_mobiletest_xml.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmedia_mobiletest_xml.data.api.ApiService
import com.example.suitmedia_mobiletest_xml.domain.model.User
import com.example.suitmedia_mobiletest_xml.domain.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var currentPage = 1
    private val apiService = ApiService.create()

    init {
        fetchUsers(currentPage)
    }

    fun refresh() {
        currentPage = 1
        fetchUsers(currentPage)
    }

    fun loadMoreData(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++
                    fetchUsers(currentPage)
                }
            }
        })
    }

    private fun fetchUsers(page: Int) {
        apiService.getUsers(page, 12).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val newUsers = response.body()?.data ?: emptyList()
                    if (page == 1) {
                        _users.value = newUsers
                    } else {
                        _users.value = _users.value?.plus(newUsers)
                    }
                } else {
                    _error.value = "Error fetching data"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}
