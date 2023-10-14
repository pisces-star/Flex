package com.pisces.core.build

import androidx.annotation.RestrictTo
import com.pisces.core.TemplateNode
import com.pisces.core.eventsystem.EventTarget
import org.apache.commons.jexl3.JexlContext
import org.apache.commons.jexl3.JexlEngine

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object When : Definition() {

    override val dataBinding: DataBinding
        get() = DataBinding.empty

    override fun onBuildWidget(
        buildTool: BuildTool,
        rawProps: Map<String, String>,
        children: List<TemplateNode>,
        factory: RenderNodeFactory<*>?,
        engine: JexlEngine,
        dataContext: JexlContext,
        eventDispatcher: EventTarget,
        other: Any?,
        upperDisplay: Boolean
    ): List<Any> {
        var elseItem: TemplateNode? = null
        if (children.isEmpty()) {
            return emptyList()
        }
        for (item in children) {
            if (item.type == "case") {
                val itemAttrs = item.attrs
                if (itemAttrs != null && If.bindProps(
                        itemAttrs,
                        engine,
                        dataContext,
                        eventDispatcher
                    )["test"] == true) {
                    return item.children?.let {
                        buildTool.buildAll(
                            it,
                            engine,
                            dataContext,
                            eventDispatcher,
                            other,
                            upperDisplay
                        )
                    } ?: emptyList()
                }
            } else if (item.type == "else" && elseItem == null) {
                elseItem = item
            }
        }
        return elseItem?.children?.let {
            buildTool.buildAll(
                it,
                engine,
                dataContext,
                eventDispatcher,
                other,
                upperDisplay
            )
        } ?: emptyList()
    }

}
