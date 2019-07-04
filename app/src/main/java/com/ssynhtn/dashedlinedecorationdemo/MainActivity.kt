package com.ssynhtn.dashedlinedecorationdemo

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssynhtn.library.DashedLineItemDecoration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TextAdapter("hello world this is a test", R.layout.item)
        adapter.setListener { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        recyclerView.adapter = adapter;

        recyclerView.addItemDecoration(DashedLineItemDecoration(recyclerView, Gravity.START,
            dp2px(this, 5f),
            dp2px(this, 5f),
            dp2px(this, 2f),
            dp2px(this, 20f),
            Color.GRAY, true))

    }

    private fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale
    }
}
