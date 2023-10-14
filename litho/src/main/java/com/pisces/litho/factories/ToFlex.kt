package com.pisces.litho.factories

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaPositionType
import com.pisces.core.build.PropSet
import com.pisces.core.enums.FlexDirection
import com.pisces.litho.Widget
import com.pisces.litho.factories.filler.PropsFiller

internal object ToFlex : ToComponent<Component.ContainerBuilder<*>>() {

    override val propsFiller by PropsFiller
        .create<Component.ContainerBuilder<*>>(CommonProps) {
            enum("flexWrap", Component.ContainerBuilder<*>::wrap)
            enum("justifyContent", Component.ContainerBuilder<*>::justifyContent)
            enum("alignItems", Component.ContainerBuilder<*>::alignItems)
            enum("alignContent", Component.ContainerBuilder<*>::alignContent)
        }

    override fun create(
        c: ComponentContext,
        visibility: Boolean,
        attrs: PropSet
    ): Component.ContainerBuilder<*> {
        val component: Component.ContainerBuilder<*>
        when (attrs.getOrElse("flexDirection") { FlexDirection.ROW }) {
            FlexDirection.COLUMN -> {
                component = Column.create(c)
            }
            FlexDirection.COLUMN_REVERSE -> {
                component = Column.create(c)
                    .reverse(true)
            }
            FlexDirection.ROW_REVERSE -> {
                component = Row.create(c)
                    .reverse(true)
            }
            FlexDirection.ROW -> {
                component = Row.create(c)
            }
            else -> {
                component = Row.create(c)
                    .positionType(YogaPositionType.ABSOLUTE)
                    .positionPx(YogaEdge.ALL, 0)
            }
        }
        return component
    }

    override fun onInstallChildren(
        owner: Component.ContainerBuilder<*>,
        visibility: Boolean,
        attrs: PropSet,
        children: List<Widget>
    ) {
        children.forEach {
            owner.child(it)
        }
    }
}