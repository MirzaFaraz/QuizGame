package com.example.androiddeveloperassignment_capsuleapp.capsule

import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import com.example.androiddeveloperassignment_capsuleapp.R

class VideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var view: View
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate XML layout
        view = inflater.inflate(R.layout.fragment_video, container, false)

        initViews()
        videoSetup()

        return view
    }

    private fun videoSetup() {
        //Creating MediaController
        val mediaController = MediaController(context)
        mediaController.setAnchorView(videoView)
        //specify the location of media file
        val uri: Uri = parse("android.resource://" + context?.packageName + "/" + R.raw.a)
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
    }

    private fun initViews() {
        videoView = view.findViewById(R.id.videoView)
    }


}