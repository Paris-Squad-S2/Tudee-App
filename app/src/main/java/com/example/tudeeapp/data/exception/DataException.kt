package com.example.tudeeapp.data.exception

import com.example.tudeeapp.domain.exception.TudeeException


abstract class DataException(message: String) : TudeeException(message)
abstract class MappingException(message: String) : DataException(message)
class MappingPriorityException(mappingError: String) : DataException("Mapping Priority error : $mappingError")
class MappingStatusException(mappingError: String) : DataException("Mapping Status error : $mappingError")
class MappingDateTimeException(mappingError: String) : DataException("Mapping DataTime Error : $mappingError")
class MappingDrawableException(mappingError: String) : DataException("Mapping From Image Url To Image Drawable Error : $mappingError")