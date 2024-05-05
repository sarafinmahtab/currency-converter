package com.sarafinmahtab.currencyconverter.data.dto

interface DtoToDomainMapper<T> {
    fun mapToDomain(): T
}
