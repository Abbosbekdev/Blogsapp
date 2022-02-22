package com.example.retrofit2.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofit2.R
import com.example.retrofit2.adapter.PostAdapter
import com.example.retrofit2.api.Api
import com.example.retrofit2.api.ApiService
import com.example.retrofit2.api.BaseResponse
import com.example.retrofit2.model.PostModel
import com.example.retrofit2.model.UserModel
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {
    lateinit var user : UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        user = intent.getSerializableExtra("extra_data") as UserModel
        tvTitle.text = user.firstName+" "+user.lastName
        swipe.setOnRefreshListener(this)

        loadPost()
    }

    override fun onRefresh() {
        loadPost()
    }
    fun loadPost(){
        swipe.isRefreshing = true
        ApiService.apiClient().getPostByUser(user.id).enqueue(object : Callback<BaseResponse<List<PostModel>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {
                swipe.isRefreshing = false
                recyclerPost.layoutManager = LinearLayoutManager(this@PostsActivity)
                recyclerPost.adapter = PostAdapter(response.body()?.data?: listOf())
            }

            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {
                swipe.isRefreshing = false
                Toast.makeText(this@PostsActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        })
    }
}