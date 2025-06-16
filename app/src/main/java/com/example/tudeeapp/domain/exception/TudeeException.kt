package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Throwable(message)
class TaskException() : TudeeException("Task exception ")
class CategoryException() : TudeeException("Task exception")