package com.example.tudeeapp.data


abstract class DataException(message: String) : Exception(message)

class MappingException( mappingError: String) : DataException("Mapping error : $mappingError")
class MappingDateTimeException(mappingError: String) : DataException("DataTime Error : $mappingError")