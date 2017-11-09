package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.utilities.Constant;

/**
 * @Run: https://stackoverflow.com/questions/2335813/how-to-inflate-one-view-with-a-layout
 * => Inflate layout => DONE
 *
 * @Run: https://stackoverflow.com/questions/10776764/what-is-the-right-way-to-communicate-from-a-custom-view-to-the-activity-in-which
 * => Communicate between Custom View and Activity
 *
 * @Run: https://stackoverflow.com/questions/15444375/how-to-create-interface-between-fragment-and-adapter
 */

public class ToolbarLayout extends ConstraintLayout{

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.toolbar, this, true);
        ButterKnife.bind(this, view);
        initViews();
        initAttributes();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        // Default information Toolbar
        mToolbar.setTitle(Constant.ToolbarLayoutInfo._TITLE);
        mToolbar.setTitleTextColor(Color.parseColor(Constant.ToolbarLayoutInfo._TITLE_TEXT_COLOR));
        mToolbar.setBackgroundColor(Color.parseColor(Constant.ToolbarLayoutInfo._BACKGROUND_COLOR));
        // mToolbar.setNavigationIcon(R.drawable.delete);

        /**
         * @Run: https://stackoverflow.com/questions/23538929/android-menuitem-custom-layout
         * => Custom pop menu in Android
         *
         * @Run: com.captech.myapp.ui.views
         */
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.inflateMenu(R.menu.main_menu);
    }

    private void initAttributes() {
        /**
         * @Run: http://sonevalley.blogspot.com/2015/11/ripple-effect-touch-animation-for.html
         * => Animation Ripple in Android
         */
    }

    /**
     * @Run: https://stackoverflow.com/questions/38240628/get-title-textview-of-collapsing-toolbar-layout
     * => Custom: Done
     * @param toolbar
     * => mTitleTextView => Go to root Toolbar and you will see it.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextView getTextViewTitle(Toolbar toolbar) {
        try {
            Class<?> toolbarClass = Toolbar.class;
            Field titleTextViewField = toolbarClass.getDeclaredField("mTitleTextView");
            titleTextViewField.setAccessible(true);
            return (TextView) titleTextViewField.get(toolbar);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Run: https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
     * => Learn how to set name.
     *
     * @Run: main_menu
     */
    public Toolbar getToolbar() {
        return mToolbar;
        /*
         * mToolbar.setTitle();
         * mToolbar.setBackground();
         * mToolbar.setForeBackground();
         * mToolbar.set...
         */
    }

}
