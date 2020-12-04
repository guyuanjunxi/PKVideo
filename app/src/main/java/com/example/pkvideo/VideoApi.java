package com.example.pkvideo;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoApi {

    private Context mContext;
    private boolean mIsAttach = false;//是否在播放
    private static VideoPlayerView mVideoPlay;

    public VideoApi(Context context){
        mContext = context;
        mVideoPlay =  new VideoPlayerView(mContext);
    }


    /**
     * 请求播放视屏，并且添加VideoView到当前View中
     */
    @JavascriptInterface
    public String play(Object msg){
        if(mIsAttach){
            return "video already play.";
        }
        String url = "";
        int width = 0;
        int height = 0;
        try{
            JSONObject videoInfo = new JSONObject((String)msg);
            url = videoInfo.getString("url");
            width = videoInfo.getInt("width");
            height = videoInfo.getInt("height");
        }catch (Exception e){
        }
        //将VideoPlay视图添加到View中
        final Activity acticity = (Activity)mContext;
        final int finalWidth = width;
        final int finalHeight = height;
        final String finalUrl = url;
        acticity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout = acticity.findViewById(R.id.root_view);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(finalWidth, finalHeight);
                frameLayout.addView(mVideoPlay, params);
                mIsAttach = true;
                mVideoPlay.play(finalUrl);
            }
        });
        return "play success";
    }

    /**
     * 暂停播放
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String pause(Object msg){
        if(mVideoPlay!=null){
            mVideoPlay.pause();
        }
        return "call pause success";
    }

    /**
     * 继续播放
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String resume(Object msg){
        if(mVideoPlay!=null){
            mVideoPlay.resume();
        }
        return "call resume success";
    }

    /**
     * 后退或前进
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String seek(Object msg){
        if(mVideoPlay!=null){
            mVideoPlay.seek(Integer.valueOf((String)msg));
        }
        return "call seek success";
    }

    /**
     * 全屏
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String fullScreen(Object msg){
        if(mVideoPlay!=null){
            mVideoPlay.fullScreen();
        }
        return "call fullScreen success";
    }

    /**
     * 退出全屏
     * @param msg
     * @return
     */
    @JavascriptInterface
    public String exitFullScreen(Object msg){
        if(mVideoPlay!=null){
            mVideoPlay.exitFullScreen();
        }
        return "call exitFullScreen success";
    }


    /**
     * android返回按钮：先退出全屏
     * @return
     */
    public static boolean onBackPressed() {
        if(mVideoPlay!=null){
            return mVideoPlay.onBack();
        }
        return false;
    }

}





















