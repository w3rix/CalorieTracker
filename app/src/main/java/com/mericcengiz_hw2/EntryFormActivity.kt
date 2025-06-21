package com.mericcengiz_hw2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mericcengiz_hw2.Database.Entry
import com.mericcengiz_hw2.Database.EntryRoomDatabase
import com.mericcengiz_hw2.databinding.ActivityEntryFormBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryFormBinding
    private lateinit var db: EntryRoomDatabase
    private var entryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = EntryRoomDatabase.getDatabase(this)

        entryId = intent.getIntExtra("entry_id", -1).takeIf { it != -1 }
        if (entryId != null) {
            loadEntryForEdit(entryId!!)
        }


        val types = resources.getStringArray(R.array.entry_types).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
        binding.typeSpinner.adapter = adapter

        binding.saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (entryId != null) {
                    updateEntry()
                } else {
                    saveEntry()
                }
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun loadEntryForEdit(id: Int) {
        val entry = db.entryDao().getById(id)

        runOnUiThread {
            binding.titleEditText.setText(entry.title)
            binding.caloriesEditText.setText(entry.calories.toString())
            binding.dateEditText.setText(entry.date)

            val types = resources.getStringArray(R.array.entry_types)
            val index = types.indexOf(entry.type)
            if (index >= 0) {
                binding.typeSpinner.setSelection(index)
            }
        }
    }


    private fun saveEntry() {
        val type = binding.typeSpinner.selectedItem.toString()
        val title = binding.titleEditText.text.toString().trim()
        val caloriesText = binding.caloriesEditText.text.toString().trim()
        val date = binding.dateEditText.text.toString().trim()

        if (title.isEmpty() || caloriesText.isEmpty() || date.isEmpty()) {
            runOnUiThread {
                Toast.makeText(this, "Please fill all the blanks!", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val calories = caloriesText.toIntOrNull()
        if (calories == null || calories <= 0) {
            runOnUiThread {
                Toast.makeText(this, "Invalid interval of calories!", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val entry = Entry(
            id = 0,
            type = type,
            title = title,
            calories = calories,
            date = date
        )

        db.entryDao().insert(entry)

        runOnUiThread {
            Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateEntry() {
        val title = binding.titleEditText.text.toString().trim()
        val caloriesText = binding.caloriesEditText.text.toString().trim()
        val date = binding.dateEditText.text.toString().trim()
        val type = binding.typeSpinner.selectedItem.toString()

        val calories = caloriesText.toIntOrNull()

        if (title.isEmpty() || calories == null || calories <= 0 || date.isEmpty()) {
            runOnUiThread {
                Toast.makeText(this, "Please fill all the blanks!", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val updatedEntry = Entry(
            id = entryId!!,
            title = title,
            calories = calories,
            date = date,
            type = type
        )

        db.entryDao().update(updatedEntry)

        runOnUiThread {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
