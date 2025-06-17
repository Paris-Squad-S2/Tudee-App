package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Throwable(message)
class TaskException() : TudeeException("Task exception")
class AddTaskException() : TudeeException("Could not add task")
class EditTaskException() : TudeeException("Could not edit task")
class CategoryException() : TudeeException("Task exception")