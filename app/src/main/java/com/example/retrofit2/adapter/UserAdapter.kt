package com.example.retrofit2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit2.R
import com.example.retrofit2.model.UserModel
import kotlinx.android.synthetic.main.post_item_layout.view.*
import kotlinx.android.synthetic.main.user_item_layout.view.*

interface UserAdapterListener{
    fun onClick(items:UserModel)
}

class UserAdapter(val items : List<UserModel>,val adapterListener: UserAdapterListener):RecyclerView.Adapter<UserAdapter.ItemHolder>(){
    inner class ItemHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener{
            adapterListener.onClick(item)
        }
        holder.itemView.tvName.text = item.firstName
        Glide.with(holder.itemView.context).load(item.picture).into(holder.itemView.imgUser)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}