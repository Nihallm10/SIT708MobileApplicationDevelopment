package com.example.task_manager_app_41p

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskDetailsRecycler: RecyclerView
    private lateinit var addTaskAdapter: AddTaskAdapter
    private val addanewtaskList = ArrayList<Addanewtask>()
    private lateinit var addButton: Button
    private lateinit var database: Database
    private val OVERLAY_PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Database(this)
        taskDetailsRecycler = findViewById(R.id.all_task_recycler)
        addButton = findViewById(R.id.add_task_button)
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = RecyclerView.VERTICAL
        taskDetailsRecycler.layoutManager = layoutManager

        addButton.setOnClickListener {
            openAddTaskDialog()
        }

        addTaskAdapter = AddTaskAdapter(applicationContext, addanewtaskList, this)
        taskDetailsRecycler.adapter = addTaskAdapter
        refreshRecyclerView()

        checkOverlayPermission() // Check overlay permission here

        // Sort button setup
        val sortButton = findViewById<Button>(R.id.sort_button)
        sortButton.setOnClickListener {
            sortByDate()
        }
    }

    private fun openAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_task_view, null)

        val taskTitle = view.findViewById<EditText>(R.id.add_task_title)
        val taskDescription = view.findViewById<EditText>(R.id.add_task_desc)
        val dueDate = view.findViewById<EditText>(R.id.add_task_date)
        val datePicker = DatePickerDialog(this, { _, year, month, day ->
            // Set selected date to EditText
            val formattedDate = "$day/${month + 1}/$year"
            dueDate.setText(formattedDate)
        }, Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        dueDate.setOnClickListener {
            datePicker.show()
        }

        builder.setView(view)
            .setTitle("Add Task")
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Insert") { _, _ ->
                val title = taskTitle.text.toString()
                val description = taskDescription.text.toString()
                val date = dueDate.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                    val isInserted = database.insertData(title, description, date)
                    if (isInserted) {
                        Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                        refreshRecyclerView()
                    } else {
                        Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }

        builder.create().show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshRecyclerView() {
        addanewtaskList.clear()

        val cursor = database.getAllTasks()
        cursor?.apply {
            while (moveToNext()) {
                val taskId = getString(getColumnIndex(Database.COLUMN_ID))
                val taskTitle = getString(getColumnIndex(Database.COLUMN_TASK_TITLE))
                val taskDescription = getString(getColumnIndex(Database.COLUMN_TASK_DESCRIPTION))
                val dueDate = getString(getColumnIndex(Database.COLUMN_DUE_DATE))
                addanewtaskList.add(Addanewtask(taskId, taskTitle, taskDescription, dueDate, null))
            }
            close()
        }

        addTaskAdapter.notifyDataSetChanged()
    }

    private fun sortByDate() {
        // Sort the task list by due date
        addanewtaskList.sortBy { it.dueDate }

        // Notify the adapter of the data change
        addTaskAdapter.notifyDataSetChanged()
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                // Overlay permission granted, perform necessary actions
            } else {
                Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
