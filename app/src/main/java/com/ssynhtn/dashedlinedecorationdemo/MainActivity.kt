package com.ssynhtn.dashedlinedecorationdemo

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
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

        val divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.list_divider_1dp)?.let { divider.setDrawable(it) }
        recyclerView.addItemDecoration(divider)

    }

    private fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale
    }
}
