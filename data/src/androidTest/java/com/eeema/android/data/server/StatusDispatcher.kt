package com.eeema.android.data.server

import okhttp3.mockwebserver.Dispatcher

abstract class StatusDispatcher : Dispatcher() {
    var status: ResponseStatus = ResponseStatus.Ok
}
