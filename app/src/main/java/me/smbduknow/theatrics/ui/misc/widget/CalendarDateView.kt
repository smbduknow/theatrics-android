package me.smbduknow.theatrics.ui.misc.widget

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.smbduknow.theatrics.R

// TODO try custom view with https://github.com/Kotlin/anko

class CalendarDateView(
        ctx: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
) : LinearLayout(ctx, attrs, defStyleAttr, defStyleRes) {

    constructor(ctx: Context): this(ctx, null)
    constructor(ctx: Context, attrs: AttributeSet?): this(ctx, attrs, 0)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int): this(ctx, attrs, defStyleAttr, 0)

    val dateTextView: TextView
    val dayTextView: TextView

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        isClickable = true
        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_day_selector, null)

        dateTextView = TextView(ctx, null, 0, R.style.AppTheme_CalendarItemDate)
        dayTextView = TextView(ctx, null, 0, R.style.AppTheme_CalendarItemDay)

        addView(dateTextView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        val dayLp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dayLp.topMargin = ctx.resources.getDimensionPixelSize(R.dimen.space_negative_small)
        addView(dayTextView, dayLp)

        val a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView)

        val dateStr = a.getString(R.styleable.CalendarDateView_textDate) ?: ""
        val dayStr = a.getString(R.styleable.CalendarDateView_textDay) ?: ""

        a.recycle()

        setTextDate(dateStr)
        setTextWeekday(dayStr)
    }

    fun setTextDate(text: CharSequence) {
        dateTextView.text = text
    }

    fun setTextWeekday(text: CharSequence) {
        dayTextView.text = text
    }
}