package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Throwable(message)

class TaskException(message: String) : TudeeException(message)
class CategoryException(message: String) : TudeeException(message)