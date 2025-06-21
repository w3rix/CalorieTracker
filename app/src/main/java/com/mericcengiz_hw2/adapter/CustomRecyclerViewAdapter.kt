package com.mericcengiz.hw2.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mericcengiz_hw2.databinding.ItemExerciseBinding
import com.mericcengiz_hw2.databinding.ItemMealBinding
import com.mericcengiz_hw2.Database.Entry
import com.mericcengiz_hw2.Database.EntryRoomDatabase
import com.mericcengiz_hw2.EntryFormActivity
import com.mericcengiz_hw2.MainActivity

class CustomRecyclerViewAdapter(
    private var entries: List<Entry>,
    private val listener: OnEntryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_MEAL = 0
        private const val TYPE_EXERCISE = 1
    }

    fun setData(items: List<Entry>) {
        entries = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (entries[position].type == "Add Meal") TYPE_MEAL else TYPE_EXERCISE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MEAL) {
            val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MealViewHolder(binding)
        } else {
            val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExerciseViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry = entries[position]
        if (holder is MealViewHolder) {
            holder.bind(entry)
        } else if (holder is ExerciseViewHolder) {
            holder.bind(entry)
        }
    }

    override fun getItemCount() = entries.size

    fun updateData(newEntries: List<Entry>) {
        entries = newEntries
        notifyDataSetChanged()
    }

    inner class MealViewHolder(private val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            binding.mealTitle.text = entry.title
            binding.mealCalories.text = "${entry.calories} kcal"

            binding.root.setOnClickListener {
                listener.onItemClick(entry)
            }

            binding.editButton.setOnClickListener {
                val intent = Intent(binding.root.context , EntryFormActivity::class.java)
                intent.putExtra("entry_id", entry.id)
                binding.root.context.startActivity(intent)
            }

            binding.root.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle(entry.title)
                    .setMessage(
                        "Calories: ${entry.calories} kcal\n" +
                                "Date: ${entry.date}\n" +
                                "Type: ${entry.type}"
                    )
                    .setPositiveButton("OKAY", null)
                    .show()
            }

            binding.deleteButton.setOnClickListener {
                val db = EntryRoomDatabase.getDatabase(binding.root.context)
                db.entryDao().delete(entry)

                val updatedList = db.entryDao().getAllOnce()

                (binding.root.context as? Activity)?.runOnUiThread {
                    updateData(updatedList)
                }
            }


        }
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            binding.exerciseTitle.text = entry.title
            binding.exerciseCalories.text = "${entry.calories} kcal"

            binding.root.setOnClickListener {
                listener.onItemClick(entry)
            }

            binding.editButton.setOnClickListener {
                val intent = Intent(binding.root.context , EntryFormActivity::class.java)
                intent.putExtra("entry_id", entry.id)
                binding.root.context.startActivity(intent)
            }

            binding.root.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle("${entry.type} name: " + entry.title)
                    .setMessage(
                        "Calories: ${entry.calories} kcal\n" +
                                "Date: ${entry.date}\n" +
                                "Type: ${entry.type}"
                    )
                    .setPositiveButton("OKAY", null)
                    .show()
            }

            binding.deleteButton.setOnClickListener {
                val db = EntryRoomDatabase.getDatabase(binding.root.context)
                db.entryDao().delete(entry)

                val updatedList = db.entryDao().getAllOnce()

                (binding.root.context as? Activity)?.runOnUiThread {
                    updateData(updatedList)
                }
            }


        }
    }

    interface OnEntryClickListener {
        fun onItemClick(entry: Entry)
    }
}
