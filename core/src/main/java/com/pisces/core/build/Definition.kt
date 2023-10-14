package com.pisces.core.build

import com.pisces.core.TemplateNode
import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

abstract class Definition {

    internal abstract val dataBinding: DataBinding

    internal abstract fun onBuildWidget(
        buildTool: BuildTool,
        rawProps: Map<String, String>,
        children: List<TemplateNode>,
        factory: RenderNodeFactory<*>?,
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget,
        other: Any?,
        upperDisplay: Boolean = true
    ): List<Any>

    fun bindProps(
        rawAttrs: Map<String, String>,
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget
    ): PropSet {
        return dataBinding.bind(
            engine,
            dataContext,
            eventDispatcher,
            rawAttrs
        )
    }

}