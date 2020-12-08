package com.example.chenpeiwen.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private  TextureView mTextureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏、

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        getDisplayInfomation();

        if(true) {
            setContentView(R.layout.activity_main);
            mTextureView = (TextureView) findViewById(R.id.tv);
            mTextureView.setSurfaceTextureListener(this);


            LogUtil.LogInfo("mainac...bafore stringFromJNI:" + stringFromJNI());
            Intent intent = new Intent(MainActivity.this, CameraPreviewActivity.class);
            startActivity(intent);
            LogUtil.LogInfo("mainac...after stringFromJNI:" + stringFromJNI());

            return;
        }







        setContentView(R.layout.activity_main);
        if (null == savedInstanceState) {
            LogUtil.LogInfo("null == savedInstanceState");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }



        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }

    private void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LogUtil.LogInfo("the screen size is "+point.toString());
        getWindowManager().getDefaultDisplay().getRealSize(point);
        LogUtil.LogInfo("the screen real size is "+point.toString());
    }

// charlie : the screen size is Point(1080, 2139)
//charlie : the screen real size is Point(1080, 2340)


    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.LogInfo("onResume..");

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void drawColor(Surface surface,int color);
    public static native void yuv420p2rgba(byte[] yuv420p,
                                           int width,
                                           int height,
                                           byte[] rgba);

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        LogUtil.LogInfo("onSurfaceTextureAvailable");
        SurfaceTexture texture = mTextureView.getSurfaceTexture();
        assert texture != null;
        if(texture == null)
        {
            LogUtil.LogInfo("err:" + "texture null");
            return;
        }
        Surface surface = new Surface(texture);
        drawColor(surface, Color.RED);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
