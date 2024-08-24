package com.kirikstreltsov.components.controller

import com.kirikstreltsov.components.dto.request.CreateComponentRequest
import com.kirikstreltsov.components.dto.response.CreateComponentResponse
import com.kirikstreltsov.components.dto.response.GetComponentResponse
import com.kirikstreltsov.components.service.CaseService
import com.kirikstreltsov.entities.Case
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/components/cases")
class CaseController(private val caseService: CaseService) {
    @GetMapping
    fun getAllCases(): List<GetComponentResponse> = caseService.getAllCases()
        .map { GetComponentResponse(it.id, it.name, it.price) }

    @GetMapping("/{id}")
    fun getCaseById(@PathVariable id: Long): GetComponentResponse? {
        val case = caseService.getCaseById(id) ?: return null
        return GetComponentResponse(case.id, case.name, case.price)
    }

    @PostMapping
    fun createCase(@RequestBody dto: CreateComponentRequest) : CreateComponentResponse {
        val case = Case(dto.name, dto.price)
        caseService.createCase(case)
        return CreateComponentResponse(case.id, case.name, case.price)
    }

    @PatchMapping("/{id}")
    fun setCasePrice(@PathVariable id: Long, @RequestParam price: Double): GetComponentResponse {
        val case = caseService.setCasePrice(id, price)
        return GetComponentResponse(case.id, case.name, case.price)
    }

    @DeleteMapping("/{id}")
    fun deleteCase(@PathVariable id: Long) : GetComponentResponse {
        val case = caseService.getCaseById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        caseService.deleteCaseById(id)

        return GetComponentResponse(case.id, case.name, case.price)
    }
}