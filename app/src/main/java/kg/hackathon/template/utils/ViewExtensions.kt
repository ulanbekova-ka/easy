package kg.hackathon.template.utils

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ViewGroup.inflate(@LayoutRes resid: Int): View {
    return LayoutInflater.from(context)
        .inflate(resid, this, false)
}

fun ImageView.setImageByUrlWithListener(
    imageUrl: String?, onSuccess: ((Drawable) -> (Unit))? = null,
    onError: ((GlideException?) -> (Unit))? = null, requestOptions: RequestOptions? = null
) {
    if (imageUrl == null) {
        onError?.invoke(null)
        return
    }
    val builder = Glide.with(this.context)
        .load(imageUrl)
        .listener(getGlideOnLoadListener(onSuccess, onError))
    if (requestOptions != null) builder.apply(requestOptions)
    builder.into(this)
}

private fun getGlideOnLoadListener(
    onSuccess: ((Drawable) -> Unit)?,
    onError: ((GlideException?) -> Unit)?
): RequestListener<Drawable>? {
    val isHaveActionParams = onSuccess != null || onError != null
    if (!isHaveActionParams) return null
    else return object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onError?.invoke(e)
            return true
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onSuccess?.invoke(resource)
            return true
        }
    }
}