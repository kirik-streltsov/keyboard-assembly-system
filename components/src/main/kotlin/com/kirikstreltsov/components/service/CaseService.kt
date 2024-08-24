package com.kirikstreltsov.components.service

import com.kirikstreltsov.components.repository.CaseRepository
import com.kirikstreltsov.entities.Case
import org.springframework.stereotype.Service

@Service
class CaseService(private val repository: CaseRepository) {
    fun getAllCases(): List<Case> = repository.findAll()
    fun getCaseById(id: Long): Case? = repository.findById(id).orElse(null)
    fun createCase(case: Case) = repository.save(case)

    fun setCasePrice(id: Long, price: Double) : Case {
        val case = repository.findById(id).orElseThrow()
        case.price = price
        return repository.save(case)
    }

    fun deleteCaseById(id: Long) : Case {
        val case = repository.findById(id).orElseThrow()
        repository.deleteById(id)
        return case
    }
}