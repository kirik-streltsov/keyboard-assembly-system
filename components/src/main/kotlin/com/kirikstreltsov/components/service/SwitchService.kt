package com.kirikstreltsov.components.service

import com.kirikstreltsov.components.repository.SwitchRepository
import com.kirikstreltsov.entities.Switch
import org.springframework.stereotype.Service

@Service
class SwitchService(
    private val switchRepository: SwitchRepository
) {
    fun getAllSwitches(): List<Switch> = switchRepository.findAll()

    fun getSwitchById(id: Long): Switch? = switchRepository.findById(id).orElse(null)

    fun saveSwitch(switch: Switch): Switch = switchRepository.save(switch)

    fun setSwitchPrice(id: Long, price: Double) : Switch {
        val switch = switchRepository.findById(id).orElseThrow { RuntimeException("No switch found with id $id") }
        switch.price = price
        return switchRepository.save(switch)
    }

    fun removeSwitchById(id: Long): Switch {
        val switch = switchRepository.findById(id).orElseThrow { RuntimeException("No switch found with id $id") }
        switchRepository.delete(switch)
        return switch
    }
}