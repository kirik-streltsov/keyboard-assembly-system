package com.kirikstreltsov.adminbot.factory

import com.squareup.okhttp.Headers
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody

object RequestFactory {
    fun createGetRequest(url: String, headers: Headers = Headers.of()): Request = Request
        .Builder()
        .url(url)
        .get()
        .headers(headers)
        .build()

    fun createPostRequest(url: String, body: RequestBody, headers: Headers = Headers.of()): Request = Request
        .Builder()
        .url(url)
        .post(body)
        .headers(headers)
        .build()

    fun createPatchRequest(
        url: String,
        requestBody: RequestBody,
        headers: Headers = Headers.of()
    ): Request = Request
        .Builder()
        .url(url)
        .patch(requestBody)
        .headers(headers)
        .build()

    fun createDeleteRequest(url: String, headers: Headers = Headers.of()): Request = Request
        .Builder()
        .url(url)
        .delete()
        .headers(headers)
        .build()
}