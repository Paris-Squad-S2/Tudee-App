package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Throwable(message)
class TaskException() : TudeeException("Task exception ")
class CategoryException() : TudeeException("Task exception")
class UpdateTaskStatusException: TudeeException("failed to update task status")
class GetCategoryByIdException: TudeeException("failed to get category")
class GetTaskByIdException: TudeeException("failed to get task")