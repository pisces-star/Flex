package com.pisces.litho.widget

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext

open class DefaultComponentBuilder<T : Component>(
    context: ComponentContext,
    defStyleAttr: Int,
    defStyleRes: Int,
    @JvmField var component: T
) : Component.Builder<DefaultComponentBuilder<T>>(context, defStyleAttr, defStyleRes, component) {
    override fun build(): Component {
        return component
    }

    override fun getThis(): DefaultComponentBuilder<T> {
        return this
    }

    override fun setComponent(component: Component?) {
        this.component = component as T
    }
}