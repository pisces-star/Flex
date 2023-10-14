package com.pisces.core.eventsystem.event

import com.pisces.core.http.HttpRequest

class HttpRequestEvent(
    val httpRequest: HttpRequest
) : TemplateEvent<Unit>(Unit)