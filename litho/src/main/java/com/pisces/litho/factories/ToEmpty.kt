package com.pisces.litho.factories

import com.facebook.litho.Component
import com.facebook.litho.EmptyComponent
import com.pisces.core.build.PropSet
import com.pisces.core.build.RenderNodeFactory

internal object ToEmpty : RenderNodeFactory<Component> {
    override fun create(display: Boolean, attrs: PropSet, children: List<Component>, other: Any?): Component {
        return EmptyComponent()
    }
}