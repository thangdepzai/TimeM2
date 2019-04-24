package hust.edu.vn.timem.CalendarUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CalendarViewSpan implements LineBackgroundSpan {
    String text;
    int color;

    public CalendarViewSpan(String text,int color ) {
        this.text = text;
        this.color = color;

    }
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        text = this.text;
        int oldColor = p.getColor();
        if (color != 0) {
            p.setColor(color);
        }
        c.drawText(String.valueOf(text), (left+right)/2, bottom+30, p );
        p.setColor(oldColor);
    }
}
