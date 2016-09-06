package me.smbduknow.theatrics.ui.commons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.smbduknow.theatrics.R;

public class CompatTextView extends AppCompatTextView {
	public CompatTextView(Context context) {
		super(context);
		init(null);
	}

	public CompatTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public CompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(@Nullable AttributeSet attrs) {
		if (attrs != null) {
			Context context = getContext();
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CompatTextView);

			// Obtain DrawableManager used to pull Drawables safely, and check if we're in RTL
			AppCompatDrawableManager dm = AppCompatDrawableManager.get();
			boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;

			// Grab the compat drawable resources from the XML
			int startDrawableRes = a.getResourceId(R.styleable.CompatTextView_compatDrawableStart, 0);
			int topDrawableRes = a.getResourceId(R.styleable.CompatTextView_compatDrawableTop, 0);
			int endDrawableRes = a.getResourceId(R.styleable.CompatTextView_compatDrawableEnd, 0);
			int bottomDrawableRes = a.getResourceId(R.styleable.CompatTextView_compatDrawableBottom, 0);
			int tintColorRes = a.getResourceId(R.styleable.CompatTextView_compatTintColor, 0);

			// Load the used drawables, falling back to whatever may be set in an "android:" namespace attribute
			Drawable[] currentDrawables = getCompoundDrawables();
			Drawable start = startDrawableRes != 0 ? dm.getDrawable(context, startDrawableRes) : currentDrawables[0];
			Drawable end = endDrawableRes != 0 ? dm.getDrawable(context, endDrawableRes) : currentDrawables[1];
			Drawable top = topDrawableRes != 0 ? dm.getDrawable(context, topDrawableRes) : currentDrawables[2];
			Drawable bottom = bottomDrawableRes != 0 ? dm.getDrawable(context, bottomDrawableRes) : currentDrawables[3];

			if(tintColorRes != 0) {
				if(startDrawableRes != 0) start = createTintDrawable(start, tintColorRes);
                if(endDrawableRes != 0) end = createTintDrawable(end, tintColorRes);
                if(topDrawableRes != 0) top = createTintDrawable(top, tintColorRes);
                if(bottomDrawableRes != 0) bottom = createTintDrawable(bottom, tintColorRes);
			}

			// Account for RTL and apply the compound Drawables
			Drawable left = rtl ? end : start;
			Drawable right = rtl ? start : end;

			setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);

			a.recycle();
		}
	}

	private Drawable createTintDrawable(Drawable drawable, int tintColorRes) {
        if(isInEditMode()) return drawable;
		drawable = DrawableCompat.wrap(drawable);
		DrawableCompat.setTint(drawable.mutate(), getResources().getColor(tintColorRes));
		return drawable;
	}
}