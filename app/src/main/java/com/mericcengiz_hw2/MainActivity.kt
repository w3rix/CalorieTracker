package com.mericcengiz_hw2

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mericcengiz.hw2.adapter.CustomRecyclerViewAdapter
import com.mericcengiz_hw2.databinding.ActivityMainBinding
import com.mericcengiz_hw2.Database.Entry
import com.mericcengiz_hw2.Database.EntryRoomDatabase

class MainActivity : AppCompatActivity(), CustomRecyclerViewAdapter.OnEntryClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var db: EntryRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EntryRoomDatabase.getDatabase(this)

        adapter = CustomRecyclerViewAdapter(emptyList(), this)
        binding.entryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.entryRecyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            val intent = Intent(this, EntryFormActivity::class.java)
            startActivity(intent)
        }
    }



    private fun addDummyDataIfNeeded() {
        val entries = db.entryDao().getAllOnce()
        if (entries.isEmpty()) {
            val dummyList = listOf(
                Entry(0, "meal", "Yulaf Ezmesi", 250, "2025-05-12"),
                Entry(0, "meal", "Sebzeli Omlet", 300, "2025-05-12"),
                Entry(0, "exercise", "Koşu", 350, "2025-05-12"),
                Entry(0, "exercise", "Yoga", 150, "2025-05-12")
            )
            dummyList.forEach { db.entryDao().insert(it) }
        }
    }


    private fun getData(){
        val data= EntryRoomDatabase.getDatabase(this).entryDao().getAllEntries()
        adapter.setData(data)
    }

    override fun onResume() {
        super.onResume()
        val entries = db.entryDao().getAllOnce()
        adapter.updateData(entries)
    }

    override fun onItemClick(entry: Entry) {
    }
}
