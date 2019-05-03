package iran.alirezanazari.emojiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class EmojiView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mFaceColor   = getColor(R.color.face_color) ;
    private int mEyeColor    = Color.BLACK;
    private int mMouthColor  = Color.BLACK;
    private int mBorderColor = Color.BLACK;
    private EmojiState mEmojiState = EmojiState.HAPPY  ;
    private float mBorderWidth = 4.0f;
    private int mViewSize = 256 ;


    public EmojiView(Context context) {
        super(context);
    }

    public EmojiView(Context context , AttributeSet attrs) {
        super(context , attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiView);
        mFaceColor = typedArray.getColor(R.styleable.EmojiView_ev_faceColor, mFaceColor);
        mEyeColor = typedArray.getColor(R.styleable.EmojiView_ev_eyesColor, mEyeColor);
        mMouthColor = typedArray.getColor(R.styleable.EmojiView_ev_mouthColor, mMouthColor);
        mBorderColor = typedArray.getColor(R.styleable.EmojiView_ev_borderColor, mBorderColor);
        mBorderWidth = typedArray.getFloat(R.styleable.EmojiView_ev_borderWidth, mBorderWidth);
        mEmojiState = EmojiState.get(typedArray.getInt(R.styleable.EmojiView_ev_emojiState, 0));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec , int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec , heightMeasureSpec);

        mViewSize = Math.min(getMeasuredWidth() , getMeasuredHeight());
        setMeasuredDimension(mViewSize , mViewSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFaceBackground(canvas);
        drawEyes(canvas);
        drawMouth(canvas);

    }

    private void drawFaceBackground(Canvas canvas){

        mPaint.setColor(mFaceColor);
        mPaint.setStyle(Paint.Style.FILL);
        float radius = mViewSize / 2f ;
        canvas.drawCircle(mViewSize / 2f , mViewSize / 2 , radius , mPaint);

        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        canvas.drawCircle(mViewSize / 2f , mViewSize / 2f , radius - (mBorderWidth / 2f)  , mPaint);

    }

    private void drawEyes(Canvas canvas){

        mPaint.setColor(mEyeColor);
        mPaint.setStyle(Paint.Style.FILL);

        RectF leftEye = new RectF(
                mViewSize * 0.22f , mViewSize * 0.32f ,
                mViewSize * 0.33f , mViewSize * 0.43f
        );

        RectF rightEye = new RectF(
                mViewSize * 0.62f , mViewSize * 0.32f ,
                mViewSize * 0.73f , mViewSize * 0.43f
        );

        canvas.drawOval(leftEye , mPaint);
        canvas.drawOval(rightEye , mPaint);

    }

    private void drawMouth(Canvas canvas){

        mPaint.setColor(mMouthColor);
        mPaint.setStyle(Paint.Style.FILL);

        Path mouthPath = new Path();
        mouthPath.moveTo(mViewSize * 0.22f  ,  mViewSize * 0.7f );

        switch (mEmojiState){

            case HAPPY:

                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.75f ,
                        mViewSize * 0.78f , mViewSize * 0.7f
                );
                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.9f ,
                        mViewSize * 0.22f , mViewSize * 0.7f
                );

                break;

            case NORMAL:
                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.7f ,
                        mViewSize * 0.78f , mViewSize * 0.7f
                );
                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.78f ,
                        mViewSize * 0.22f , mViewSize * 0.7f
                );
                break;

            case SAD:
                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.45f ,
                        mViewSize * 0.78f , mViewSize * 0.7f
                );
                mouthPath.quadTo(
                        mViewSize * 0.5f , mViewSize * 0.6f ,
                        mViewSize * 0.22f , mViewSize * 0.7f
                );
                break;
        }

        canvas.drawPath(mouthPath , mPaint);

    }

    private int getColor(int color) {
        return getContext().getResources().getColor(color);
    }

    public enum EmojiState {
         HAPPY , NORMAL , SAD;


        public static EmojiState get(int id){
            switch (id){
                case 0 :
                    return HAPPY;

                case 1 :
                    return NORMAL;

                case 2 :
                    return SAD;

                default:
                        return HAPPY;
            }
        }
    }

}
