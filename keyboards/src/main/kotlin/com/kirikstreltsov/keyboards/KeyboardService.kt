package com.kirikstreltsov.keyboards

import com.kirikstreltsov.entities.Keyboard
import org.springframework.stereotype.Service

@Service
class KeyboardService(private val keyboardRepository: KeyboardRepository) {
    fun saveKeyboard(keyboard: Keyboard): Keyboard = keyboardRepository.save(keyboard)
    fun findAll(): List<Keyboard> = keyboardRepository.findAll()
    fun findById(id: Long): Keyboard? = keyboardRepository.findById(id).orElse(null)
}