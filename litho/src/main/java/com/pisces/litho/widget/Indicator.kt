package com.pisces.litho.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.facebook.litho.*
import com.facebook.litho.core.height
import com.facebook.litho.core.margin
import com.facebook.litho.core.width
import com.facebook.litho.kotlin.widget.Image
import com.facebook.rendercore.px
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaJustify
import com.pisces.core.build.UrlType
import com.pisces.core.enums.IndicatorType
import com.pisces.litho.drawable.LazyImageDrawable
import com.pisces.litho.toPx

class Indicator(
    private val indicatorHeight: Int = 3.toPx(),
    private val indicatorWidth: Int = 8.toPx(),
    private val indicatorSpace: Int = 2.5.toPx(),
    private val indicatorMargin: Int = 5.toPx(),
    private val indicatorType: IndicatorType = IndicatorType.OVAL,
    private val indicatorUnselected: String = "",
    private val indicatorSelected: String = "",
    private val selectedIndex: Int,
    private val indicatorCount: Int
) : KComponent() {
    override fun ComponentScope.render(): Component? {
        val indicatorSelectedDrawable = loadDrawable(context.androidContext, indicatorSelected)
        val indicatorUnselectedDrawable = loadDrawable(context.androidContext, indicatorUnselected)
        return Row(
            alignContent = YogaAlign.CENTER,
            justifyContent = YogaJustify.CENTER,
            style = Style.margin(bottom = indicatorMargin.px)
        ) {
            if (indicatorSelectedDrawable != null && indicatorUnselectedDrawable != null) {
                for (index in 0 until indicatorCount) {
                    child(
                        Image(
                            drawable = if (index == selectedIndex % indicatorCount) indicatorSelectedDrawable else indicatorSelectedDrawable,
                            style = Style.height(indicatorHeight.px).width(indicatorWidth.px)
                                .margin(horizontal = (indicatorSpace / 2).px)
                        )
                    )
                }
            }
        }

    }

    private fun loadDrawable(c: Context, url: String?): Drawable? {
        if (url.isNullOrEmpty()) {
            return null
        } else {
            val (type, args) = UrlType.parseUrl(c, url)
            when (type) {
                UrlType.COLOR -> {
                    return ColorDrawable(args[0] as Int)
                }

                UrlType.GRADIENT -> {
                    return GradientDrawable(
                        args[0] as GradientDrawable.Orientation,
                        args[1] as IntArray
                    )
                }

                UrlType.URL, UrlType.RESOURCE -> {
                    return LazyImageDrawable(c, args[0])
                }

                else -> {
                    return null
                }
            }
        }
    }

}