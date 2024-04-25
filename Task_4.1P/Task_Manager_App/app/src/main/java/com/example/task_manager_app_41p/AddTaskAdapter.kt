package com.example.task_manager_app_41p

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class AddTaskAdapter(
    private val context: Context,
    private val taskList: MutableList<Addanewtask> = mutableListOf(),
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<AddTaskAdapter.TaskViewHolder>() {

    private val database = Database(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_view, parent, false)
        return TaskViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.idTextView.text = "ID: ${currentTask.taskId}"
        holder.titleTextView.text = currentTask.taskTitle
        holder.descriptionTextView.text = currentTask.taskDescription
        holder.dueDateTextView.text = currentTask.dueDate

        holder.itemView.setOnClickListener {
            try {
                val dialog = Dialog(holder.itemView.context.applicationContext)
                dialog.window?.setBackgroundDrawableResource(android.R.color.black)
                dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
                dialog.setContentView(R.layout.update_task_view)

                val editId = dialog.findViewById<TextView>(R.id.edit_id)
                val editTitle = dialog.findViewById<EditText>(R.id.edit_title)
                val editDesc = dialog.findViewById<EditText>(R.id.edit_desc)
                val editDate = dialog.findViewById<EditText>(R.id.edit_date)

                dialog.setCancelable(true)
                editId.text = "ID: ${currentTask.taskId}"
                editTitle.setText(currentTask.taskTitle)
                editDesc.setText(currentTask.taskDescription)
                editDate.setText(currentTask.dueDate)

                dialog.show()

                val updateButton = dialog.findViewById<Button>(R.id.update_button)
                updateButton.setOnClickListener {
                    val taskId = currentTask.taskId ?: ""

                    val isUpdated = database.updateTask(
                        taskId,
                        editTitle.text.toString(),
                        editDesc.text.toString(),
                        editDate.text.toString()
                    )
                    if (isUpdated) {
                        mainActivity.refreshRecyclerView()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, "Failed to update task", Toast.LENGTH_SHORT).show()
                    }
                }

                val cancelButton = dialog.findViewById<Button>(R.id.cancel_button)
                cancelButton.setOnClickListener {
                    dialog.dismiss() // Close the dialog when Cancel is clicked
                }

            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }

        holder.deleteButton.setOnClickListener {
            val taskId = currentTask.taskId ?: ""
            val isDeleted = database.deleteTask(taskId)

            if (isDeleted) {
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    taskList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, taskList.size)

                    Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid position", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.task_id)
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val dueDateTextView: TextView = itemView.findViewById(R.id.due_date)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }
}
