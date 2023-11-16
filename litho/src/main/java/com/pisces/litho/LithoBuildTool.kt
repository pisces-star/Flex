package com.pisces.litho

import android.content.Context
import android.util.ArrayMap
import com.facebook.yoga.YogaNodeManager
import com.pisces.core.build.*
import com.pisces.litho.drawable.load.DrawableLoader
import com.pisces.litho.factories.*
import com.pisces.litho.widget.ComponentTreePool

object LithoBuildTool : BuildTool() {

    override val widgets: Map<String, ToWidget> by lazy {
        val arr = arrayOf(
            "Empty" to ToWidget(Empty, ToEmpty),
            "Flex" to ToWidget(Flex, ToFlex),
            "Pager" to ToWidget(Pager, ToPager),
            "Image" to ToWidget(Image, ToImage),
            "Scroller" to ToWidget(Scroller, ToScroller),
            "TextInput" to ToWidget(TextInput, ToTextInput),
            "Text" to ToWidget(Text, ToText),
            "for" to ToWidget(For, null),
            "foreach" to ToWidget(ForEach, null),
            "when" to ToWidget(When, null),
            "if" to ToWidget(If, null)
        )
        arr.toMap(ArrayMap(arr.size))
    }

    override val kits: List<BuildKit> by lazy {
        return@lazy listOf(
            YogaNodeManager,
            DrawableLoader,
            ComponentTreePool
        )
    }

    @JvmStatic
    fun init(c: Context) {
        install(c)
    }

}