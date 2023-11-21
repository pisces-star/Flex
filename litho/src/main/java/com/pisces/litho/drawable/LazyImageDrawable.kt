package com.pisces.litho.drawable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.core.net.toUri
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.litho.drawable.ComparableDrawable
import com.facebook.litho.drawable.ComparableDrawableWrapper
import com.facebook.litho.fresco.NoOpDrawable
import com.pisces.core.log.AndroidLog
import com.pisces.litho.drawable.load.CornerRadius
import java.util.concurrent.atomic.AtomicBoolean

class LazyImageDrawable private constructor(
    private val context: Context,
    private val model: Any,
    private val radius: CornerRadius
) : DrawableWrapper(), ComparableDrawable {

    constructor(
        context: Context,
        model: Any,
        leftTop: Float,
        rightTop: Float,
        rightBottom: Float,
        leftBottom: Float
    ) : this(
        context, model,
        CornerRadius(
            leftTop,
            rightTop,
            rightBottom,
            leftBottom
        )
    )

    constructor(
        context: Context,
        model: Any,
        radius: Float
    ) : this(context, model, CornerRadius(radius))

    constructor(
        context: Context,
        model: Any
    ) : this(context, model, 0f)

    private val isInit = AtomicBoolean(false)

    override fun isEquivalentTo(other: ComparableDrawable?): Boolean {
        return other is LazyImageDrawable
                && model == other.model
                && radius == other.radius
    }

    override fun draw(canvas: Canvas) {
        if (isInit.compareAndSet(false, true)) {
            val url = model.toString()
            val imageRequest = ImageRequestBuilder.newBuilderWithSource(url.toUri()).build()
            val imagePipeline = Fresco.getImagePipeline()
            val dataSource = if (imagePipeline.isInBitmapMemoryCache(imageRequest)) {
                imagePipeline.fetchImageFromBitmapCache(imageRequest, null)
            } else {
                imagePipeline.fetchDecodedImage(imageRequest, null)
            }

            dataSource.subscribe(object : BaseBitmapDataSubscriber() {
                override fun onNewResultImpl(bitmap: Bitmap?) {
                    wrappedDrawable = BitmapDrawable(context.resources, bitmap)
                    invalidateSelf()
                }

                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                    AndroidLog.getLog(LazyImageDrawable::javaClass.name).warn("download drawable fail")
                }

            }, UiThreadImmediateExecutorService.getInstance())

        } else {
            super.draw(canvas)
        }
    }
}