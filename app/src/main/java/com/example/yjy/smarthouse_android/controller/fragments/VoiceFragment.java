package com.example.yjy.smarthouse_android.controller.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yjy.smarthouse_android.R;
import com.example.yjy.smarthouse_android.toolkit.protocol.ProtocolHelper;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolList;
import com.example.yjy.smarthouse_android.bussiness.protocol.ProtocolCommand;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulRequest;
import com.example.yjy.smarthouse_android.toolkit.http.RestfulResponse;
import com.example.yjy.smarthouse_android.toolkit.speech.setting.UnderstanderSettings;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.sunflower.FlowerCollector;

public class VoiceFragment extends BaseFragment implements View.OnClickListener{

    private static String TAG = VoiceFragment.class.getSimpleName();
    // 语义理解对象（语音到语义）。
    private SpeechUnderstander mSpeechUnderstander;

    private Toast mToast;
    private TextView mUnderstanderText;
    private SharedPreferences mSharedPreferences;
    
    
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_understander, container, false);
        return view;
    }

    public void setContext(Context context){
        mContext = context;
       }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.start_understander).setOnClickListener(this);
        view.findViewById(R.id.image_understander_set).setOnClickListener(this);

        /**
         * 申请的appid时，我们为开发者开通了开放语义（语义理解）
         * 由于语义理解的场景繁多，需开发自己去开放语义平台：http://www.xfyun.cn/services/osp
         * 配置相应的语音场景，才能使用语义理解，否则文本理解将不能使用，语义理解将返回听写结果。
         */
        // 初始化对象
        mUnderstanderText = (TextView)view.findViewById(R.id.understander_text);
        mSpeechUnderstander = SpeechUnderstander.createUnderstander(mContext, mSpeechUdrInitListener);
//        mTextUnderstander = TextUnderstander.createTextUnderstander(mContext, mTextUdrInitListener);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        mSharedPreferences = mContext.getSharedPreferences(UnderstanderSettings.PREFER_NAME, Activity.MODE_PRIVATE);
    }


    /**
     * 初始化监听器（语音到语义）。
     */
    private InitListener mSpeechUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "speechUnderstanderListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code);
            }
        }
    };

    int ret = 0;// 函数调用返回值
    @Override
    public void onClick(View view) {
        if( null == this.mSpeechUnderstander ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
            return;
        }

        switch (view.getId()) {
            // 进入参数设置页面
            case R.id.image_understander_set:
                Intent intent = new Intent(mContext, UnderstanderSettings.class);
                startActivity(intent);
                break;

            // 开始语音理解
            case R.id.start_understander:
                mUnderstanderText.setText("");
                // 设置参数
                setParam();

                if(mSpeechUnderstander.isUnderstanding()){// 开始前检查状态
                    mSpeechUnderstander.stopUnderstanding();
                    showTip("停止录音");
                }else {
                    ret = mSpeechUnderstander.startUnderstanding(mSpeechUnderstanderListener);
                    if(ret != 0){
                        showTip("语义理解失败,错误码:"	+ ret);
                    }else {
                        showTip(getString(R.string.text_begin));
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 语义理解回调。
     */
    private SpeechUnderstanderListener mSpeechUnderstanderListener = new SpeechUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                Log.d(TAG, result.getResultString());

                // 显示
                String text = result.getResultString();
                if (!TextUtils.isEmpty(text)) {
                    mUnderstanderText.setText(text);
                    try {
                        ProtocolCommand parseResult = ProtocolHelper.parseCommand(text);
                        //caution,buildContent() must be called earlier than buildOperation() if using "POST"
                        RestfulResponse response = RestfulRequest.create()
                                .buildUri("http://"+getString(R.string.onenet_restful_api)+"/cmds?device_id="+ ProtocolList.ID_CONTROLLER)
                                .buildHeader("api-key",getString(R.string.onenet_app_key))
                                .buildContent(parseResult.getJsonMessage())
                                .buildOperation("POST")
                                .send();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: 17-4-29 show the error input for user
                    }
                }
            } else {
                showTip("识别结果不正确。");
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, data.length+"");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        if( null != mSpeechUnderstander ){
            // 退出时释放连接
            mSpeechUnderstander.cancel();
            mSpeechUnderstander.destroy();
        }

    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     * @param
     * @return
     */
    public void setParam(){
        String lang = mSharedPreferences.getString("understander_language_preference", "mandarin");
        if (lang.equals("en_us")) {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
        }else {
            // 设置语言
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lang);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("understander_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("understander_vadeos_preference", "1000"));

        // 设置标点符号，默认：1（有标点）
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("understander_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/sud.wav");
    }

    @Override
    public void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(mContext);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    public void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(mContext);
        super.onPause();
    }

}
