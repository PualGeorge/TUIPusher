package com.tencent.qcloud.tuikit.tuipusher.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuipusher.demo.view.FunctionView;
import com.tencent.qcloud.tuikit.tuipusher.view.TUIPusherView;
import com.tencent.qcloud.tuikit.tuipusher.view.listener.TUIPusherViewListener;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TUIPusherView 推流主页面
 * <p>
 * 推流逻辑主要依赖 tuipusher 模块中的{@link TUIPusherView} 实现
 */
public class MainActivity extends AppCompatActivity {
    private TUIPusherView mTUIPusherView;
    FunctionView mFunctionView;
    private AlertDialog mPKDialog;
    private AlertDialog mLinkDialog;

    private void initFunctionView() {
        mFunctionView = new FunctionView(this);
        mFunctionView.setTUIPusherView(mTUIPusherView);
        mTUIPusherView.addView(mFunctionView);
        mFunctionView.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        mTUIPusherView = new TUIPusherView(this);
        setContentView(mTUIPusherView);
        initFunctionView();
        final String pushUrl = URLUtils.generatePushUrl(TUILogin.getUserId(), URLUtils.PushType.RTC);
        mTUIPusherView.setTUIPusherViewListener(new TUIPusherViewListener() {
            @Override
            public void onPushStarted(TUIPusherView pushView, String url) {
                mFunctionView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPushStoped(TUIPusherView pushView, String url) {

            }

            @Override
            public void onPushEvent(TUIPusherView pusherView, TUIPusherEvent event, String message) {

            }

            @Override
            public void onClickStartPushButton(TUIPusherView pushView, String url, final ResponseCallback callback) {
                ToastUtil.toastShortMessage(getString(R.string.app_call_process_task));
                pushView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback.response(true);
                    }
                }, 1000);
            }

            @Override
            public void onReceivePKRequest(TUIPusherView pushView, String userId, ResponseCallback callback) {
                showPKDialog(userId, callback);
            }

            @Override
            public void onRejectPKResponse(TUIPusherView pusherView, int reason) {
                if (reason == 1) {
                    ToastUtil.toastShortMessage(getString(R.string.app_anchor_reject_request));
                } else if (reason == 2) {
                    ToastUtil.toastShortMessage(getString(R.string.app_anchor_busy));
                } else {
                    ToastUtil.toastShortMessage("error -> reason:" + reason);
                }
                dismissPKDialog();
                if (mFunctionView != null) {
                    mFunctionView.setmButtonPKState(FunctionView.PKState.PK);
                }
            }

            @Override
            public void onCancelPKRequest(TUIPusherView pusherView) {
                if (mFunctionView != null) {
                    mFunctionView.setmButtonPKState(FunctionView.PKState.PK);
                }
                dismissPKDialog();
            }

            @Override
            public void onStartPK(TUIPusherView pusherView) {
                if (mFunctionView != null) {
                    mFunctionView.setmButtonPKState(FunctionView.PKState.STOP);
                }
            }

            @Override
            public void onStopPK(TUIPusherView pusherView) {
                dismissPKDialog();
                if (mFunctionView != null) {
                    mFunctionView.setmButtonPKState(FunctionView.PKState.PK);
                }
            }

            @Override
            public void onPKTimeout(TUIPusherView pusherView) {
                dismissPKDialog();
                if (mFunctionView != null) {
                    mFunctionView.setmButtonPKState(FunctionView.PKState.PK);
                }
            }

            @Override
            public void onReceiveJoinAnchorRequest(TUIPusherView pushView, String userId, ResponseCallback callback) {
                showLinkDialog(userId, callback);
            }

            @Override
            public void onCancelJoinAnchorRequest(TUIPusherView pusherView) {
                dismissLinkDialog();
            }

            @Override
            public void onStartJoinAnchor(TUIPusherView pusherView) {

            }

            @Override
            public void onStopJoinAnchor(TUIPusherView pusherView) {
                dismissLinkDialog();
            }

            @Override
            public void onJoinAnchorTimeout(TUIPusherView pusherView) {
                dismissLinkDialog();
            }
        });
        mTUIPusherView.start(pushUrl);
    }

    private void dismissPKDialog() {
        if (mPKDialog != null && mPKDialog.isShowing()) {
            mPKDialog.dismiss();
        }
        mPKDialog = null;
    }

    private void showPKDialog(String userId, final TUIPusherViewListener.ResponseCallback callback) {
        if (mPKDialog == null) {
            final TextView textView = new TextView(this);
            textView.setText(userId + getString(R.string.app_request_pk));
            mPKDialog = new AlertDialog.Builder(this, R.style.PKDialogTheme)
                    .setView(textView)
                    .setPositiveButton(getString(R.string.app_accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.response(true);
                        }
                    })
                    .setNegativeButton(getString(R.string.app_reject), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.response(false);
                        }
                    }).create();
        }
        mPKDialog.show();
    }

    private void dismissLinkDialog() {
        if (mLinkDialog != null && mLinkDialog.isShowing()) {
            mLinkDialog.dismiss();
        }
        mLinkDialog = null;
    }

    private void showLinkDialog(String userId, final TUIPusherViewListener.ResponseCallback callback) {
        if (mLinkDialog == null) {
            final TextView textView = new TextView(this);
            textView.setText(userId + getString(R.string.app_request_link));
            mLinkDialog = new AlertDialog.Builder(this, R.style.PKDialogTheme)
                    .setView(textView)
                    .setPositiveButton(getString(R.string.app_accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.response(true);
                        }
                    })
                    .setNegativeButton(getString(R.string.app_reject), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.response(false);
                        }
                    }).create();
        }
        mLinkDialog.show();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTUIPusherView.stop();
    }

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getSafeUrl(String streamName) {
        long txTime = System.currentTimeMillis() / 1000 + 60 * 60;
        String input = new StringBuilder().
                append("23163df6f7320112a8417ca9b36e0cc4").
                append(streamName).
                append(Long.toHexString(txTime).toUpperCase()).toString();
        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new StringBuilder().
                append("?txSecret=").
                append(txSecret).
                append("&").
                append("txTime=").
                append(Long.toHexString(txTime).toUpperCase()).
                toString();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }
}
