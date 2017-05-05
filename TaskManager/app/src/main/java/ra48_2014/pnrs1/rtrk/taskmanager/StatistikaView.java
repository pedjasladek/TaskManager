package ra48_2014.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
/**
 * Created by VELIKI on 5/4/2017.
 */

public class StatistikaView extends View {

    private Paint mPaint;

    /* Rectangle shapes for circles */

    private RectF redCircle = new RectF();
    private RectF yellowCircle = new RectF();
    private RectF greenCircle = new RectF();

    /* Final and start percentage */

    private int redFinalPerc = 68;
    private int greenFinalPerc = 90;
    private int yellowFinalPerc = 30;

    private int redDrawnPerc = 0;
    private int greenDrawnPerc = 0;
    private int yellowDrawnPerc = 0;

    /* Strings for viewing percentage in txt form */

    private String redPerc;
    private String yellowPerc;
    private String greenPerc;

    Animation animation = new Animation();

    private final String highTxt = "Zadaci visokog";
    private final String mediumTxt = "Zadaci srednjeg";
    private final String lowTxt = "Zadaci niskog";
    private final String allTxt = "prioriteta";

    public StatistikaView(Context context) {
        super(context);
        mPaint = new Paint();
        animation.execute();
    }



    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        /* Red circle coordinates */

        float leftRed = canvas.getWidth() * 1/3;
        float topRed = canvas.getHeight() / 10;
        float rightRed = canvas.getWidth() * 2/3;
        float bottomRed = canvas.getHeight() * 3 / 10;

        /* Green circle coordinates */

        float leftGreen = canvas.getWidth() / 12;
        float topGreen = canvas.getHeight() * 1/2;
        float rightGreen = canvas.getWidth() * 5 / 12;
        float bottomGreen = canvas.getHeight() * 7 / 10;

        /* Yellow circle coordinates */

        float leftYellow = canvas.getWidth() * 7 / 12;
        float topYellow = canvas.getHeight() * 1/2;
        float rightYellow = canvas.getWidth() * 11 / 12;
        float bottomYellow = canvas.getHeight() * 7 / 10;

        float density = getResources().getDisplayMetrics().density;

        /* Rectangles on which circles will be placed */

        redCircle.set(leftRed, topRed, rightRed, bottomRed);
        greenCircle.set(leftGreen, topGreen, rightGreen, bottomGreen);
        yellowCircle.set(leftYellow, topYellow, rightYellow, bottomYellow);

        /* Red circle */

        mPaint.setColor(Color.CYAN);
        canvas.drawOval(redCircle, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(redCircle, -90, (float)(redDrawnPerc * 3.6), true, mPaint);

        /* Green circle */

        mPaint.setColor(Color.CYAN);
        canvas.drawOval(greenCircle, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(greenCircle, -90, (float)(greenDrawnPerc * 3.6), true, mPaint);

        /* Yellow circle */

        mPaint.setColor(Color.CYAN);
        canvas.drawOval(yellowCircle, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(yellowCircle, -90, (float)(yellowDrawnPerc * 3.6), true, mPaint);

        /* Setting color and size of text */

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(15 * density);

        /* Text under circles */

        canvas.drawText(highTxt, 0, highTxt.length(),
                redCircle.centerX() - 50 * density, redCircle.centerY() + 85 * density, mPaint);
        canvas.drawText(allTxt, 0, allTxt.length(),
                redCircle.centerX() - 30 * density, redCircle.centerY() + 105 * density, mPaint);
        canvas.drawText(mediumTxt, 0, mediumTxt.length(),
                greenCircle.centerX() - 51 * density, greenCircle.centerY() + 85 * density, mPaint);
        canvas.drawText(allTxt, 0, allTxt.length(),
                greenCircle.centerX() - 30 * density, greenCircle.centerY() + 105 * density, mPaint);
        canvas.drawText(lowTxt, 0, lowTxt.length(),
                yellowCircle.centerX() - 45 * density, yellowCircle.centerY() + 85 * density, mPaint);
        canvas.drawText(allTxt, 0, allTxt.length(),
                yellowCircle.centerX() - 30 * density, yellowCircle.centerY() + 105 * density, mPaint);

        mPaint.setTextSize(25 * density);

        /*
            Percentage string
         */
        canvas.drawText(redPerc, 0, redPerc.length(),
                redCircle.centerX() - 20 * density, redCircle.centerY() + 11 * density, mPaint);
        canvas.drawText(yellowPerc, 0, yellowPerc.length(),
                yellowCircle.centerX() - 20 * density, yellowCircle.centerY() + 11 * density, mPaint);
        canvas.drawText(greenPerc, 0, greenPerc.length(),
                greenCircle.centerX() - 20 * density, greenCircle.centerY() + 11 * density, mPaint);

    }

    public class Animation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {

                while (redDrawnPerc < redFinalPerc || yellowDrawnPerc < yellowFinalPerc || greenDrawnPerc < greenFinalPerc) {

                    if (redDrawnPerc < redFinalPerc)
                        redDrawnPerc++;

                    if (yellowDrawnPerc < yellowFinalPerc)
                        yellowDrawnPerc++;

                    if (greenDrawnPerc < greenFinalPerc)
                        greenDrawnPerc++;

                    redPerc = Integer.toString(redDrawnPerc) + "%";
                    yellowPerc = Integer.toString(yellowDrawnPerc) + "%";
                    greenPerc = Integer.toString(greenDrawnPerc) + "%";

                    postInvalidate();
                    SystemClock.sleep(25);

                    if(isCancelled())
                        break;
                }
            return null;

        }
    }

}


