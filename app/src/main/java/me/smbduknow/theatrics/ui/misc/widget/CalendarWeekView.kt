package me.smbduknow.theatrics.ui.misc.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import me.smbduknow.theatrics.R
import java.text.SimpleDateFormat
import java.util.*


class CalendarWeekView(
        ctx: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
) : LinearLayout(ctx, attrs, defStyleAttr, defStyleRes) {

    constructor(ctx: Context): this(ctx, null)
    constructor(ctx: Context, attrs: AttributeSet?): this(ctx, attrs, 0)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int): this(ctx, attrs, defStyleAttr, 0)

    private val listOfDates = ArrayList<Date>()
    private val listofViews = ArrayList<CalendarDateView>()

    private val sekectedPosition = -1

    var onDateSelectListener: (date: Date, index: Int) -> Unit = {_, _ -> }

    init {
        orientation = HORIZONTAL
        isClickable = true

        val cal = Calendar.getInstance()
        for(i in 0..6) {
            if(i > 0) cal.add(Calendar.DATE, 1)

            val date = cal.time
            listOfDates.add(date)

            val dateView = createDateView(date, i)
            listofViews.add(dateView)

            addView(dateView, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        }

        val btn = ImageButton(context).apply {
            gravity = Gravity.CENTER
            val padding = resources.getDimensionPixelSize(R.dimen.space_normal)
            setPadding(padding, padding, padding, padding)
            background = ResourcesCompat.getDrawable(resources, R.color.transparent_white, null)


            var drawable: Drawable? = VectorDrawableCompat.create(resources, R.drawable.ic_more_vert_black_24dp, null)
            drawable = DrawableCompat.wrap(drawable!!)
            DrawableCompat.setTint(drawable, ResourcesCompat.getColor(resources, R.color.text_secondary, null))
            setImageDrawable(drawable)
        }

        addView(btn, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
    }



    private fun createDateView(date: Date, index: Int) = CalendarDateView(context).apply {
        setTextDate(SimpleDateFormat("dd", Locale.getDefault()).format(date))
        setTextWeekday(SimpleDateFormat("EE", Locale.getDefault()).format(date))
        val padding = resources.getDimensionPixelSize(R.dimen.space_normal)
        setPadding(padding, padding, padding, padding)
        this.setOnClickListener { onDateSelectListener(date, index) }
    }
}