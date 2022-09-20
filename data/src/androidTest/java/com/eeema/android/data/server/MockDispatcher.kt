package com.eeema.android.data.server

import com.eeema.android.data.utils.FileExtensions
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockDispatcher : StatusDispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (status) {
            is ResponseStatus.CustomOk -> {
                val castedStatus = status as ResponseStatus.CustomOk
                MockResponse().setResponseCode(200).setBodyFromFile(castedStatus.fileName)
            }
            ResponseStatus.Ok -> MockResponse().setResponseCode(200).handleSuccessResponse(request)
            ResponseStatus.NotFound -> MockResponse().setResponseCode(404)
            ResponseStatus.ServerNotFound -> MockResponse().setResponseCode(500)
        }
    }

    private fun MockResponse.handleSuccessResponse(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/oompa-loompas",
            "/oompa-loompas?page=0",
            "/oompa-loompas?page=1" -> this.setBodyFromFile("characters.json")
            "/oompa-loompas?page=2" -> this.setBodyFromFile("characters_page_2.json")
            else -> this
        }
    }

    private fun MockResponse.setBodyFromFile(fileName: String): MockResponse {
        return this.setBody(FileExtensions.readFileFromResources(fileName))
    }
}
