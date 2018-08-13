package com.wowo.wowo.Views.Countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.wowo.wowo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 倒计时视图控件
 */
public class CountdownView extends View {
    private static final String DEFAULT_SUFFIX = ":";
    private static final float DEFAULT_SUFFIX_LR_MARGIN = 3; // dp
    private static final float DEFAULT_TIME_BG_DIVISION_LINE_SIZE = 0.5f; // dp

    private Context mContext;
    private int mDay, mHour, mMinute, mSecond, mMillisecond;
    private long mRemainTime;
    private OnCountdownEndListener mOnCountdownEndListener;
    private OnCountdownIntervalListener mOnCountdownIntervalListener;
    private CustomCountDownTimer mCustomCountDownTimer;

    private boolean isShowDay, isShowHour,isShowMinute, isShowSecond, isShowMillisecond;
    private boolean mHasSetIsShowDay, mHasSetIsShowHour;
    private boolean isHideTimeBackground;
    private boolean isShowTimeBgDivisionLine;
    private boolean isTimeTextBold;
    private boolean isSuffixTextBold;

    private Paint mTimeTextPaint;
    private Paint mSuffixTextPaint;
    private Paint mTimeTextBgPaint;
    private Paint mTimeTextBgDivisionLinePaint;

    private RectF mDayBgRectF, mHourBgRectF, mMinuteBgRectF, mSecondBgRectF, mMillisecondBgRectF;

    private float mTimeTextWidth;
    private float mTimeTextHeight;
    private float mTimeTextSize;
    private float mTimeBgSize;
    private int mTimeTextColor;
    private int mTimeBgColor;
    private float mTimeBgRadius;
    private int mTimeBgDivisionLineColor;
    private float mTimeBgDivisionLineSize;
    private float mTimeTextBaseY;
    private float mTimeBgDivisionLineYPos;

    private String mSuffix, mSuffixDay, mSuffixHour, mSuffixMinute, mSuffixSecond, mSuffixMillisecond;
    private int mSuffixTextColor;
    private float mSuffixTextSize;
    private float mSuffixDayTextWidth, mSuffixHourTextWidth, mSuffixMinuteTextWidth, mSuffixSecondTextWidth, mSuffixMillisecondTextWidth;
    private int mSuffixGravity;
    private float mSuffixLRMargin;
    private float mSuffixDayLeftMargin, mSuffixDayRightMargin;
    private float mSuffixHourLeftMargin, mSuffixHourRightMargin;
    private float mSuffixMinuteLeftMargin, mSuffixMinuteRightMargin;
    private float mSuffixSecondLeftMargin, mSuffixSecondRightMargin;
    private float mSuffixMillisecondLeftMargin;
    private float mSuffixDayTextBaseline, mSuffixHourTextBaseline, mSuffixMinuteTextBaseline, mSuffixSecondTextBaseline, mSuffixMillisecondTextBaseline;

    private float mTempSuffixDayLeftMargin, mTempSuffixDayRightMargin;
    private float mTempSuffixHourLeftMargin, mTempSuffixHourRightMargin;
    private float mTempSuffixMinuteLeftMargin, mTempSuffixMinuteRightMargin;
    private float mTempSuffixSecondLeftMargin, mTempSuffixSecondRightMargin;
    private float mTempSuffixMillisecondLeftMargin;
    private String mTempSuffixMinute, mTempSuffixSecond;

    private float mTimeTextBaseline;
    private float mLeftPaddingSize;
    private float mTopPaddingSize;
    private int mContentAllWidth;
    private int mContentAllHeight;
    private int mViewWidth;
    private int mViewHeight;
    private int mTimeTextBottom;

    private float mDayTimeTextWidth;
    private float mDayTimeBgWidth;
    private boolean isDayLargeNinetyNine;

    private long mInterval;
    private long mPreviouIntervalCallbackTime;
    private boolean hasCustomSomeSuffix = false;


    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountdownView);
        mTimeBgColor = ta.getColor(R.styleable.CountdownView_timeBgColor, 0xFF444444);
        mTimeBgRadius = ta.getDimension(R.styleable.CountdownView_timeBgRadius, 0);
        isShowTimeBgDivisionLine = ta.getBoolean(R.styleable.CountdownView_isShowTimeBgDivisionLine, true);
        mTimeBgDivisionLineColor = ta.getColor(R.styleable.CountdownView_timeBgDivisionLineColor, Color.parseColor("#30FFFFFF"));
        mTimeBgDivisionLineSize = ta.getDimension(R.styleable.CountdownView_timeBgDivisionLineSize, dp2px(DEFAULT_TIME_BG_DIVISION_LINE_SIZE));
        mTimeBgSize = ta.getDimension(R.styleable.CountdownView_timeBgSize, 0);

        isTimeTextBold = ta.getBoolean(R.styleable.CountdownView_isTimeTextBold, false);
        mTimeTextSize = ta.getDimension(R.styleable.CountdownView_timeTextSize, sp2px(12));
        mTimeTextColor = ta.getColor(R.styleable.CountdownView_timeTextColor, 0xFF000000);
        isHideTimeBackground = ta.getBoolean(R.styleable.CountdownView_isHideTimeBackground, true);
        isShowDay = ta.getBoolean(R.styleable.CountdownView_isShowDay, false);
        isShowHour = ta.getBoolean(R.styleable.CountdownView_isShowHour, false);
        isShowMinute = ta.getBoolean(R.styleable.CountdownView_isShowMinute, true);
        isShowSecond = ta.getBoolean(R.styleable.CountdownView_isShowSecond, true);
        isShowMillisecond = ta.getBoolean(R.styleable.CountdownView_isShowMillisecond, false);

        mHasSetIsShowDay = ta.hasValue(R.styleable.CountdownView_isShowDay);
        mHasSetIsShowHour = ta.hasValue(R.styleable.CountdownView_isShowHour);

        isSuffixTextBold = ta.getBoolean(R.styleable.CountdownView_isSuffixTextBold, false);
        mSuffixTextSize = ta.getDimension(R.styleable.CountdownView_suffixTextSize, sp2px(12));
        mSuffixTextColor = ta.getColor(R.styleable.CountdownView_suffixTextColor, 0xFF000000);
        mSuffix = ta.getString(R.styleable.CountdownView_suffix);
        mSuffixDay = ta.getString(R.styleable.CountdownView_suffixDay);
        mSuffixHour = ta.getString(R.styleable.CountdownView_suffixHour);
        mSuffixMinute = ta.getString(R.styleable.CountdownView_suffixMinute);
        mSuffixSecond = ta.getString(R.styleable.CountdownView_suffixSecond);
        mSuffixMillisecond = ta.getString(R.styleable.CountdownView_suffixMillisecond);
        mSuffixGravity = ta.getInt(R.styleable.CountdownView_suffixGravity, 1);
        mSuffixLRMargin = ta.getDimension(R.styleable.CountdownView_suffixLRMargin, -1);
        mSuffixDayLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixDayLeftMargin, -1);
        mSuffixDayRightMargin = ta.getDimension(R.styleable.CountdownView_suffixDayRightMargin, -1);
        mSuffixHourLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixHourLeftMargin, -1);
        mSuffixHourRightMargin = ta.getDimension(R.styleable.CountdownView_suffixHourRightMargin, -1);
        mSuffixMinuteLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixMinuteLeftMargin, -1);
        mSuffixMinuteRightMargin = ta.getDimension(R.styleable.CountdownView_suffixMinuteRightMargin, -1);
        mSuffixSecondLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixSecondLeftMargin, -1);
        mSuffixSecondRightMargin = ta.getDimension(R.styleable.CountdownView_suffixSecondRightMargin, -1);
        mSuffixMillisecondLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixMillisecondLeftMargin, -1);
        ta.recycle();//为了保持以后使用的一致性，需要回收

        mTempSuffixDayLeftMargin = mSuffixDayLeftMargin;
        mTempSuffixDayRightMargin = mSuffixDayRightMargin;
        mTempSuffixHourLeftMargin = mSuffixHourLeftMargin;
        mTempSuffixHourRightMargin = mSuffixHourRightMargin;
        mTempSuffixMinuteLeftMargin = mSuffixMinuteLeftMargin;
        mTempSuffixMinuteRightMargin = mSuffixMinuteRightMargin;
        mTempSuffixSecondLeftMargin = mSuffixSecondLeftMargin;
        mTempSuffixSecondRightMargin = mSuffixSecondRightMargin;
        mTempSuffixMillisecondLeftMargin = mSuffixMillisecondLeftMargin;

        mTempSuffixMinute = mSuffixMinute;
        mTempSuffixSecond = mSuffixSecond;

        // 初始化
        initPaint();
        initSuffix(true);
        initSuffixMargin();

        // 设置固定的时间
        // 挑一两个时间 (分钟或者秒)
        if (!isShowMinute && !isShowSecond) {
            isShowSecond = true;
        }
        if (!isShowSecond) {
            isShowMillisecond = false;
        }

        // 初始化时间Text的宽度和高度
        Rect rect = new Rect();
        mTimeTextPaint.getTextBounds("00", 0, 2, rect);
        mTimeTextWidth = rect.width();
        mTimeTextHeight = rect.height();
        mTimeTextBottom = rect.bottom;

        if (!isHideTimeBackground && mTimeBgSize < mTimeTextWidth) {
            mTimeBgSize = mTimeTextWidth + (dp2px(2) * 4);
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 时间
        mTimeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextPaint.setColor(mTimeTextColor);
        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
        mTimeTextPaint.setTextSize(mTimeTextSize);
        if (isTimeTextBold) {
            mTimeTextPaint.setFakeBoldText(true);
        }

        // 时间计量单位
        mSuffixTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSuffixTextPaint.setColor(mSuffixTextColor);
        mSuffixTextPaint.setTextSize(mSuffixTextSize);
        if (isSuffixTextBold) {
            mSuffixTextPaint.setFakeBoldText(true);
        }

        // 时间背景
        mTimeTextBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgPaint.setStyle(Paint.Style.FILL);
        mTimeTextBgPaint.setColor(mTimeBgColor);

        // 时间背景的分割线
        mTimeTextBgDivisionLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        mTimeTextBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
    }

    /**
     * 初始化时间计量单位
     */
    private void initSuffix(boolean isInit) {
        boolean isSuffixNull = true;
        float mSuffixTextWidth = 0;
        float mDefSuffixTextWidth = mSuffixTextPaint.measureText(DEFAULT_SUFFIX);

        if (!TextUtils.isEmpty(mSuffix)) {
            isSuffixNull = false;
            mSuffixTextWidth = mSuffixTextPaint.measureText(mSuffix);
        }

        boolean isSetSuffixDay = !TextUtils.isEmpty(mSuffixDay);
        boolean isSetSuffixHour = !TextUtils.isEmpty(mSuffixHour);
        boolean isSetSuffixMinute = !TextUtils.isEmpty(mSuffixMinute);
        boolean isSetSuffixSecond = !TextUtils.isEmpty(mSuffixSecond);
        boolean isSetSuffixMillisecond = !TextUtils.isEmpty(mSuffixMillisecond);

        if (isInit) {
            if ((isShowDay && isSetSuffixDay)
                    || (isShowHour && isSetSuffixHour)
                    || (isShowMinute && isSetSuffixMinute)
                    || (isShowSecond && isSetSuffixSecond)
                    || (isShowMillisecond && isSetSuffixMillisecond)) {
                hasCustomSomeSuffix = true;
            }
        }

        if (isShowDay) {
            if (isSetSuffixDay) {
                mSuffixDayTextWidth = mSuffixTextPaint.measureText(mSuffixDay);
            } else {
                if (!isSuffixNull) {
                    mSuffixDay = mSuffix;
                    mSuffixDayTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixDay = DEFAULT_SUFFIX;
                    mSuffixDayTextWidth = mDefSuffixTextWidth;
                }
            }
        } else {
            mSuffixDayTextWidth = 0;
        }

        if (isShowHour) {
            if (isSetSuffixHour) {
                mSuffixHourTextWidth = mSuffixTextPaint.measureText(mSuffixHour);
            } else {
                if (!isSuffixNull) {
                    mSuffixHour = mSuffix;
                    mSuffixHourTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixHour = DEFAULT_SUFFIX;
                    mSuffixHourTextWidth = mDefSuffixTextWidth;
                }
            }
        } else {
            mSuffixHourTextWidth = 0;
        }

        if (isShowMinute) {
            if (isSetSuffixMinute) {
                mSuffixMinuteTextWidth = mSuffixTextPaint.measureText(mSuffixMinute);
            } else if (isShowSecond) {
                if (!isSuffixNull) {
                    mSuffixMinute = mSuffix;
                    mSuffixMinuteTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixMinute = DEFAULT_SUFFIX;
                    mSuffixMinuteTextWidth = mDefSuffixTextWidth;
                }
            } else {
                mSuffixMinuteTextWidth = 0;
            }
        } else {
            mSuffixMinuteTextWidth = 0;
        }

        if (isShowSecond) {
            if (isSetSuffixSecond) {
                mSuffixSecondTextWidth = mSuffixTextPaint.measureText(mSuffixSecond);
            } else if (isShowMillisecond) {
                if (!isSuffixNull) {
                    mSuffixSecond = mSuffix;
                    mSuffixSecondTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixSecond = DEFAULT_SUFFIX;
                    mSuffixSecondTextWidth = mDefSuffixTextWidth;
                }
            } else {
                mSuffixSecondTextWidth = 0;
            }
        } else {
            mSuffixSecondTextWidth = 0;
        }

        if (isShowMillisecond && hasCustomSomeSuffix && isSetSuffixMillisecond) {
            mSuffixMillisecondTextWidth = mSuffixTextPaint.measureText(mSuffixMillisecond);
        } else {
            mSuffixMillisecondTextWidth = 0;
        }
    }

    /**
     * 初始化时间计量单位边距
     */
    private void initSuffixMargin() {
        int defSuffixLRMargin = dp2px(DEFAULT_SUFFIX_LR_MARGIN);
        boolean isSuffixLRMarginNull = true;

        if (mSuffixLRMargin >= 0) {
            isSuffixLRMarginNull = false;
        }

        if (isShowDay && mSuffixDayTextWidth > 0) {
            if (mSuffixDayLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayLeftMargin = defSuffixLRMargin;
                }
            }

            if (mSuffixDayRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayRightMargin = defSuffixLRMargin;
                }
            }
        } else {
            mSuffixDayLeftMargin = 0;
            mSuffixDayRightMargin = 0;
        }

        if (isShowHour && mSuffixHourTextWidth > 0) {
            if (mSuffixHourLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourLeftMargin = defSuffixLRMargin;
                }
            }

            if (mSuffixHourRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourRightMargin = defSuffixLRMargin;
                }
            }
        } else {
            mSuffixHourLeftMargin = 0;
            mSuffixHourRightMargin = 0;
        }

        if (isShowMinute && mSuffixMinuteTextWidth > 0) {
            if (mSuffixMinuteLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixMinuteLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixMinuteLeftMargin = defSuffixLRMargin;
                }
            }

            if (isShowSecond) {
                if (mSuffixMinuteRightMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixMinuteRightMargin = mSuffixLRMargin;
                    } else {
                        mSuffixMinuteRightMargin = defSuffixLRMargin;
                    }
                }
            } else {
                mSuffixMinuteRightMargin = 0;
            }
        } else {
            mSuffixMinuteLeftMargin = 0;
            mSuffixMinuteRightMargin = 0;
        }

        if (isShowSecond) {
            if (mSuffixSecondTextWidth > 0) {
                if (mSuffixSecondLeftMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixSecondLeftMargin = mSuffixLRMargin;
                    } else {
                        mSuffixSecondLeftMargin = defSuffixLRMargin;
                    }
                }

                if (isShowMillisecond) {
                    if (mSuffixSecondRightMargin < 0) {
                        if (!isSuffixLRMarginNull) {
                            mSuffixSecondRightMargin = mSuffixLRMargin;
                        } else {
                            mSuffixSecondRightMargin = defSuffixLRMargin;
                        }
                    }
                } else {
                    mSuffixSecondRightMargin = 0;
                }
            } else {
                mSuffixSecondLeftMargin = 0;
                mSuffixSecondRightMargin = 0;
            }

            if (isShowMillisecond  && mSuffixMillisecondTextWidth > 0) {
                if (mSuffixMillisecondLeftMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixMillisecondLeftMargin = mSuffixLRMargin;
                    } else {
                        mSuffixMillisecondLeftMargin = defSuffixLRMargin;
                    }
                }
            } else {
                mSuffixMillisecondLeftMargin = 0;
            }
        } else {
            mSuffixSecondLeftMargin = 0;
            mSuffixSecondRightMargin = 0;
            mSuffixMillisecondLeftMargin = 0;
        }
    }

    /**
     * 初始化时间的时候初始化RectF(矩形)
     */
    private void initTimeBgRect() {
        if (!isHideTimeBackground) {
            float mHourLeft;
            float mMinuteLeft;
            float mSecondLeft;

            if (isShowDay) {
                // 初始化天数的矩形背景
                mDayBgRectF = new RectF(mLeftPaddingSize, mTopPaddingSize, mLeftPaddingSize + mDayTimeBgWidth, mTopPaddingSize + mDayTimeBgWidth);
                mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                mHourLeft = mLeftPaddingSize;
            }

            if (isShowHour) {
                // 初始化小时的矩形背景
                mHourBgRectF = new RectF(mHourLeft, mTopPaddingSize, mHourLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
                mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                mMinuteLeft = mHourLeft;
            }

            if (isShowMinute) {
                // 初始化分钟的矩形背景
                mMinuteBgRectF = new RectF(mMinuteLeft, mTopPaddingSize, mMinuteLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
                mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                mSecondLeft = mMinuteLeft;
            }

            if (isShowSecond) {
                // 初始化秒速的矩形背景
                mSecondBgRectF = new RectF(mSecondLeft, mTopPaddingSize, mSecondLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);

                if (isShowMillisecond) {
                    float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;

                    mMillisecondBgRectF = new RectF(mMillisecondLeft, mTopPaddingSize, mMillisecondLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
                }
            }

            // 时间文本的基准线
            Paint.FontMetrics timeFontMetrics = mTimeTextPaint.getFontMetrics();
            mTimeTextBaseY = mSecondBgRectF.top + (mSecondBgRectF.bottom - mSecondBgRectF.top - timeFontMetrics.bottom + timeFontMetrics.top) / 2 - timeFontMetrics.top - mTimeTextBottom;

            // 初始化背景划分线Y点
            mTimeBgDivisionLineYPos = mSecondBgRectF.centerY() + (mTimeBgDivisionLineSize == dp2px(DEFAULT_TIME_BG_DIVISION_LINE_SIZE) ? mTimeBgDivisionLineSize : mTimeBgDivisionLineSize / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mContentAllWidth = getAllContentWidth();
        mContentAllHeight = isHideTimeBackground ? (int) mTimeTextHeight : (int) mTimeBgSize;

        mViewWidth = measureSize(1, mContentAllWidth, widthMeasureSpec);
        mViewHeight = measureSize(2, mContentAllHeight, heightMeasureSpec);

        setMeasuredDimension(mViewWidth, mViewHeight);

        initTimeTextBaselineAndTimeBgTopPadding();
        initLeftPaddingSize();
        initTimeBgRect();
    }

    /**
     * 初始化时间文本的基准线和时间背景的顶部内边距
     *
     */
    private void initTimeTextBaselineAndTimeBgTopPadding() {
        if (getPaddingTop() == getPaddingBottom()) {
            // 居中
            mTimeTextBaseline = mViewHeight / 2 + mTimeTextHeight / 2 - mTimeTextBottom;

            mTopPaddingSize = (mViewHeight - mContentAllHeight) / 2;
        } else {
            // 顶部内边距
            mTimeTextBaseline = mViewHeight - (mViewHeight - getPaddingTop()) + mTimeTextHeight - mTimeTextBottom;

            mTopPaddingSize = getPaddingTop();
        }

        if (isShowDay && mSuffixDayTextWidth > 0) {
            mSuffixDayTextBaseline = getSuffixTextBaseLine(mSuffixDay);
        }

        if (isShowHour && mSuffixHourTextWidth > 0) {
            mSuffixHourTextBaseline = getSuffixTextBaseLine(mSuffixHour);
        }

        if (isShowMinute && mSuffixMinuteTextWidth > 0) {
            mSuffixMinuteTextBaseline = getSuffixTextBaseLine(mSuffixMinute);
        }

        if (mSuffixSecondTextWidth > 0) {
            mSuffixSecondTextBaseline = getSuffixTextBaseLine(mSuffixSecond);
        }

        if (isShowMillisecond && mSuffixMillisecondTextWidth > 0) {
            mSuffixMillisecondTextBaseline = getSuffixTextBaseLine(mSuffixMillisecond);
        }
    }

    private float getSuffixTextBaseLine(String suffixText) {
        Rect tempRect = new Rect();
        mSuffixTextPaint.getTextBounds(suffixText, 0, suffixText.length(), tempRect);

        float ret;
        switch (mSuffixGravity) {
            case 0:
                // 顶部
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - mTimeTextHeight - tempRect.top;
                } else {
                    ret = mTopPaddingSize - tempRect.top;
                }
                break;
            default:
            case 1:
                // 居中
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - mTimeTextHeight / 2 + tempRect.height() / 2;
                } else {
                    ret = mTopPaddingSize + mTimeBgSize - mTimeBgSize / 2  + tempRect.height() / 2;
                }
                break;
            case 2:
                // 底部
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - tempRect.bottom;
                } else {
                    ret = mTopPaddingSize + mTimeBgSize - tempRect.bottom;
                }
                break;
        }

        return ret;
    }

    /**
     * 初始化视图左内边距
     */
    private void initLeftPaddingSize() {
        if (getPaddingLeft() == getPaddingRight()) {
            // 居中
            mLeftPaddingSize = (mViewWidth - mContentAllWidth) / 2;
        } else {
            // 左内边距
            mLeftPaddingSize = getPaddingLeft();
        }
    }

    /**
     * 测量view的尺寸
     * @param specType    1 width 2 height
     * @param contentSize all content view size
     * @param measureSpec spec
     * @return measureSize
     */
    private int measureSize(int specType, int contentSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(contentSize, specSize);
        } else {
            result = contentSize;

            if (specType == 1) {
                // 宽度
                result += (getPaddingLeft() + getPaddingRight());
            } else {
                // 高度
                result += (getPaddingTop() + getPaddingBottom());
            }
        }

        return result;
    }

    /**
     * 或许所有视图的宽度
     * @return 所有视图的宽度
     */
    private int getAllContentWidth() {
        float timeWidth = isHideTimeBackground ? mTimeTextWidth : mTimeBgSize;
        float width = (mSuffixDayTextWidth + mSuffixHourTextWidth + mSuffixMinuteTextWidth + mSuffixSecondTextWidth + mSuffixMillisecondTextWidth);
        width += (mSuffixDayLeftMargin + mSuffixDayRightMargin + mSuffixHourLeftMargin + mSuffixHourRightMargin
                + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin + mSuffixSecondLeftMargin + mSuffixSecondRightMargin + mSuffixMillisecondLeftMargin);

        if (isShowDay) {
            if (isDayLargeNinetyNine) {
                Rect rect = new Rect();
                String tempDay = String.valueOf(mDay);
                mTimeTextPaint.getTextBounds(tempDay, 0, tempDay.length(), rect);
                mDayTimeTextWidth = rect.width();

                if (!isHideTimeBackground) {
                    mDayTimeBgWidth = mDayTimeTextWidth + (dp2px(2) * 4);
                    width += mDayTimeBgWidth;
                } else {
                    width += mDayTimeTextWidth;
                }
            } else {
                mDayTimeTextWidth = mTimeTextWidth;
                mDayTimeBgWidth = mTimeBgSize;
                width += timeWidth;
            }
        }

        if (isShowHour) {
            width += timeWidth;
        }

        if (isShowMinute) {
            width += timeWidth;
        }

        if (isShowSecond) {
            width += timeWidth;
        }

        if (isShowMillisecond) {
            width += timeWidth;
        }

        return (int)Math.ceil(width);
    }

    private void refTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowSecond, boolean isShowMillisecond) {
        boolean isRef = false;

        if (this.isShowDay != isShowDay) {
            this.isShowDay = isShowDay;
            isRef = true;

            // 重置天数的边距
            if (isShowDay) {
                mSuffixDayLeftMargin = mTempSuffixDayLeftMargin;
                mSuffixDayRightMargin = mTempSuffixDayRightMargin;
            }
        }

        if (this.isShowHour != isShowHour) {
            this.isShowHour = isShowHour;
            isRef = true;

            // 重置小时的边距
            if (isShowHour) {
                mSuffixHourLeftMargin = mTempSuffixHourLeftMargin;
                mSuffixHourRightMargin = mTempSuffixHourRightMargin;
            }
        }

        if (this.isShowMinute != isShowMinute) {
            this.isShowMinute = isShowMinute;
            isRef = true;

            // 重置分钟的边距
            if (isShowMinute) {
                mSuffixMinuteLeftMargin = mTempSuffixMinuteLeftMargin;
                mSuffixMinuteRightMargin = mTempSuffixMinuteRightMargin;
                mSuffixMinute = mTempSuffixMinute;
            }
        }

        boolean isModCountdownInterval = false;

        if (this.isShowSecond != isShowSecond) {
            this.isShowSecond = isShowSecond;
            isRef = true;
            isModCountdownInterval = true;

            // 重置秒数的边距
            if (isShowSecond) {
                mSuffixSecondLeftMargin = mTempSuffixSecondLeftMargin;
                mSuffixSecondRightMargin = mTempSuffixSecondRightMargin;
                mSuffixSecond = mTempSuffixSecond;
            } else {
                mSuffixMinute = mTempSuffixMinute;
            }

            mSuffixMinuteLeftMargin = mTempSuffixMinuteLeftMargin;
            mSuffixMinuteRightMargin = mTempSuffixMinuteRightMargin;
        }

        if (this.isShowMillisecond != isShowMillisecond) {
            this.isShowMillisecond = isShowMillisecond;
            isRef = true;
            isModCountdownInterval = true;

            // 重置毫秒数的边距
            if (isShowMillisecond) {
                mSuffixMillisecondLeftMargin = mTempSuffixMillisecondLeftMargin;
            } else {
                mSuffixSecond = mTempSuffixSecond;
            }

            mSuffixSecondLeftMargin = mTempSuffixSecondLeftMargin;
            mSuffixSecondRightMargin = mTempSuffixSecondRightMargin;
        }

        // 通过判断修改时间倒计时的间隔
        if (isModCountdownInterval) {
            start(mRemainTime);
        }

        if (isRef) {
            initSuffix(false);
            initSuffixMargin();
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mHourLeft;
        float mMinuteLeft;
        float mSecondLeft;

        if (isHideTimeBackground) {

            if (isShowDay) {
                canvas.drawText(formatNum(mDay), mLeftPaddingSize + mDayTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixDayTextWidth > 0) {
                    canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeTextWidth + mSuffixDayLeftMargin, mSuffixDayTextBaseline, mSuffixTextPaint);
                }

                mHourLeft = mLeftPaddingSize + mDayTimeTextWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                mHourLeft = mLeftPaddingSize;
                mHour = mDay * 24 + mHour;
            }

            if (isShowHour) {
                canvas.drawText(formatNum(mHour), mHourLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixHourTextWidth > 0) {
                    canvas.drawText(mSuffixHour, mHourLeft + mTimeTextWidth + mSuffixHourLeftMargin, mSuffixHourTextBaseline, mSuffixTextPaint);
                }

                mMinuteLeft = mHourLeft + mTimeTextWidth + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                mMinuteLeft = mHourLeft;
            }

            if (isShowMinute) {
                canvas.drawText(formatNum(mMinute), mMinuteLeft + mTimeTextWidth / 2 , mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixMinuteTextWidth > 0) {
                    canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeTextWidth + mSuffixMinuteLeftMargin, mSuffixMinuteTextBaseline, mSuffixTextPaint);
                }

                mSecondLeft = mMinuteLeft + mTimeTextWidth + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                mSecondLeft = mMinuteLeft;
            }

            if (isShowSecond) {
                canvas.drawText(formatNum(mSecond), mSecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixSecondTextWidth > 0) {
                    canvas.drawText(mSuffixSecond, mSecondLeft + mTimeTextWidth + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
                }

                if (isShowMillisecond) {
                    float mMillisecondLeft = mSecondLeft + mTimeTextWidth + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                    canvas.drawText(formatMillisecond(), mMillisecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                    if (mSuffixMillisecondTextWidth > 0) {
                        canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeTextWidth + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                    }
                }
            }
        } else {

            if (isShowDay) {
                canvas.drawRoundRect(mDayBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    canvas.drawLine(mLeftPaddingSize, mTimeBgDivisionLineYPos, mLeftPaddingSize + mDayTimeBgWidth, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                canvas.drawText(formatNum(mDay), mDayBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixDayTextWidth > 0) {
                    canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayLeftMargin, mSuffixDayTextBaseline, mSuffixTextPaint);
                }

                mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                mHourLeft = mLeftPaddingSize;
                mHour = mDay * 24 + mHour;
            }

            if (isShowHour) {
                canvas.drawRoundRect(mHourBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    canvas.drawLine(mHourLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mHourLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                canvas.drawText(formatNum(mHour), mHourBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixHourTextWidth > 0) {
                    canvas.drawText(mSuffixHour, mHourLeft + mTimeBgSize + mSuffixHourLeftMargin, mSuffixHourTextBaseline, mSuffixTextPaint);
                }
                mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                mMinuteLeft = mHourLeft;
            }
            if (isShowMinute) {
                canvas.drawRoundRect(mMinuteBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    canvas.drawLine(mMinuteLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMinuteLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                canvas.drawText(formatNum(mMinute), mMinuteBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixMinuteTextWidth > 0) {
                    canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeBgSize + mSuffixMinuteLeftMargin, mSuffixMinuteTextBaseline, mSuffixTextPaint);
                }

                mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                mSecondLeft = mMinuteLeft;
            }

            if (isShowSecond) {
                canvas.drawRoundRect(mSecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    canvas.drawLine(mSecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mSecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                canvas.drawText(formatNum(mSecond), mSecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixSecondTextWidth > 0) {
                    canvas.drawText(mSuffixSecond, mSecondLeft + mTimeBgSize + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
                }

                if (isShowMillisecond) {
                    float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                    canvas.drawRoundRect(mMillisecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                    if (isShowTimeBgDivisionLine) {
                        canvas.drawLine(mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                    }
                    canvas.drawText(formatMillisecond(), mMillisecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                    if (mSuffixMillisecondTextWidth > 0) {
                        canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeBgSize + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                    }
                }
            }
        }
    }

    /**
     * View被销毁时调用停止倒计时方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 开始倒计时
     * @param millisecond 毫秒数
     */
    public void start(long millisecond) {
        if (millisecond <= 0) {
            return ;
        }

        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
            mCustomCountDownTimer = null;
        }

        long countDownInterval;
        if (isShowMillisecond) {
            countDownInterval = 10;
            updateShow(millisecond);
        } else {
            countDownInterval = 1000;
        }

        mCustomCountDownTimer = new CustomCountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                allShowZero();
                if (null != mOnCountdownEndListener) {
                    mOnCountdownEndListener.onEnd(CountdownView.this);
                }
            }
        };
        mCustomCountDownTimer.start();
    }

    /**
     * 停止倒计时
     */
    public void stop() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.stop();
    }

    /**
     * 暂停倒计时
     */
    public void pause() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.pause();
    }

    /**
     * 再次启动倒计时
     */
    public void restart() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.restart();
    }

    /**
     * 是否显示时间
     * @param isShowDay 是否显示天数
     * @param isShowHour 是否显示小时
     * @param isShowMinute 是否显示分钟
     * @param isShowSecond 是否显示秒速
     * @param isShowMillisecond 是否显示毫秒数
     */
    public void customTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowSecond, boolean isShowMillisecond) {
        mHasSetIsShowDay = true;
        mHasSetIsShowHour = true;

        if (!isShowMinute && !isShowSecond) {
            isShowSecond = true;
        }
        if (!isShowSecond) {
            isShowMillisecond = false;
        }

        refTimeShow(isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);
    }

    /**
     * 初始化所有时间
     */
    public void allShowZero() {
        mHour = 0;
        mMinute = 0;
        mSecond = 0;
        mMillisecond = 0;
        invalidate();//调用该方法实现屏幕刷新
    }

    /**
     * 设置倒计时结束时的监听
     * @param onCountdownEndListener OnCountdownEndListener
     */
    public void setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener) {
        this.mOnCountdownEndListener = onCountdownEndListener;
    }

    /**
     * 设置倒计时间隔的监听
     * @param interval 间隔时间
     * @param onCountdownIntervalListener OnCountdownIntervalListener
     */
    public void setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener) {
        this.mInterval = interval;
        this.mOnCountdownIntervalListener = onCountdownIntervalListener;
    }

    /**
     * 获取当前时间到指定时间差
     * @param lastDate
     * @return
     */
    public long getTime(String lastDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lastMillisecond = 0;
        try {
            lastMillisecond = sdf.parse(lastDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date curDate = new Date();
        long diff = lastMillisecond-curDate.getTime();
        return diff;
    }

    /**
     * get day
     * @return current day
     */
    public int getDay()  {
        return mDay;
    }

    /**
     * get hour
     * @return current hour
     */
    public int getHour() {
        return mHour;
    }

    /**
     * get minute
     * @return current minute
     */
    public int getMinute() {
        return mMinute;
    }

    /**
     * get second
     * @return current second
     */
    public int getSecond() {
        return mSecond;
    }

    /**
     * get remain time
     * @return remain time ( millisecond )
     */
    public long getRemainTime() {
        return mRemainTime;
    }

    public void updateShow(long ms) {
        this.mRemainTime = ms;

        mDay = (int)(ms / (1000 * 60 * 60 * 24));
        mHour = (int)((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        mMinute = (int)((ms % (1000 * 60 * 60)) / (1000 * 60));
        mSecond = (int)((ms % (1000 * 60)) / 1000);
        mMillisecond = (int)(ms % 1000);

        if (mInterval > 0 && null != mOnCountdownIntervalListener) {
            if (mPreviouIntervalCallbackTime == 0) {
                mPreviouIntervalCallbackTime = ms;
            } else if (ms + mInterval <= mPreviouIntervalCallbackTime) {
                mPreviouIntervalCallbackTime = ms;
                mOnCountdownIntervalListener.onInterval(this, mRemainTime);
            }
        }

        handlerAutoShowTimeAndDayLargeNinetyNine();

        invalidate();
    }

    private void handlerAutoShowTimeAndDayLargeNinetyNine() {
        if (!mHasSetIsShowDay) {
            if (!isShowDay && mDay > 0) {
                if (!mHasSetIsShowHour) {
                    refTimeShow(true, true, isShowMinute, isShowSecond, isShowMillisecond);
                } else {
                    refTimeShow(true, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);
                }
            } else if (isShowDay && mDay == 0) {
                refTimeShow(false, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);
            } else {
                if (!mHasSetIsShowHour) {
                    if (!isShowHour && (mDay > 0 || mHour > 0)) {
                        refTimeShow(isShowDay, true, isShowMinute, isShowSecond, isShowMillisecond);
                    } else if (isShowHour && mDay == 0 && mHour == 0) {
                        refTimeShow(false, false, isShowMinute, isShowSecond, isShowMillisecond);
                    }
                }
            }
        } else {
            if (!mHasSetIsShowHour) {
                if (!isShowHour && (mDay > 0 || mHour > 0)) {
                    refTimeShow(isShowDay, true, isShowMinute, isShowSecond, isShowMillisecond);
                } else if (isShowHour && mDay == 0 && mHour == 0) {
                    refTimeShow(isShowDay, false, isShowMinute, isShowSecond, isShowMillisecond);
                }
            }
        }

        if (isShowDay) {
            if (!isDayLargeNinetyNine && mDay > 99) {
                isDayLargeNinetyNine = true;
                requestLayout();
            } else if (isDayLargeNinetyNine && mDay <= 99) {
                isDayLargeNinetyNine = false;
                requestLayout();
            }
        }
    }

    private String formatNum(int time) {
        return time < 10 ? "0"+time : String.valueOf(time);
    }

    private String formatMillisecond() {
        String retMillisecondStr;

        if (mMillisecond > 99) {
            retMillisecondStr = String.valueOf(mMillisecond / 10);
        } else if (mMillisecond <= 9) {
            retMillisecondStr = "0" + mMillisecond;
        } else {
            retMillisecondStr = String.valueOf(mMillisecond);
        }

        return retMillisecondStr;
    }

    public interface OnCountdownEndListener {
        void onEnd(CountdownView cv);
    }

    public interface OnCountdownIntervalListener {
        void onInterval(CountdownView cv, long remainTime);
    }

    private int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private float sp2px(float spValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

    public void setTimeBgColor(int mTimeBgColor){
        mTimeTextBgPaint.setColor(mTimeBgColor);
    }


}
