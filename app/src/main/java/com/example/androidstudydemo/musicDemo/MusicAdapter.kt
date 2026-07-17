package com.example.androidstudydemo.musicDemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudydemo.databinding.ItemMusicBinding

class MusicAdapter: RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private val musicList= mutableListOf<MusicInfo>()
    //parent就是item的父容器，即RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMusicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
//绑定数据到item视图（覆盖旧数据，列表滑动时会频繁调用
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(musicList[position])
    }

    override fun getItemCount()= musicList.size
//ViewBinding 会把布局里所有带 id 的控件直接封装到 binding 对象中，不用再写 findViewById,,,,binding.root = item_music.xml 的根布局 View，传递给父类 ViewHolder
    class ViewHolder(private val binding: ItemMusicBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(musicInfo: MusicInfo) {
            binding.tvTitle.text = musicInfo.title
            binding.tvArtist.text = musicInfo.artist
            binding.tvDuration.text = formatDuration(musicInfo.duration)
        }
    private fun formatDuration(duration: Long): String {
        val minutes = duration / 60000
        val seconds = (duration % 60000) / 1000
        //minutes = 3，seconds = 5 → 03:05
        return String.format("%02d:%02d", minutes, seconds)
    }

    }

    fun updateMusicList(newMusicList: List<MusicInfo>) {
        musicList.clear()
        musicList.addAll(newMusicList)
        notifyDataSetChanged()
    }

}