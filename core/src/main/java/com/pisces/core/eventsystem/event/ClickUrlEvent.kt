package com.pisces.core.eventsystem.event

import android.view.View

class ClickUrlEvent(
    source: View,
    val url: String
) : ClickEvent(source) {

}