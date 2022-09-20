package com.eeema.android.data.server

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule(
    private val dispatcher: StatusDispatcher
) : TestRule {

    private val webServer = MockWebServer()

    val url: HttpUrl
        get() = webServer.url("/")

    fun setResponseStatus(status: ResponseStatus) {
        dispatcher.status = status
    }

    override fun apply(
        base: Statement?,
        description: Description?
    ) = object : Statement() {
        override fun evaluate() {
            webServer.dispatcher = dispatcher
            base?.evaluate()
            webServer.shutdown()
        }
    }
}
