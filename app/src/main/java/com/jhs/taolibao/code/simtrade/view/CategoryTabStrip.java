package com.jhs.taolibao.code.simtrade.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.adapter.SimtradePagerAdapter;

/**
 * 自定义 分类smarttab
 * Created by xujingbo on 2016/7/7.
 */
public class CategoryTabStrip extends HorizontalScrollView {
	private static final String TAG = "CategoryTabStrip";

	private LayoutInflater mLayoutInflater;
	private final PageListener pageListener = new PageListener();
	private ViewPager pager;
	private LinearLayout tabsContainer;
	private int tabCount;

	private int currentPosition = 0;
	private float currentPositionOffset = 0f;

	private Rect indicatorRect;

	private LinearLayout.LayoutParams defaultTabLayoutParams;

	private int scrollOffset = 100;
	private int lastScrollX = 0;

	private Drawable indicator;
	
	private boolean isAverage = false;

	private Context context;

	//当前选中的类别
	private TextView tvCurrentCategory;
	//tab是否有图片，默认没有图片
	private boolean isTitleWithImg = false;

	private boolean isIndicatorFitParent = false;
	public CategoryTabStrip(Context context) {
		this(context, null);
	}

	public CategoryTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CategoryTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);

		indicatorRect = new Rect();

		// 当你想让一个高度值不足scrollview的子控件fillparent的时候，单独的定义android:layout_height="fill_parent"是不起作用的，必须加上fillviewport属性，当子控件的高度值大于scrollview的高度时，这个标签就没有任何意义了。
		// 把子控件设置match_parent不就可以么？
		setFillViewport(true);

		// onDraw()  draw()
		setWillNotDraw(false);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

		// 绘制高亮区域作为滑动分页指示器
		indicator = getResources().getDrawable(R.drawable.bg_category_indicator);

	}

	// 绑定与CategoryTabStrip控件对应的ViewPager控件，实现联动
	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}

		pager.setOnPageChangeListener(pageListener);

		notifyDataSetChanged();
	}

	// 当附加在ViewPager适配器上的数据发生变化时,应该调用该方法通知CategoryTabStrip刷新数据
	public void notifyDataSetChanged() {
		tabsContainer.removeAllViews();

		tabCount = pager.getAdapter().getCount();

		for (int i = 0; i < tabCount; i++) {
			if (isTitleWithImg) {
				addTab(i, pager.getAdapter().getPageTitle(i).toString(),
						((SimtradePagerAdapter) pager.getAdapter()).getResId(i));
			}else {
				addTab(i, pager.getAdapter().getPageTitle(i).toString(),0);
			}
		}

	}

	/**
	 * 添加子tab
	 * @param position tab位置
	 * @param title tab标题
	 * @param resId tab的Icon
	 */
	private void addTab(final int position, String title,int resId) {
		ViewGroup tab = (ViewGroup)mLayoutInflater.inflate(R.layout.category_tab, this, false);
		//类名title
		TextView category_text = (TextView) tab.findViewById(R.id.category_text);
		category_text.setText(title);
		category_text.setGravity(Gravity.CENTER);
		category_text.setSingleLine();
		category_text.setFocusable(true);
		category_text.setTextColor(getResources().getColor(R.color.Black));
		//类名icon
		ImageView category_img = (ImageView) tab.findViewById(R.id.category_img);
		if (isTitleWithImg) {
			category_img.setVisibility(VISIBLE);
			category_img.setImageDrawable(getResources().getDrawable(resId));
		}else {
			category_img.setVisibility(GONE);
		}
		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(position);
			}
		});
		if (isAverage){
			int tabWeight = (getScreenWidth()- getPaddingLeft() - getPaddingRight()) / tabCount;
			ViewGroup.LayoutParams params = tab.getLayoutParams();
			params.width = tabWeight ;
			tab.setLayoutParams(params);
			ViewGroup.LayoutParams params1 = tabsContainer.getLayoutParams();
			params1.width = getScreenWidth() - getPaddingLeft() - getPaddingRight();
			tabsContainer.setLayoutParams(params1);
			tabsContainer.addView(tab, position);
		} else {
			tabsContainer.addView(tab, position, defaultTabLayoutParams);
		}
	}

	// 计算滑动过程中矩形高亮区域的上下左右位置
	private void calculateIndicatorRect(Rect rect) {
		try {
			ViewGroup currentTab = (ViewGroup) tabsContainer.getChildAt(currentPosition);
			LinearLayout category_ll = (LinearLayout) currentTab.findViewById(R.id.category_ll);

			float left = (float) (currentTab.getLeft() + category_ll.getLeft());
			float width = ((float) category_ll.getWidth()) + left;
			if (isIndicatorFitParent) {

				left = (float) currentTab.getLeft();
				width = (float) currentTab.getWidth() + left;
			}
			if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
				ViewGroup nextTab = (ViewGroup) tabsContainer.getChildAt(currentPosition + 1);
				LinearLayout next_category_ll = (LinearLayout) nextTab.findViewById(R.id.category_ll);

				float next_left = (float) (nextTab.getLeft() + next_category_ll.getLeft());

				if (isIndicatorFitParent) {
					next_left = (float)nextTab.getLeft();
					left = (left )* (1.0f - currentPositionOffset) + next_left * currentPositionOffset;
					width = width * (1.0f - currentPositionOffset) + currentPositionOffset * (((float) nextTab.getWidth()) + next_left);
				}else {
					left = left * (1.0f - currentPositionOffset) + next_left * currentPositionOffset;
					width = width * (1.0f - currentPositionOffset) + currentPositionOffset * (((float) next_category_ll.getWidth()) + next_left);
				}

			}

			rect.set(((int) left) + getPaddingLeft(), getPaddingTop() + currentTab.getTop() + category_ll.getTop(),
					((int) width) + getPaddingLeft(), currentTab.getTop() + getPaddingTop() + category_ll.getTop() + category_ll.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 计算滚动范围
	private int getScrollRange() {
		return getChildCount() > 0 ? Math.max(0, getChildAt(0).getWidth() - getWidth() + getPaddingLeft() + getPaddingRight()) : 0;
	}

	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}

		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {

			//Half screen offset.
			//- Either tabs start at the middle of the view scrolling straight away
			//- Or tabs start at the begging (no padding) scrolling when indicator gets
			//  to the middle of the view width
			newScrollX -= scrollOffset;
			Pair<Float, Float> lines = getIndicatorCoordinates();
			newScrollX += ((lines.second - lines.first) / 2);
		}

		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}

	}

	private Pair<Float, Float> getIndicatorCoordinates() {
		// default: line below current tab
		int position = Math.max(currentPosition - 2, 0);
		View currentTab = tabsContainer.getChildAt(position);
		float lineLeft = currentTab.getLeft();
		float lineRight = currentTab.getRight();

		// if there is an offset, start interpolating left and right coordinates between current and next tab
		if (currentPositionOffset > 0f && position < tabCount - 1) {

			View nextTab = tabsContainer.getChildAt(position + 1);
			final float nextTabLeft = nextTab.getLeft();
			final float nextTabRight = nextTab.getRight();

			lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
			lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
		}
		return new Pair<>(lineLeft, lineRight);
	}

	private int px2Dp(float px) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		float dpi = dm.density;
		dpi = dpi > 0 ? dpi : 1f;
		return (int)(px * dpi);
	}


	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		calculateIndicatorRect(indicatorRect);

		if(indicator != null) {
			int left = indicatorRect.left - 5;
			int right = indicatorRect.right + 5;
			int bottom = indicatorRect.bottom;
			int top = bottom - 5;
			if (isIndicatorFitParent){
				left = indicatorRect.left;
				right = indicatorRect.right;
			}
			indicator.setBounds(left, top, right, bottom);
			indicator.draw(canvas);
		}

		int i = 0;
		while (i < tabsContainer.getChildCount()) {
			ViewGroup tab = (ViewGroup)tabsContainer.getChildAt(i);
			TextView category_text = (TextView) tab.findViewById(R.id.category_text);
			ImageView category_img = (ImageView) tab.findViewById(R.id.category_img);

			if (category_text != null) {

				if (i == Math.floor(currentPosition + currentPositionOffset + 0.5f)) {
					category_text.setTextColor(getResources().getColor(R.color.Red));
					if (category_img != null ){
						if(isTitleWithImg) {
							category_img.setVisibility(VISIBLE);
							category_img.setSelected(true);

						}else{
							category_img.setVisibility(GONE);
						}
					}
				} else {
					category_text.setTextColor(getResources().getColor(R.color.Black));
					if (category_img != null) {
						category_img.setSelected(false);
					}
				}
			}
			i++;

		}
	}

	private class PageListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			currentPosition = position;
			currentPositionOffset = positionOffset;

			int offset = tabCount > 0 ? (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()) : 0;

			scrollToChild(position, offset);
			if(null != tvCurrentCategory) {
				tvCurrentCategory.setText(pager.getAdapter().getPageTitle(pager.getCurrentItem()));
			}
			invalidate();

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {

				if(pager.getCurrentItem() == 0) {
					// 滑动到最左边
					scrollTo(0, 0);


				} else if (pager.getCurrentItem() == tabCount - 1) {
					// 滑动到最右边
					scrollTo(getScrollRange(), 0);
				} else {
					scrollToChild(pager.getCurrentItem(), 0);
				}
				if (null != tvCurrentCategory) {
					tvCurrentCategory.setText(pager.getAdapter().getPageTitle(pager.getCurrentItem()));
				}
			}
		}

		@Override
		public void onPageSelected(int position) {

		}

	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean ret = super.dispatchTouchEvent(ev);
		if(ret)
		{
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return ret;
	}

	/**
	 * 设置item宽度是否平分屏幕宽度
	 * 必须在setViewPage()之前设置此方法
	 * @param isAverage true：平分
	 */
	public void setAverage(boolean isAverage){
		this.isAverage = isAverage;
	}

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public int getScreenWidth(){
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public void setCurrentTvCateroty(TextView textView){
		this.tvCurrentCategory = textView;
	}

	/**
	 * 设置tab是否带有图片
	 * 必须在setViewPage()之前设置此方法
	 * @param isTitleWithImg
	 */
	public void setTitleWithImg(boolean isTitleWithImg){
		this.isTitleWithImg = isTitleWithImg;
	}

	/**
	 * 设置高亮指示线的长度是否适应tab的宽度
	 * @param isIndicatorFitParent true 适应tab的宽度，false 适应标题内容的宽度
	 *                             默认为false
	 *
	 */
	public void setIndicatorFitParent(boolean isIndicatorFitParent){
		this.isIndicatorFitParent = isIndicatorFitParent;
	}
}
