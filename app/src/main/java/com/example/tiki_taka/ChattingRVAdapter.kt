//package com.example.tiki_taka
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.tiki_taka.databinding.ItemChattingFriendBinding
//
//class ChattingRVAdapter(var items: ArrayList<Data_chatting>) :
//    RecyclerView.Adapter<ChattingRVAdapter.Holder>() {
//
//    inner class Holder(val binding: ItemChattingFriendBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = ItemChattingFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.binding.apply {
//            imgChattingFriendProfile.setImageResource(items[position].chatting_img)
//            textChattingFriendName.text = items[position].chatting_name
//            textChattingFriendContent.text = items[position].chatting_content
//        }
//    }
//
//
//}