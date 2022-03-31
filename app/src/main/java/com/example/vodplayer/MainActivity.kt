package com.example.vodplayer

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.VideoView
import androidx.work.Data
import com.example.vodplayer.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.start
import java.net.URI

private lateinit var binding: ActivityMainBinding
private lateinit var videoView: VideoView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.videoView
        binding.button77.setOnClickListener{
            val intent = Intent()
            intent.setType("video/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, 101)
        }
    }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (requestCode == 101) {

                    var uri:Uri? = data.data
                    var selectedImage: String? = uri?.let { getPath(it) }
                    if(selectedImage!= null){
                        videoView.setVideoPath(selectedImage)
                        var mediaController = MediaController(this)
                        videoView.setMediaController(mediaController)
                        videoView.start()
                    }

                }
            }
        }

    private fun getPath(uri: Uri): String {
        var projectionArray = arrayOf(MediaStore.Video.Media.DATA)

        var cursor:Cursor? = applicationContext.contentResolver.query(uri,projectionArray,null,null,null)

        if(cursor!=null)
        {
            val columnIndex:Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        else{
            return ""
        }

    }

}