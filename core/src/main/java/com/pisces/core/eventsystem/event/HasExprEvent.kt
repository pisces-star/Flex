package com.pisces.core.eventsystem.event

import java.util.concurrent.Callable

interface HasExprEvent {
    val expr: Callable<Any>
}