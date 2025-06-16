package com.example.tudeeapp.data.exception


abstract class DataException(message: String) : Exception(message)

class MappingException( mappingError: String) : DataException("Mapping error : $mappingError")
class MappingDateTimeException(mappingError: String) : DataException("Mapping DataTime Error : $mappingError")
class MappingDrawableException(mappingError: String) : DataException("Mapping From Image Url To Image Drawable Error : $mappingError")