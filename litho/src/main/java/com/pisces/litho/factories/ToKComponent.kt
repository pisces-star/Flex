package com.pisces.litho.factories

import com.facebook.litho.Component
import com.pisces.core.build.PropSet
import com.pisces.core.build.RenderNodeFactory

abstract class ToKComponent : RenderNodeFactory<Component> {

    override fun create(display: Boolean, attrs: PropSet, children: List<Component>, other: Any?): Component {
        TODO("Not yet implemented")
    }
}