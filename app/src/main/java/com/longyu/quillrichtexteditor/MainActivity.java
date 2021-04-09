package com.longyu.quillrichtexteditor;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.longyu.quillandroid.Editor;
import com.longyu.quillandroid.Format;
import com.longyu.quillandroid.OnToolbarClickListener;
import com.longyu.quillandroid.toolbar.Toolbar;
import com.longyu.quillandroid.toolbar.label.pop.ToolbarTextColorPop;
import com.longyu.quillandroid.toolbar.label.pop.ToolbarTextPop;
import com.longyu.quillandroid.toolbar.label.pop.ToolbarTextSizePop;


public class MainActivity extends AppCompatActivity implements OnToolbarClickListener, ToolbarTextPop.OnMenuPopClickListener, ToolbarTextSizePop.OnTextSizeClickListener, ToolbarTextColorPop.OnTextColorClickListener {

    private String json = "{\"ops\":[{\"insert\":\"拉轰摸过我哄体育胡\"},{\"attributes\":{\"list\":\"ordered\"},\"insert\":\"\\n\"},{\"insert\":\"小心心莫咯哦咯我咯\"},{\"attributes\":{\"list\":\"ordered\"},\"insert\":\"\\n\"},{\"attributes\":{\"color\":\"orange\"},\"insert\":\"休息休息哦你屋www无所谓\"},{\"attributes\":{\"list\":\"ordered\"},\"insert\":\"\\n\"},{\"attributes\":{\"color\":\"blue\"},\"insert\":\"科技四路屠苏屠苏图片\"},{\"attributes\":{\"list\":\"ordered\"},\"insert\":\"\\n\"},{\"insert\":\"朱辛庄磨破民族\"},{\"attributes\":{\"list\":\"ordered\"},\"insert\":\"\\n\"},{\"insert\":\"\\n\"},{\"attributes\":{\"size\":\"huge\"},\"insert\":\"标题标题标题\"},{\"insert\":\"\\n\"},{\"attributes\":{\"size\":\"large\"},\"insert\":\"   \"},{\"attributes\":{\"bold\":true,\"size\":\"large\"},\"insert\":\"我或许那就是兔兔\"},{\"insert\":\"\\n\"},{\"attributes\":{\"italic\":true,\"size\":\"large\"},\"insert\":\"没印象屠苏我迫于\"},{\"insert\":\"\\n\"},{\"attributes\":{\"size\":\"large\",\"underline\":true},\"insert\":\"做一下我先去洗澡休息休息谢谢\"},{\"insert\":\"\\n\"},{\"attributes\":{\"size\":\"large\",\"strike\":true},\"insert\":\"我先去洗澡我先去洗澡没哦哦那图\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#00eeee\",\"size\":\"large\",\"strike\":true},\"insert\":\"脱模太卡啦咯啦来咯额通TMD莫哦哦噢噢噢哦哦look\"},{\"attributes\":{\"background\":\"#00eeee\",\"color\":\"yellow\",\"size\":\"large\"},\"insert\":\"lone鄂东饥渴的看见了快乐的ad\"},{\"insert\":\"\\n\"}]}\n";

    private Toolbar toolbar;

    private Format mFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.CAMERA
                    , Manifest.permission.READ_LOGS
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SET_DEBUG_APP
                    , Manifest.permission.SYSTEM_ALERT_WINDOW
                    , Manifest.permission.REQUEST_INSTALL_PACKAGES
                    , Manifest.permission.GET_ACCOUNTS
                    , Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Editor editor = (Editor) findViewById(R.id.editor);
        editor.debug();
        toolbar.setEditor(editor);
        toolbar.setOnToolbarClickListener(this);

        try {
            JSONObject jsonObject = new JSONObject(json);
            editor.setContents(jsonObject, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ToolbarTextPop.getInstance(this).initMenuPop();
        ToolbarTextPop.getInstance(this).setOnMenuPopClickListener(this);
        ToolbarTextSizePop.getInstance(this).initMenuPop();
        ToolbarTextSizePop.getInstance(this).setOnTextSizeClickListener(this);
        ToolbarTextColorPop.getInstance(this).initMenuPop();
        ToolbarTextColorPop.getInstance(this).setOnTextColorClickListener(this);

        TextView textView = findViewById(R.id.tv_contexts);
        ImageView back = findViewById(R.id.iv_format_back);
        ImageView go = findViewById(R.id.iv_format_go);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.getContents(new ValueCallback<JSONObject>() {
                    @Override
                    public void onReceiveValue(JSONObject value) {
                        Log.e("TAG", "----" + value.toString());
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.undo();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.redo();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    String photoShow = MyApplication.selectPhotoShow(this, localMedia.get(0));
                    toolbar.setImage(mFormat, photoShow);
                    toolbar.setToolbarState(0);
                    break;
            }
        }
    }

    @Override
    public void onToolbarClick(Format format, String toolbar) {
        this.mFormat = format;
        PhotoSelectSingleUtile.selectPhoto(this, PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onToolbarTxtClick(View view) {
        ToolbarTextPop.getInstance(this).showMenuPop(toolbar);
    }

    @Override
    public void onToolbarTxtSizeClick(View view) {
        ToolbarTextSizePop.getInstance(this).showMenuPop(toolbar);
    }

    @Override
    public void onToolbarTxtColorClick(View view) {
        ToolbarTextColorPop.getInstance(this).showMenuPop(toolbar);
    }

    @Override
    public void onToolbarState(int position, boolean isSelect, String value) {
        if (position == 4) {
            ToolbarTextSizePop.getInstance(this).setShowState(value);
        } else if (position == 5) {
            ToolbarTextColorPop.getInstance(this).setShowState(value);
        } else if (position >= 0) {
            ToolbarTextPop.getInstance(this).setImageState(position);
        }
    }

    @Override
    public void onMenuClick(boolean boo, int position) {
        toolbar.setTxtPosition(boo, position);
    }

    @Override
    public void onMenuDismiss() {
        toolbar.setToolbarState(1);
    }

    @Override
    public void onTextSizeClick(String textSize) {
        toolbar.setTextSize(textSize);
    }

    @Override
    public void onTextColorClick(String textColor) {
        toolbar.setTextColor(textColor);
    }

    @Override
    public void onTextSizeDismiss() {
        toolbar.setToolbarState(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToolbarTextPop.getInstance(this).onDestroy();
        ToolbarTextSizePop.getInstance(this).onDestroy();
        ToolbarTextColorPop.getInstance(this).onDestroy();
    }
}
