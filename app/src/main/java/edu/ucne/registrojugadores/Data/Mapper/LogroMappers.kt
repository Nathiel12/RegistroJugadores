package edu.ucne.registrojugadores.Data.Mapper

import edu.ucne.registrojugadores.Data.Local.Logros.LogroEntity
import edu.ucne.registrojugadores.Domain.Model.Logros.Logro

fun LogroEntity.toLogro(): Logro {
    return Logro(
        logroId = logroId,
        nombre = nombre,
        descripcion = descripcion,
        esLogrado = esLogrado
    )
}

fun Logro.toEntity(): LogroEntity {
    return LogroEntity(
        logroId = logroId,
        nombre = nombre,
        descripcion = descripcion,
        esLogrado = esLogrado
    )
}