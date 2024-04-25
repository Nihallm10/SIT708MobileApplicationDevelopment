package com.example.task_manager_app_41p
import android.widget.Button


class Addanewtask(
    var taskId: String?,
    var taskTitle: String?,
    var taskDescription: String?,
    var dueDate: String?,
    var deleteButton: Button?
) {
    // Specify different names for getter and setter methods using @JvmName
    @JvmName("getCustomTaskId")
    fun getTaskId(): String? {
        return taskId
    }

    @JvmName("setCustomTaskId")
    fun setTaskId(taskId: String?) {
        this.taskId = taskId
    }

    @JvmName("getCustomTaskTitle")
    fun getTaskTitle(): String? {
        return taskTitle
    }

    @JvmName("setCustomTaskTitle")
    fun setTaskTitle(taskTitle: String?) {
        this.taskTitle = taskTitle
    }

    @JvmName("getCustomTaskDescription")
    fun getTaskDescription(): String? {
        return taskDescription
    }

    @JvmName("setCustomTaskDescription")
    fun setTaskDescription(taskDescription: String?) {
        this.taskDescription = taskDescription
    }

    @JvmName("getCustomDueDate")
    fun getDueDate(): String? {
        return dueDate
    }

    @JvmName("setCustomDueDate")
    fun setDueDate(dueDate: String?) {
        this.dueDate = dueDate
    }

    @JvmName("getCustomDeleteButton")
    fun getDeleteButton(): Button? {
        return deleteButton
    }

    @JvmName("setCustomDeleteButton")
    fun setDeleteButton(deleteButton: Button?) {
        this.deleteButton = deleteButton
    }
}
