package com.codigosdeprogramacion.mistock.data

data class Product(
    val exists: Boolean,
    val code: String?,
    val name: String?,
    val price: Double?,
    val stock: Int?
)
