package com.kirikstreltsov.components.service

import com.kirikstreltsov.components.repository.KeycapRepository
import com.kirikstreltsov.entities.Keycap
import org.springframework.stereotype.Service

@Service
class KeycapService(val keycapRepository: KeycapRepository) {
    fun saveKeycap(keycap: Keycap): Keycap = keycapRepository.save(keycap)

    fun findKeycapById(keycapId: Long): Keycap? = keycapRepository.findById(keycapId).orElse(null)

    fun findAllKeycaps(): List<Keycap> = keycapRepository.findAll()

    fun setPriceById(id: Long, price: Double) : Keycap {
        val keycap = keycapRepository.findById(id).orElseThrow { RuntimeException("Keycap not found") }
        keycap.price = price
        return keycapRepository.save(keycap)
    }

    fun removeKeycapById(id: Long) : Keycap {
        val keycap = keycapRepository.findById(id).orElseThrow { RuntimeException("Keycap not found") }
        keycapRepository.deleteById(id)
        return keycap
    }
}