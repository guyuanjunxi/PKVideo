package com.example.pkvideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;

public class VideoPlayerView extends FrameLayout {

    private VideoView mVideoView;
    private boolean mIsFull = false;//是否全屏
    private int lastWidth;//全屏圈的宽
    private int lastHeight;//全屏圈的高

    public VideoPlayerView(Context context) {
        super(context);
    }

    /**
     * 播放视频
     * @param url
     */
    public void play(String url){
        mVideoView = new VideoView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mVideoView,params);
        MediaController controller = new MediaController(getContext());
        mVideoView.setMediaController(controller);
        controller.setAnchorView(mVideoView);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.v("VIDEO","onPrepare()");
            }
        });

        mVideoView.setVideoPath(url);
        mVideoView.start();
    }

    /**
     * 暂停播放
     */
    public void pause(){
        if(mVideoView!=null){
            mVideoView.pause();
        }
    }

    /**
     * 继续播放
     */
    public void resume(){
        if(mVideoView!=null){
            mVideoView.start();
        }
    }

    /**
     * 前进或倒退几秒
     * @param second
     */
    public void  seek(int second){
        if(mVideoView!=null){
            mVideoView.seekTo(mVideoView.getCurrentPosition()+second*1000);
        }
    }

    /**
     * 获取当前播放进度
     * @return
     */
    public int getCurrentPosition(){
        if(mVideoView!=null){
            return mVideoView.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 全屏
     */
    public void fullScreen(){
        if(mIsFull){
            return;
        }
        final Activity  activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //做成横屏，并且全屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //留存现有的宽和高
                ViewGroup.LayoutParams params = mVideoView.getLayoutParams();
                lastWidth = params.width;
                lastHeight = params.height;
                //设置全屏宽高度
                params.width = LayoutParams.MATCH_PARENT;
                params.height = LayoutParams.MATCH_PARENT;

                setLayoutParams(params);
                mIsFull = true;
            }
        });

    }

    /**
     * 退出全屏
     */
    public void exitFullScreen(){
        if(!mIsFull){
            return;
        }
        final Activity  activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //做成竖屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //设置全屏宽高度
                ViewGroup.LayoutParams params = getLayoutParams();
                params.width = lastWidth;
                params.height = lastHeight;
                setLayoutParams(params);
                mIsFull = false;
            }
        });
    }

    /**
     * 返回按钮:先退出全屏
     * @return
     */
    public boolean onBack() {
        if(mIsFull){
            exitFullScreen();
            return true;
        }
        return false;
    }

}














