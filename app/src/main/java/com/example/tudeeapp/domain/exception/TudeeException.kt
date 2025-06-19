package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Exception(message)
class TaskException(message: String? = null) : TudeeException(message ?: "Task exception")
class AddTaskException(message: String? = null) : TudeeException(message ?: "Could not add task")
class EditTaskException(message: String? = null) : TudeeException(message ?: "Could not edit task")
class CategoryException(message: String? = null) : TudeeException(message ?: "Category exception")