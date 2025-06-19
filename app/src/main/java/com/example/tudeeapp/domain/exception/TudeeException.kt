package com.example.tudeeapp.domain.exception

abstract class TudeeException(message: String) : Exception(message)
open class TaskException(message: String) : TudeeException("Task , $message")
open class CategoryException(message: String) : TudeeException("Category , $message")

class NoTaskAddedException() : TaskException("Something wrong happened while adding task")
class NoTaskDeletedException() : TaskException("Something wrong happened while deleting task")
class NoTaskEditedException() : TaskException("Something wrong happened while editing task")
class TaskNotFoundException() : TaskException("The task that you are looking for not found")
class TasksNotFoundException() : TaskException("No tasks found in your local data of tudee")

class AddCategoryException(): CategoryException("Category not added")
class NoCategoryAddedException() : CategoryException("Something wrong happened while adding category")
class NoCategoryDeletedException() : CategoryException("Something wrong happened while deleting category")
class NoCategoryEditedException() : CategoryException("Something wrong happened while editing category")
class CategoryNotFoundException() : CategoryException("The category that you are looking for not found")
class CategoriesNotFoundException() : CategoryException("No categories found in your local data of tudee")