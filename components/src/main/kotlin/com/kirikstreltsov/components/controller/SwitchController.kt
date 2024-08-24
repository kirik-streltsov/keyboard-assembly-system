package com.kirikstreltsov.components.controller

import com.kirikstreltsov.components.dto.request.CreateComponentRequest
import com.kirikstreltsov.components.dto.response.CreateComponentResponse
import com.kirikstreltsov.components.dto.response.GetComponentResponse
import com.kirikstreltsov.components.service.SwitchService
import com.kirikstreltsov.entities.Switch
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/components/switches")
class SwitchController(private val switchService: SwitchService) {
    @PostMapping
    fun saveSwitch(@RequestBody dto: CreateComponentRequest): CreateComponentResponse {
        val switch = Switch(dto.name, dto.price)
        switchService.saveSwitch(switch)
        return CreateComponentResponse(switch.id, switch.name, switch.price)
    }

    @GetMapping("/{id}")
    fun getSwitchById(@PathVariable("id") id: Long) : GetComponentResponse? {
        val switch = switchService.getSwitchById(id) ?: return null
        return GetComponentResponse(switch.id, switch.name, switch.price)
    }

    @GetMapping
    fun findAllSwitches(): List<GetComponentResponse> = switchService.getAllSwitches()
        .map { GetComponentResponse(it.id, it.name, it.price) }

    @DeleteMapping("/{id}")
    fun deleteSwitchById(@PathVariable("id") id: Long) : GetComponentResponse {
        val switch = switchService.getSwitchById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        switchService.removeSwitchById(id)

        return GetComponentResponse(switch.id, switch.name, switch.price)
    }
}