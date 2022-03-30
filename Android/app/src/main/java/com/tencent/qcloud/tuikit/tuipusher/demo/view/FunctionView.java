package com.tencent.qcloud.tuikit.tuipusher.demo.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.tencent.qcloud.tuikit.tuipusher.demo.R;
import com.tencent.qcloud.tuikit.tuipusher.view.TUIPusherView;

import static com.tencent.qcloud.tuikit.tuipusher.demo.view.FunctionView.PKState.CANCEL;
import static com.tencent.qcloud.tuikit.tuipusher.demo.view.FunctionView.PKState.PK;

/**
 * 测试TUIPusherView提供的功能
 * <p>
 * 主要测试功能包含：
 * - 切换摄像头
 * - 设置镜像
 * - 设置分辨率
 * - PK功能
 * - 连麦功能
 */
public class FunctionView extends FrameLayout implements View.OnClickListener {
    private View                                   mViewRoot;
    private Button                                 mButtonPK;
    private Button                                 mButtonSwitchCamera;
    private Button                                 mButtonMirror;
    private Button                                 mButtonResolution;
    private Button                                 mButtonLinkStop;
    private TUIPusherView                          mTUIPusherView;
    private boolean                                mIsMirror       = true;
    private boolean                                mIsFrontCamera  = true;
    private TUIPusherView.TUIPusherVideoResolution mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_360;
    private PKState                                mPKState        = PK;
    private EditText                               mEditUserId;

    public FunctionView(@NonNull Context context) {
        this(context, null);
    }

    public FunctionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewRoot = LayoutInflater.from(getContext()).inflate(R.layout.app_function_view, this, true);
        mButtonPK = mViewRoot.findViewById(R.id.btn_pk);
        mButtonSwitchCamera = mViewRoot.findViewById(R.id.btn_switch_camera);
        mButtonMirror = mViewRoot.findViewById(R.id.btn_mirror);
        mButtonResolution = mViewRoot.findViewById(R.id.btn_resolution);
        mButtonLinkStop = mViewRoot.findViewById(R.id.btn_link_stop);
        mEditUserId = mViewRoot.findViewById(R.id.et_pk_user_id);
        initListener();
    }

    public void setTUIPusherView(TUIPusherView view) {
        mTUIPusherView = view;
    }

    public void initListener() {
        mButtonPK.setOnClickListener(this);
        mButtonSwitchCamera.setOnClickListener(this);
        mButtonMirror.setOnClickListener(this);
        mButtonResolution.setOnClickListener(this);
        mButtonLinkStop.setOnClickListener(this);
    }

    public Button getButtonResolution() {
        return mButtonResolution;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pk) {
            switch (mPKState) {
                case PK:
                    if (TextUtils.isEmpty(mEditUserId.getText().toString())) {
                        Toast.makeText(getContext(), getContext().getString(R.string.app_hint_user_id), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    mTUIPusherView.sendPKRequest(mEditUserId.getText().toString());
                    mPKState = CANCEL;
                    setmButtonPKState(mPKState);
                    break;
                case CANCEL:
                    mTUIPusherView.cancelPKRequest();
                    mPKState = PK;
                    setmButtonPKState(mPKState);
                    break;
                case STOP:
                    mPKState = PK;
                    mTUIPusherView.stopPK();
                    setmButtonPKState(mPKState);
                    break;
            }

        } else if (v.getId() == R.id.btn_switch_camera) {
            mIsFrontCamera = !mIsFrontCamera;
            mTUIPusherView.switchCamera(mIsFrontCamera);
        } else if (v.getId() == R.id.btn_mirror) {
            mIsMirror = !mIsMirror;
            mTUIPusherView.setMirror(mIsMirror);
        } else if (v.getId() == R.id.btn_resolution) {
            showResolutionMenu();
        } else if (v.getId() == R.id.btn_link_stop) {
            mTUIPusherView.stopJoinAnchor();
        }
    }


    private void showResolutionMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), getButtonResolution(), Gravity.TOP);
        popupMenu.getMenuInflater().inflate(com.tencent.qcloud.tuikit.tuipusher.R.menu.tuipusher_resolution, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_360;
                if (item.getItemId() == com.tencent.qcloud.tuikit.tuipusher.R.id.resolution_360) {
                    mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_360;
                } else if (item.getItemId() == com.tencent.qcloud.tuikit.tuipusher.R.id.resolution_540) {
                    mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_540;
                } else if (item.getItemId() == com.tencent.qcloud.tuikit.tuipusher.R.id.resolution_720) {
                    mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_720;
                } else if (item.getItemId() == com.tencent.qcloud.tuikit.tuipusher.R.id.resolution_1080) {
                    mResolutionFlag = TUIPusherView.TUIPusherVideoResolution.TUIPUSHER_VIDEO_RES_1080;
                }
                mTUIPusherView.setVideoResolution(mResolutionFlag);
                return false;
            }
        });
        popupMenu.show();
    }

    public void setmButtonPKState(PKState pkState) {
        mPKState = pkState;
        switch (pkState) {
            case PK:
                mButtonPK.setText(R.string.app_request_pk);
                break;
            case CANCEL:
                mButtonPK.setText(R.string.app_cancel_pk);
                break;
            case STOP:
                mButtonPK.setText(R.string.app_stop_pk);
                break;
        }
    }

    public enum PKState {
        PK,
        CANCEL,
        STOP,
    }

}

