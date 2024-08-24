package com.kirikstreltsov.customerbot.factory

object ComponentRequestFactory {
    private const val URL = "http://localhost:8080/api/v1/components"

    fun getAllCasesRequest() = RequestFactory.createGetRequest("$URL/cases")
    fun getAllSwitchesRequest() = RequestFactory.createGetRequest("$URL/switches")
    fun getAllKeycapsRequest() = RequestFactory.createGetRequest("$URL/keycaps")

    fun getCaseById(caseId: Long) = RequestFactory.createGetRequest("$URL/cases/$caseId")
    fun getSwitchById(switchId: Long) = RequestFactory.createGetRequest("$URL/switches/$switchId")
    fun getKeycapById(keycapId: Long) = RequestFactory.createGetRequest("$URL/keycaps/$keycapId")
}