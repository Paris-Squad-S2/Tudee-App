package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Exception(message)
open class TaskException : TudeeException("Task error")
open class CategoryException : TudeeException("Category error")