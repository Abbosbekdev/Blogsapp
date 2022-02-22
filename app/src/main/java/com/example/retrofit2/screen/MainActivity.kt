package com.example.retrofit2.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofit2.R
import com.example.retrofit2.adapter.PostAdapter
import com.example.retrofit2.adapter.UserAdapter
import com.example.retrofit2.adapter.UserAdapterListener
import com.example.retrofit2.api.ApiService
import com.example.retrofit2.api.BaseResponse
import com.example.retrofit2.model.PostModel
import com.example.retrofit2.model.UserModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipe.setOnRefreshListener(this)
        swipe.isRefreshing = true
        loadUser()
        loadPost()
    }
    fun loadUser(){
        ApiService.apiClient().getUser().enqueue(object:Callback<BaseResponse<List<UserModel>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<UserModel>>>,
                response: Response<BaseResponse<List<UserModel>>>
            ) {
                swipe.isRefreshing = false
                recyclerUser.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                recyclerUser.adapter = UserAdapter(response.body()?.data?: emptyList(),object :UserAdapterListener{
                    override fun onClick(items: UserModel) {
                        val intent = Intent(this@MainActivity,PostsActivity::class.java)
                        intent.putExtra("extra_data",items)
                        startActivity(intent)
                    }
                })
            }

            override fun onFailure(call: Call<BaseResponse<List<UserModel>>>, t: Throwable) {
                swipe.isRefreshing = false
                Toast.makeText(this@MainActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun loadPost(){
        ApiService.apiClient().getPost().enqueue(object :Callback<BaseResponse<List<PostModel>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<PostModel>>>,
                response: Response<BaseResponse<List<PostModel>>>
            ) {

                recyclerPost.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerPost.adapter = PostAdapter(response.body()?.data?: listOf())
            }

            override fun onFailure(call: Call<BaseResponse<List<PostModel>>>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRefresh() {
        loadUser()
        loadPost()
    }
}