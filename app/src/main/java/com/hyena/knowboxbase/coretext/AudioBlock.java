//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.text.TextUtils;
import android.view.animation.LinearInterpolator;
import com.hyena.knowboxbase.coretext.TextEnv;
import com.hyena.knowboxbase.coretext.blocks.CYPlaceHolderBlock;
import com.hyena.framework.audio.MusicDir;
import com.hyena.framework.audio.bean.Song;
import com.hyena.framework.download.DownloadManager;
import com.hyena.framework.download.Task;
import com.hyena.framework.download.Task.TaskListener;
import com.hyena.framework.security.MD5Util;
import com.hyena.framework.servcie.audio.PlayerBusService;
import com.hyena.framework.servcie.audio.listener.PlayStatusChangeListener;
import com.hyena.framework.utils.ImageFetcher;
import com.hyena.framework.utils.ToastUtils;
import com.hyena.framework.utils.UiThreadHandler;
import com.hyena.framework.utils.AnimationUtils.ValueAnimatorListener;
import com.knowbox.base.R.drawable;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class AudioBlock extends CYPlaceHolderBlock {
    private AudioManager audioManager;
    private PlayerBusService mPlayBusService;
    private Paint mPaint = new Paint(1);
    private DownloadManager mDownloadManager;
    private String mSongUrl;
    private boolean mIsPlaying = false;
    private boolean mIsDownloading = false;
    int mProgress = 0;
    private Bitmap mBitmap;
    private static String mPlayingSongUri = "";
    private RectF mContentRect = new RectF();
    private RectF mRect = new RectF();
    private TaskListener mTaskListener = new TaskListener() {
        public void onReady(Task task) {
            String taskId = AudioBlock.this.mDownloadManager.buildTaskId(AudioBlock.this.mSongUrl);
            if(taskId.equals(task.getTaskId())) {
                AudioBlock.this.onDownloadStateChange(true, task.getRemoteUrl(), 0);
            }

        }

        public void onStart(Task task, long l, long l1) {
        }

        public void onProgress(Task task, long l, long l1) {
            AudioBlock.this.updateProgress(task);
        }

        public void onComplete(Task task, int reason) {
            String taskId = AudioBlock.this.mDownloadManager.buildTaskId(AudioBlock.this.mSongUrl);
            if(!TextUtils.isEmpty(taskId) && taskId.equals(task.getTaskId())) {
                AudioBlock.this.mIsDownloading = false;
                AudioBlock.this.onDownloadStateChange(false, task.getRemoteUrl(), reason);
                if(reason == 0) {
                    if(!TextUtils.isEmpty(AudioBlock.mPlayingSongUri) && AudioBlock.this.mDownloadManager.buildTaskId(AudioBlock.mPlayingSongUri).equals(taskId)) {
                        AudioBlock.this.play();
                    }

                    AudioBlock.this.postInvalidateThis();
                } else {
                    UiThreadHandler.post(new Runnable() {
                        public void run() {
                            ToastUtils.showToast(AudioBlock.this.getTextEnv().getContext(), "音频下载失败，请点击重试!");
                        }
                    });
                }
            }

        }
    };
    private PlayStatusChangeListener mPlayStatusChangeListener = new PlayStatusChangeListener() {
        public void onStatusChange(Song song, int status) {
            if(song != null && AudioBlock.this.mSongUrl != null && AudioBlock.this.mSongUrl.equals(song.getUrl())) {
                switch(status) {
                case -1:
                case 5:
                case 6:
                case 7:
                    if(!AudioBlock.this.mIsPlaying) {
                        return;
                    }

                    AudioBlock.this.onPlayingStateChange(false, song.getUrl());
                case 0:
                case 1:
                case 2:
                case 3:
                case 8:
                default:
                    break;
                case 4:
                    if(AudioBlock.this.mIsPlaying) {
                        return;
                    }

                    AudioBlock.this.onPlayingStateChange(true, song.getUrl());
                }

            } else {
                AudioBlock.this.onPlayingStateChange(false, "");
            }
        }
    };
    private ValueAnimator mCurrentAnim;

    public AudioBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.init(content);
    }

    public static void clear() {
        mPlayingSongUri = "";
    }

    private void init(String content) {
        this.audioManager = (AudioManager)this.getTextEnv().getContext().getSystemService("audio");
        this.mPlayBusService = (PlayerBusService)this.getTextEnv().getContext().getSystemService("player_bus");
        this.mPlayBusService.getPlayerBusServiceObserver().addPlayStatusChangeListener(this.mPlayStatusChangeListener);
        this.mDownloadManager = DownloadManager.getDownloadManager();
        this.mDownloadManager.addTaskListener(this.mTaskListener);
        this.mBitmap = this.getStartPlayBitmap();
        if(this.mBitmap != null) {
            this.setWidth(this.mBitmap.getWidth());
            this.setHeight(this.mBitmap.getHeight() + this.getPaddingTop() + this.getPaddingBottom());

            try {
                JSONObject e = new JSONObject(content);
                this.mSongUrl = e.optString("src");
                if(!TextUtils.isEmpty(this.mSongUrl)) {
                    if(this.mSongUrl.indexOf("?") != -1) {
                        this.mSongUrl = this.mSongUrl + "&tag=" + this.getTextEnv().getTag();
                    } else {
                        this.mSongUrl = this.mSongUrl + "?tag=" + this.getTextEnv().getTag();
                    }
                }

                String taskId = this.mDownloadManager.buildTaskId(this.mSongUrl);
                Task task = this.mDownloadManager.getTaskById(taskId);
                if(task != null) {
                    int status = task.getStatus();
                    if(status != 4 && status != 1 && status != 2) {
                        if(status == 6 && this.mSongUrl != null && this.mSongUrl.equals(mPlayingSongUri)) {
                            this.onPlayingStateChange(true, this.mSongUrl);
                        }
                    } else {
                        this.mProgress = task.getProgress();
                        this.onDownloadStateChange(true, this.mSongUrl, 0);
                    }
                }
            } catch (JSONException var6) {
                var6.printStackTrace();
            }

        } else {
            throw new RuntimeException("start play bitmap must be not null!!!");
        }
    }

    protected Bitmap getStartPlayBitmap() {
        return ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_play_3);
    }

    protected Bitmap[] getPlayingBitmaps() {
        return new Bitmap[]{ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_play_1), ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_play_2), ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_play_3)};
    }

    protected Bitmap[] getDownloadBitmaps() {
        return new Bitmap[]{ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_download_1), ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_download_2), ImageFetcher.getImageFetcher().loadImageSync("drawable://" + drawable.song_download_3)};
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.mContentRect.set(this.getContentRect());
        this.drawBitmap(canvas, this.mBitmap);
    }

    protected void drawBitmap(Canvas canvas, Bitmap bitmap) {
        if(bitmap != null && !bitmap.isRecycled()) {
            float left = this.mContentRect.left + (this.mContentRect.width() - (float)bitmap.getWidth()) / 2.0F;
            float top = this.mContentRect.top + (this.mContentRect.height() - (float)bitmap.getHeight()) / 2.0F;
            float right = left + (float)bitmap.getWidth();
            float bottom = top + (float)bitmap.getHeight();
            this.mRect.set(left, top, right, bottom);
            canvas.drawBitmap(bitmap, (Rect)null, this.mRect, this.mPaint);
        }
    }

    public boolean onTouchEvent(int action, float x, float y) {
        super.onTouchEvent(action, x, y);
        switch(action) {
        case 0:
            this.playOrPause();
        default:
            return super.onTouchEvent(action, x, y);
        }
    }

    private void download() {
        try {
            if(!TextUtils.isEmpty(this.mSongUrl)) {
                String e = this.mDownloadManager.buildTaskId(this.mSongUrl);
                this.mDownloadManager.downloadUrl(e, "urltask", this.mSongUrl, this.getSongFile().getAbsolutePath());
            } else {
                UiThreadHandler.post(new Runnable() {
                    public void run() {
                        ToastUtils.showToast(AudioBlock.this.getTextEnv().getContext(), "音频路径非法!");
                    }
                });
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    protected void playOrPause() {
        if(!TextUtils.isEmpty(this.mSongUrl)) {
            if(this.mIsPlaying) {
                try {
                    this.mPlayBusService.pause();
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            } else {
                String taskId = this.mDownloadManager.buildTaskId(this.mSongUrl);
                Task task = this.mDownloadManager.getTaskById(taskId);
                if(task != null) {
                    int status = task.getStatus();
                    if(status != 4 && status != 1 && status != 2) {
                        if(status == 6) {
                            mPlayingSongUri = this.mSongUrl;
                            this.play();
                        } else {
                            mPlayingSongUri = this.mSongUrl;
                            this.download();
                        }
                    }
                } else {
                    mPlayingSongUri = this.mSongUrl;
                    this.download();
                }
            }

        }
    }

    private void play() {
        try {
            this.checkVoice();
            Song e = new Song(false, this.mSongUrl, this.getSongFile().getAbsolutePath());
            this.mPlayBusService.play(e);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private File getSongFile() {
        String fileName = MD5Util.encode(this.mSongUrl);
        return new File(MusicDir.getMusicDir(), fileName);
    }

    public void release() {
        super.release();
        if(this.mPlayBusService != null) {
            this.mPlayBusService.getPlayerBusServiceObserver().removemPlayStatusChangeListener(this.mPlayStatusChangeListener);
        }

        if(this.mDownloadManager != null) {
            this.mDownloadManager.removeTaskListener(this.mTaskListener);
        }

        if(this.mCurrentAnim != null) {
            this.mCurrentAnim.cancel();
        }

    }

    protected void updateProgress(Task task) {
        String taskId = this.mDownloadManager.buildTaskId(this.mSongUrl);
        if(taskId.equals(task.getTaskId()) && task.isPercentChange()) {
            this.mProgress = (int)((float)task.getProgress() * 100.0F / (float)task.getTotalLen());
            this.mIsDownloading = true;
            this.postInvalidateThis();
        }

    }

    protected void onPlayingStateChange(boolean isPlaying, String playingUri) {
        if(isPlaying) {
            this.mIsPlaying = true;
            mPlayingSongUri = playingUri;
        } else {
            this.mIsPlaying = false;
            mPlayingSongUri = "";
        }

        this.startOrPauseSoundAnim();
        this.postInvalidateThis();
    }

    protected void onDownloadStateChange(boolean isDownloading, String audioUri, int reason) {
        this.mIsDownloading = isDownloading;
        if(isDownloading) {
            mPlayingSongUri = audioUri;
        }

        this.startOrCompleteDownloadAnim();
        this.postInvalidateThis();
    }

    protected boolean isPlaying() {
        return this.mIsPlaying;
    }

    protected boolean isDownloading() {
        return this.mIsDownloading;
    }

    protected void startOrCompleteDownloadAnim() {
        UiThreadHandler.post(new Runnable() {
            public void run() {
                if(AudioBlock.this.mCurrentAnim != null) {
                    AudioBlock.this.mCurrentAnim.cancel();
                }

                if(AudioBlock.this.isDownloading()) {
                    AudioBlock.this.mCurrentAnim = ValueAnimator.ofInt(new int[]{0, AudioBlock.this.getDownloadBitmaps().length});
                    AudioBlock.this.mCurrentAnim.setRepeatCount(-1);
                    AudioBlock.this.mCurrentAnim.setDuration(1000L);
                    AudioBlock.this.mCurrentAnim.setInterpolator(new LinearInterpolator());
                    ValueAnimatorListener listener = new ValueAnimatorListener() {
                        private int mIndex = -1;

                        public void onAnimationStart(Animator animator) {
                        }

                        public void onAnimationEnd(Animator animator) {
                            AudioBlock.this.mBitmap = AudioBlock.this.getStartPlayBitmap();
                            AudioBlock.this.postInvalidateThis();
                        }

                        public void onAnimationRepeat(Animator animator) {
                        }

                        public void onAnimationCancel(Animator animator) {
                            AudioBlock.this.mBitmap = AudioBlock.this.getStartPlayBitmap();
                            AudioBlock.this.postInvalidateThis();
                        }

                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer index = (Integer)valueAnimator.getAnimatedValue();
                            if(this.mIndex != index.intValue()) {
                                AudioBlock.this.mBitmap = AudioBlock.this.getDownloadBitmaps()[index.intValue()];
                                AudioBlock.this.postInvalidateThis();
                                this.mIndex = index.intValue();
                            }

                        }
                    };
                    AudioBlock.this.mCurrentAnim.addUpdateListener(listener);
                    AudioBlock.this.mCurrentAnim.addListener(listener);
                    AudioBlock.this.mCurrentAnim.start();
                }

            }
        });
    }

    protected void startOrPauseSoundAnim() {
        UiThreadHandler.post(new Runnable() {
            public void run() {
                if(AudioBlock.this.mCurrentAnim != null) {
                    AudioBlock.this.mCurrentAnim.cancel();
                }

                if(AudioBlock.this.isPlaying()) {
                    AudioBlock.this.mCurrentAnim = ValueAnimator.ofInt(new int[]{0, AudioBlock.this.getPlayingBitmaps().length});
                    AudioBlock.this.mCurrentAnim.setRepeatCount(-1);
                    AudioBlock.this.mCurrentAnim.setDuration(1000L);
                    AudioBlock.this.mCurrentAnim.setInterpolator(new LinearInterpolator());
                    ValueAnimatorListener listener = new ValueAnimatorListener() {
                        private int mCurrentIndex = -1;

                        public void onAnimationStart(Animator animator) {
                        }

                        public void onAnimationEnd(Animator animator) {
                            AudioBlock.this.mBitmap = AudioBlock.this.getStartPlayBitmap();
                            AudioBlock.this.postInvalidateThis();
                        }

                        public void onAnimationRepeat(Animator animator) {
                        }

                        public void onAnimationCancel(Animator animator) {
                            AudioBlock.this.mBitmap = AudioBlock.this.getStartPlayBitmap();
                            AudioBlock.this.postInvalidateThis();
                        }

                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer index = (Integer)valueAnimator.getAnimatedValue();
                            if(index.intValue() != this.mCurrentIndex) {
                                AudioBlock.this.mBitmap = AudioBlock.this.getPlayingBitmaps()[index.intValue()];
                                AudioBlock.this.postInvalidateThis();
                                this.mCurrentIndex = index.intValue();
                            }

                        }
                    };
                    AudioBlock.this.mCurrentAnim.addUpdateListener(listener);
                    AudioBlock.this.mCurrentAnim.addListener(listener);
                    AudioBlock.this.mCurrentAnim.start();
                }

            }
        });
    }

    public boolean checkVoice() {
        if(this.audioManager.getStreamVolume(3) == 0) {
            UiThreadHandler.post(new Runnable() {
                public void run() {
                    ToastUtils.showToast(AudioBlock.this.getTextEnv().getContext(), "请调大音量播放");
                }
            });
            return false;
        } else {
            return true;
        }
    }
}
