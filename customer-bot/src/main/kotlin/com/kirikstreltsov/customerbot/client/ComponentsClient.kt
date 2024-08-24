package com.kirikstreltsov.customerbot.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kirikstreltsov.customerbot.dto.GetKeyboardComponentDto
import com.kirikstreltsov.customerbot.factory.ComponentRequestFactory
import com.squareup.okhttp.OkHttpClient
import org.springframework.stereotype.Component

@Component
class ComponentsClient(private val client: OkHttpClient, private val objectMapper: ObjectMapper) {
    fun getAllCases(): List<GetKeyboardComponentDto> {
        val request = ComponentRequestFactory.getAllCasesRequest()
        val response = client.newCall(request).execute()

        val cases = objectMapper.readValue(
            response.body().string(),
            object : TypeReference<List<GetKeyboardComponentDto>>() {}
        )

        return cases
    }

    fun getAllSwitches(): List<GetKeyboardComponentDto> {
        val request = ComponentRequestFactory.getAllSwitchesRequest()
        val response = client.newCall(request).execute()

        val switches = objectMapper.readValue(
            response.body().string(),
            object : TypeReference<List<GetKeyboardComponentDto>>() {}
        )

        return switches
    }

    fun getAllKeycaps(): List<GetKeyboardComponentDto> {
        val request = ComponentRequestFactory.getAllKeycapsRequest()
        val response = client.newCall(request).execute()

        val keycaps = objectMapper.readValue(
            response.body().string(),
            object : TypeReference<List<GetKeyboardComponentDto>>() {}
        )

        return keycaps
    }

    fun getCaseById(caseId: Long): GetKeyboardComponentDto? {
        val request = ComponentRequestFactory.getCaseById(caseId)
        val response = client.newCall(request).execute()

        return objectMapper.readValue(response.body().string(), GetKeyboardComponentDto::class.java)
    }

    fun getSwitchById(switchId: Long): GetKeyboardComponentDto? {
        val request = ComponentRequestFactory.getSwitchById(switchId)
        val response = client.newCall(request).execute()

        return objectMapper.readValue(response.body().string(), GetKeyboardComponentDto::class.java)
    }

    fun getKeycapById(keycapId: Long): GetKeyboardComponentDto? {
        val request = ComponentRequestFactory.getKeycapById(keycapId)
        val response = client.newCall(request).execute()

        return objectMapper.readValue(response.body().string(), GetKeyboardComponentDto::class.java)
    }
}