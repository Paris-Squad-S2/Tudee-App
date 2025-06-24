package com.example.tudeeapp.data.exception

import com.example.tudeeapp.domain.exception.TudeeException


abstract class DataException(message: String) : TudeeException(message)
abstract class MappingException(message: String) : DataException(message)
class MappingPriorityException(mappingError: String) : MappingException("Mapping Priority error : $mappingError")
class MappingStatusException(mappingError: String) : MappingException("Mapping Status error : $mappingError")
class MappingDateTimeException(mappingError: String) : MappingException("Mapping DataTime Error : $mappingError")
class MappingDrawableException(mappingError: String) : MappingException("Mapping From Image Uri To Image Drawable Error : $mappingError")