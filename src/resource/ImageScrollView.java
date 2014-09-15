package resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ImageScrollView extends ScrollView{

	private ScrollViewListenner scrollViewListenner;
	private Context mContext;
	private ArrayList<ColumnMeta> mColumnMetaList;
	private int mMarginH = 8;
    private int mMarginV = 8;
    private int mColumnWidth = 0;
    private RelativeLayout mLayout; //
    private ImageViewClickListener mListener;
    
	public ImageScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ImageScrollView(Context context, AttributeSet attrs) 
	{
        super(context, attrs);
        Log.d("ImageScrollView", "ImageScrollView");
        
        mContext = context;
        mColumnMetaList = new ArrayList<ColumnMeta>();
        
	}
	
	public void setImageViewClickListener(ImageViewClickListener listener) 
	{
        mListener = listener;
    }

    public void setHorizontalMarin(int margin) {
        mMarginH = margin;
    }

    public void setVerticalMargin(int margin) {
        mMarginV = margin;
    }

    public void setScrollViewListener(ScrollViewListenner scrollViewListener) 
    {
        this.scrollViewListenner = scrollViewListener;
    }   
    
    /**
     * remove all the ImageViews
     */
    public void removeAllImageViews()
    {
        /** if the layout is never created */
        if (mLayout == null) {
            return;
        }

        for (int i = 0; i < mLayout.getChildCount(); ++i) {
            ImageView imageView = (ImageView) mLayout.getChildAt(i);
            imageView.setImageBitmap(null);
        }
        mLayout.removeAllViews();
        mColumnMetaList.clear();
    }
	
    public int getImageCount() 
    {
        if (mLayout == null) {
            return 0;
        }
        return mLayout.getChildCount();
    }
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListenner != null) {
            scrollViewListenner.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
    
    public int addImageView(int width, int height) {
        if (mColumnMetaList == null || mColumnMetaList.size() == 0) {
            beforeAddImageView();
        }
        int id = AppUtils.generateViewId();
        ImageView imageView = new ImageView(mContext);
        int realWidth = mColumnWidth;
        int realHeight = height * realWidth / width;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                realWidth, realHeight);
        Collections.sort(mColumnMetaList, new Comparator<ColumnMeta>() {
            @Override
            public int compare(ColumnMeta lhs, ColumnMeta rhs) {
                if (lhs.getHeight() < rhs.getHeight()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        ColumnMeta above = mColumnMetaList.get(0);
        above.addHeight(realHeight);
        // find its upper view
        if (above.getTopId() == ColumnMeta.PARENT_TOP) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            params.topMargin = mMarginV; // top margin
            params.bottomMargin = mMarginV; // bottom margin
        } else {
            params.addRule(RelativeLayout.BELOW, above.getTopId());
            params.topMargin = 0; // top margin
            params.bottomMargin = mMarginV; // bottom margin
        }
        above.setTopId(id);
        // find its left view
        if (above.getLeftId() == ColumnMeta.PARENT_LEFT) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                    RelativeLayout.TRUE);
            params.leftMargin = mMarginH;
            params.rightMargin = mMarginH;
        } else {
            params.addRule(RelativeLayout.RIGHT_OF, above.getLeftId());
            params.leftMargin = 0;
            params.rightMargin = mMarginH;
        }

        // set the leftId of the right column of current
        for (ColumnMeta record : mColumnMetaList) {
            if (record.getColumn() == (above.getColumn() + 1)) {
                record.setLeftId(id);
                break;
            }
        }

        imageView.setId(id);
        imageView.setBackgroundColor(Color.GRAY);
        mLayout.addView(imageView, params);
        final int index = mLayout.getChildCount() - 1;
        if (mListener != null) {
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView view = (ImageView) v;
                    mListener.onImageViewClicked(view, index);
                }
            });
        }

        return id;
    }
	
    /**
     * 
     */
    private void beforeAddImageView() {
        // add the relative layout child view, the only view
        if (mLayout == null) 
        {
            mLayout = new RelativeLayout(mContext);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            addView(mLayout, layoutParams);
        }

        int width = getWidth();
        int height = getHeight();

        // I try to use two columns when on mobile phone
        // and three columns when on landscape view
        int columnCnt = height > width ? 2 : 3;
        mColumnWidth = (width - (columnCnt + 1) * mMarginH) / columnCnt;
        for (int i = 1; i <= columnCnt; ++i) {
            ColumnMeta columnMeta = new ColumnMeta();
            columnMeta.setHeight(0);
            columnMeta.setColumn(i);
            columnMeta.setLeftId(i == 1 ? ColumnMeta.PARENT_LEFT
                    : ColumnMeta.INVALID_LEFT);
            // the first row is all below parent top
            columnMeta.setTopId(ColumnMeta.PARENT_TOP);
            mColumnMetaList.add(columnMeta);
        }
    }
    
    
	 public interface ScrollViewListenner {

	        void onScrollChanged(ImageScrollView scrollView, int x, int y,
	                int oldx, int oldy);
	    }
	 
	 public interface ImageViewClickListener {

	        void onImageViewClicked(ImageView imageView, int index);

	    }
}
