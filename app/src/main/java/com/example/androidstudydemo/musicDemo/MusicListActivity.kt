package com.example.androidstudydemo.musicDemo

import android.Manifest
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityMusicListBinding
import com.permissionx.guolindev.PermissionX
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class MusicListActivity : AppCompatActivity() {
    private val musicAdapter = MusicAdapter()
    private lateinit var binding: ActivityMusicListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //music_list.xml 里 id = musicList 的 RecyclerView
        binding.musicList.adapter = musicAdapter
        requestPermissions()
    }

    private fun requestPermissions(){
        val permission=if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        PermissionX.init(this)
            .permissions(permission)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted){
                    loadMusicList()
                }else{
                    Toasty.error(this, "Permission denied权限申请失败", Toasty.LENGTH_SHORT)
                }
        }


    }

    private fun loadMusicList() {
        thread{
            val musicList=queryMusicList()
            musicList.add(  MusicInfo(1,"测试歌曲","测试歌手",120000,1024,"/storage/test.mp3"))
            musicList.add(  MusicInfo(2,"测试歌曲2","测试歌手2",180000,1024,"/storage/test1.mp3"))
            runOnUiThread{
                musicAdapter.updateMusicList(musicList)
            }
        }

    }

    private fun queryMusicList(): MutableList<MusicInfo> {
        val musicList = mutableListOf<MusicInfo>()
//        //查询音乐时长大于1min的歌曲
//        val selection = "${MediaStore.Audio.Media.DURATION} > ?"
//        val selectionArgs = arrayOf("60000")
        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA
            ),
            null,
            null,
            null
        )?.use { cursor ->

                val idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val sizeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
                val dataIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idIndex)
                    val title = cursor.getString(titleIndex)
                    val artist = cursor.getString(artistIndex)
                    val duration = cursor.getLong(durationIndex)
                    val size = cursor.getLong(sizeIndex)
                    val data = cursor.getString(dataIndex)
                    musicList.add(MusicInfo(id, title, artist, duration, size, data))
                }

        }

        return musicList
    }


}