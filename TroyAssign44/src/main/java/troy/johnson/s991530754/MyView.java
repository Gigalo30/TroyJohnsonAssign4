package troy.johnson.s991530754;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    Paint BackPaint = new Paint();
    Context MyContext;

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        MyContext = ctx;
        BackPaint.setStyle(Paint.Style.FILL);
        BackPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w, h);
    }


// @Override

    protected void onDraw(Canvas canvas) {

        float w, h, cx, cy, radius;
        w = getWidth();
        h = getHeight();
        cx = w / 2;
        cy = h / 2;

        if (w > h) {
            radius = h / 4;
        } else {
            radius = w / 4;
        }

        // first rect
        canvas.drawRect(0, 0, w, h, BackPaint);

        Paint MyPaint = new Paint();
        MyPaint.setStyle(Paint.Style.FILL);

        int shaderColor0 = Color.RED;
        int shaderColor1 = Color.BLUE;
        int shaderColor2 = Color.GREEN;

        MyPaint.setAntiAlias(true);
        Shader linearGradientShader;


        linearGradientShader = new LinearGradient(
                0, 0, w, h,
                shaderColor1, shaderColor0, Shader.TileMode.MIRROR);

        // Second Rect
        MyPaint.setShader(linearGradientShader);
        canvas.drawRect(0, 0, w, h, MyPaint);

        Shader linearGradientShader1;
        linearGradientShader1 = new LinearGradient(
                cx, cy, cx + radius, cy + radius,
                shaderColor0, shaderColor2, Shader.TileMode.MIRROR);

        // circle
        MyPaint.setShader(linearGradientShader1);
        canvas.drawCircle(cx, cy, radius, MyPaint);

    }
}


