package fm.kirtsim.kharos.moviestop.customComponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fm.kirtsim.kharos.moviestop.R;

/**
 * Created by kharos on 07/02/2018
 */

public class ThumbnailTab extends LinearLayout {

    private static final String TAG = ThumbnailTab.class.getSimpleName();

    private TextView label;
    private ImageView image;

    public ThumbnailTab(Context context) {
        this(context, null);
    }

    public ThumbnailTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null)
            return;
        inflater.inflate(R.layout.thumbnail_tab, this, true);

        setOrientation(VERTICAL);
        label = findViewById(R.id.title);
        image = findViewById(R.id.image);

        if (attrs != null) {
            final TypedArray attArray = context.obtainStyledAttributes(attrs, R.styleable.ThumbnailTab);
            setLabel(attArray.getString(R.styleable.ThumbnailTab_label));
            setImage(attArray.getDrawable(R.styleable.ThumbnailTab_image));
            setImageUrl(attArray.getString(R.styleable.ThumbnailTab_imageUrl));
            attArray.recycle();
        }

    }

    public void setLabel(String labelText) {
        label.setText(labelText);
    }

    public void setImage(Drawable drawable) {
        image.setImageDrawable(drawable);
    }

    public void setImage(@DrawableRes int drawableRes) {
        image.setBackgroundResource(drawableRes);
    }

    public void setImageUrl(String url) {
        Glide.with(getContext())
                .load(url)
                .into(image);
    }
}
