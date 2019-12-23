package com.androidkamallib.libapplication.view.dashboard.ui.uv_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidkamallib.libapplication.R

class UVIndexFragment : Fragment() {

    private lateinit var galleryViewModel: UVIndexViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(UVIndexViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_uvindex, container, false)
        val textView: TextView = root.findViewById(R.id.text_uvindex)
        galleryViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}