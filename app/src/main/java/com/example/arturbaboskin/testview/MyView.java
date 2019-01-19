package com.example.arturbaboskin.testview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView
        implements SurfaceHolder.Callback {

    private float x = 500;
    private float y = 500;
    private DrawThread thread;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            x = event.getX();
            y = event.getY();
        }
        return true;
    }



    public MyView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new DrawThread(surfaceHolder);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setStart(false);
    }

    class DrawThread extends Thread{
        private SurfaceHolder holder;
        private boolean startFlag = true;

        public void setStart(boolean flag){
            startFlag = flag;
        }

        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            Canvas canvas;
            while (startFlag){
                try {
                    canvas = holder.lockCanvas();
                    canvas.drawColor(getResources()
                            .getColor(R.color.colorAccent));
                    canvas.drawCircle(x, y, 100, paint);
                    holder.unlockCanvasAndPost(canvas);
                }catch (Exception e){
                    startFlag = false;
                }
            }
        }
    }

}
