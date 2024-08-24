package com.kirikstreltsov.adminbot.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.adminbot.dto.CreateKeyboardComponentDto
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

object ComponentRequestFactory {
    private const val URL = "http://localhost:8080/api/v1/components"
    private val objectMapper = ObjectMapper()

    fun getAllCasesRequest() = RequestFactory.createGetRequest("$URL/cases")
    fun getAllSwitchesRequest() = RequestFactory.createGetRequest("$URL/switches")
    fun getAllKeycapsRequest() = RequestFactory.createGetRequest("$URL/keycaps")

    fun createCaseRequest(case: CreateKeyboardComponentDto) = RequestFactory.createPostRequest(
        "$URL/cases", RequestBody.create(
            MediaType.parse("application/json"), objectMapper.writeValueAsString(case)
        )
    )

    fun createSwitchRequest(switch: CreateKeyboardComponentDto) = RequestFactory.createPostRequest(
        "$URL/switches",
        RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(switch))
    )

    fun createKeycapRequest(keycap: CreateKeyboardComponentDto) = RequestFactory.createPostRequest(
        "$URL/keycaps",
        RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(keycap))
    )

    fun getCaseById(caseId: Long) = RequestFactory.createGetRequest("$URL/cases/$caseId")
    fun getSwitchById(switchId: Long) = RequestFactory.createGetRequest("$URL/switches/$switchId")
    fun getKeycapById(keycapId: Long) = RequestFactory.createGetRequest("$URL/keycaps/$keycapId")

    fun removeCaseById(caseId: Long) = RequestFactory.createDeleteRequest("$URL/cases/$caseId")
    fun removeSwitchById(switchId: Long) = RequestFactory.createDeleteRequest("$URL/switches/$switchId")
    fun removeKeycapById(keycapId: Long) = RequestFactory.createDeleteRequest("$URL/keycaps/$keycapId")
}