package com.kirikstreltsov.components.controller

import com.kirikstreltsov.components.dto.request.CreateComponentRequest
import com.kirikstreltsov.components.dto.response.CreateComponentResponse
import com.kirikstreltsov.components.dto.response.GetComponentResponse
import com.kirikstreltsov.components.service.KeycapService
import org.springframework.web.bind.annotation.*
import com.kirikstreltsov.entities.Keycap
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/components/keycaps")
class KeycapController(val keycapService: KeycapService) {
    @GetMapping
    fun findAllKeycaps() : List<GetComponentResponse>{
        return keycapService.findAllKeycaps().map { GetComponentResponse(it.id, it.name, it.price) }
    }

    @GetMapping("/{id}")
    fun findKeycapById(@PathVariable("id") id: Long) : GetComponentResponse? {
        val keycap = keycapService.findKeycapById(id) ?: return null
        return GetComponentResponse(keycap.id, keycap.name, keycap.price)
    }

    @PostMapping
    fun createKeycap(@RequestBody dto: CreateComponentRequest) : CreateComponentResponse {
        val keycap = Keycap(dto.name, dto.price)
        keycapService.saveKeycap(keycap)
        return CreateComponentResponse(keycap.id, keycap.name, keycap.price)
    }

    @PatchMapping("/{id}")
    fun setKeycapPriceById(@PathVariable("id") id: Long, @RequestParam price: Double) : GetComponentResponse {
        keycapService.setPriceById(id, price)
        val keycap = keycapService.findKeycapById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return GetComponentResponse(keycap.id, keycap.name, keycap.price)
    }

    @DeleteMapping("/{id}")
    fun deleteKeycapById(@PathVariable("id") id: Long) : GetComponentResponse {
        val keycap = keycapService.findKeycapById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        keycapService.removeKeycapById(id)

        return GetComponentResponse(keycap.id, keycap.name, keycap.price)
    }
}