package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Throwable(message)
class TaskException(message: String="") : TudeeException("Task exception : $message")
class CategoryException(message: String="") : TudeeException("Category exception : $message")