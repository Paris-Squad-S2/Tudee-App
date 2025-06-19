package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Exception(message)
open class TaskException(message: String) : TudeeException(message)
open class CategoryException(message: String="") : TudeeException(message)
// customize your Exception
class NoTasksFoundException() : TaskException("No Tasks Found Exception ")
class NoCategoriesFoundException() : CategoryException("No Categories Found Exception")