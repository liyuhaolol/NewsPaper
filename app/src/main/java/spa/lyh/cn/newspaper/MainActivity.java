package spa.lyh.cn.newspaper;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.util.ArrayList;

import cns.workspace.lib.androidsdk.toast.Toast;
import spa.lyh.cn.lib_largeimageview.LargeImageView;
import spa.lyh.cn.lib_largeimageview.OnImageRectListener;


public class MainActivity extends AppCompatActivity {

    private PhotoPagerAdapter photoPagerAdapter;
    private ArrayList<Newspaper> imgUrls;
    private boolean isFullscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setSystemUiVisibility(getWindow().getDecorView(),View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,true);

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        //下面图2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().setAttributes(lp);
        //setTranslucent();
        initViewPager();
    }

    private void initViewPager() {
        final ViewPager2 viewPager = findViewById(R.id.viewpager2);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //Log.e("liyuhao","onPageScrolled->position:"+position);
                //Log.e("liyuhao","onPageScrolled->positionOffset:"+positionOffset);
                //Log.e("liyuhao","onPageScrolled->positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //Log.e("liyuhao","onPageSelected:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        /*File file = new File("/sdcard/A01.jpg");
        if (file.exists()){
            Log.e("liyuhao","文件存在");
        }*/


        imgUrls = new ArrayList<>();
        Newspaper newspaper1 = new Newspaper();
        newspaper1.setUrl("http://paperstatic.uschinapress.com/repo/image/newspaperNo/2019/10/31/aebfde85-6722-4cf2-a915-1688a71d70ca/1031/A02最美国.jpg");
        imgUrls.add(newspaper1);
        Newspaper newspaper2 = new Newspaper();
        newspaper2.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572952809598&di=6c26006f0286eab62f8fc60db3bf5d0e&imgtype=0&src=http%3A%2F%2Fimage.51bidlive.com%2Fu%2F20180613%2F0247.jpg");
        imgUrls.add(newspaper2);
        Newspaper newspaper3 = new Newspaper();
        newspaper3.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577506390392&di=027a73efa7a86f66afc4b682d418cddd&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Forj360%2F007kVUJhly1g1inspvu5zj30v93rbnpd.jpg");
        imgUrls.add(newspaper3);

        photoPagerAdapter = new PhotoPagerAdapter(this,imgUrls);
        viewPager.setAdapter(photoPagerAdapter);
        //viewPager.setCurrentItem(3);
        /*new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(4);
            }
        },2000);*/
        setFullscreen(true);
        photoPagerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*isFullscreen = !isFullscreen;
                setFullscreen(isFullscreen);*/
                LargeImageView imageView = (LargeImageView) view;
                /*Log.e("liyuhao","Scale:"+imageView.getScale());
                Log.e("liyuhao","Width:"+imageView.getImageWidth());
                Log.e("liyuhao","Height:"+imageView.getImageHeight());
                Log.e("liyuhao","TouchX:"+imageView.getTouchX());
                Log.e("liyuhao","TouchY:"+imageView.getTouchY());*/
                /*Log.e("liyuhao","getCurrentItem:"+imgUrls.get(viewPager.getCurrentItem()).getLeft());
                Log.e("liyuhao","getCurrentItem:"+imgUrls.get(viewPager.getCurrentItem()).getTop());
                Log.e("liyuhao","getCurrentItem:"+imgUrls.get(viewPager.getCurrentItem()).getRight());
                Log.e("liyuhao","getCurrentItem:"+imgUrls.get(viewPager.getCurrentItem()).getBottom());*/
                syncData(imageView.getWidth(),
                        imageView.getImageWidth(),
                        imageView.getImageHeight(),
                        imgUrls.get(viewPager.getCurrentItem()).getLeft(),
                        imgUrls.get(viewPager.getCurrentItem()).getTop(),
                        imgUrls.get(viewPager.getCurrentItem()).getRight(),
                        imgUrls.get(viewPager.getCurrentItem()).getBottom(),
                        imageView.getTouchX(),
                        imageView.getTouchY());
            }
        });

        /*photoPagerAdapter.setImageRectListener(new OnImageRectListener() {
            @Override
            public void getRect(int index, int left, int top, int right, int bottom) {
                //viewPager.getCurrentItem()
                Log.e("liyuhao","getCurrentItem:"+viewPager.getCurrentItem());
                Log.e("liyuhao","index:"+index);
            }
        });*/
    }

    private void syncData(int viewWidth,
                          int picWidth,
                          int picHeight,
                          int left,
                          int top,
                          int right,
                          int bottom,
                          int touchX,
                          int touchY){
        int widthSafe = 0;
        int heightSafe = 0;
        if (right > picWidth){
            widthSafe = (right - picWidth)/2;
        }
        if (bottom > picHeight){
            heightSafe = (bottom - picHeight)/2;
        }
        float scale = (float) (right - left) / (float)viewWidth;
        int x = (int) (scale * touchX) - widthSafe + left;
        int y = (int) (scale * touchY) - heightSafe + top;

        if (x >= 0 && x <= picWidth && y >= 0 && y <= picHeight){
            //点在图片里
            Log.e("liyuhao","X:"+x);
            Log.e("liyuhao","Y:"+y);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setFullscreen(isFullscreen);
    }

    private void setFullscreen(boolean isFullscreen){
        setSystemUiVisibility(getWindow().getDecorView(),View.SYSTEM_UI_FLAG_FULLSCREEN,isFullscreen);
    }

    /**
     * 非纯色状态栏，比如用图片进入状态栏位置，使用这个方法。如果纯色状态栏使用这个方法，效果与上面一致，但是不再
     * 兼容换肤框架，状态栏颜色需要手动控制。
     */
    public void setTranslucent(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        /*setSystemUiVisibility(window.getDecorView(),
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,
                true);*/
        setSystemUiVisibility(window.getDecorView(),
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,
                true);
        /*setSystemUiVisibility(getWindow().getDecorView(),
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
                true);*/
    }


    /**
     * 设置显示的样式
     * @param decorView
     * @param visibility
     */
    public void setSystemUiVisibility(View decorView, int visibility, boolean isAddVisibility){
        int oldVis = decorView.getSystemUiVisibility();
        int newVis = oldVis;
        if (isAddVisibility){
            newVis |= visibility;
        }else {
            newVis &= ~visibility;
        }
        if (newVis != oldVis) {
            decorView.setSystemUiVisibility(newVis);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
