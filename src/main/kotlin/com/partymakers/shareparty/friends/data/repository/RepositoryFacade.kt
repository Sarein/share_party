package com.partymakers.shareparty.friends.data.repository

import com.partymakers.shareparty.friends.data.mapper.DomainMapper
import com.partymakers.shareparty.friends.data.mapper.PersistenceMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.Optional

class RepositoryFacade<T, U, R>(
    private val repository: JpaRepository<U, R>,
    private val domainMapper: DomainMapper<T, U>,
    private val persistenceMapper: PersistenceMapper<U, T>
) : CrudRepository<T, R> {

    override fun <S : T> save(entity: S): S =
        persistenceMapper.toDomain(repository.save(domainMapper.toPersistence(entity))) as S

    override fun <S : T> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        entities.forEach { save(it) }
        return entities
    }

    override fun findById(id: R): Optional<T> =
        repository.findById(id).map(persistenceMapper::toDomain)

    override fun existsById(id: R): Boolean =
        repository.existsById(id)

    override fun findAll(): MutableIterable<T> =
        repository.findAll()
            .map(persistenceMapper::toDomain)
            .toMutableList()

    override fun findAllById(ids: MutableIterable<R>): MutableIterable<T> =
        repository.findAllById(ids)
            .map(persistenceMapper::toDomain)
            .toMutableList()

    override fun count(): Long =
        repository.count()

    override fun deleteById(id: R) =
        repository.deleteById(id)

    override fun delete(entity: T) =
        repository.delete(domainMapper.toPersistence(entity))

    override fun deleteAllById(ids: MutableIterable<R>) =
        repository.deleteAllById(ids)

    override fun deleteAll(entities: MutableIterable<T>) {
        entities.forEach { delete(it) }
    }

    override fun deleteAll() =
        repository.deleteAll()
}