package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lorence on 14/12/2017.
 *
 */

public class TriangleImageView extends ImageView {

    public TriangleImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        Bitmap roundBitmap = getRoundedCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {

        // Step 1: Create a new object Bitmap
        Bitmap finalBitmap;

        /**
         * Step 2: Scale image into square with width = height (If original size > 1024)
         * Apply: Triangle and circle
         */
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        else
            finalBitmap = bitmap;

        // Step 3,3.7: Create Rect
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

        // Step 4: Create Canvas
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);

        // Step 5: Create Paint
        Paint paint = new Paint();
        Point point1_draw = new Point(75, 0);
        Point point2_draw = new Point(0, 180);
        Point point3_draw = new Point(180, 180);
        Path path = new Path();
        path.moveTo(point1_draw.x, point1_draw.y);
        path.lineTo(point2_draw.x, point2_draw.y);
        path.lineTo(point3_draw.x, point3_draw.y);
        path.lineTo(point1_draw.x, point1_draw.y);
        path.close();
        paint.setColor(Color.parseColor("#BAB399"));

        // Step 6: => Expected: => drawPath()
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Step 7:
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;
    }

}
