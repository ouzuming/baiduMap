package com.example.lbsMpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static android.provider.UserDictionary.Words.APP_ID;

public class textWeixinActivity extends AppCompatActivity {


    private static final String APP_ID = "wx476039cdaabc491b";
    private IWXAPI api;
    private Button sendBtn;
    private EditText sendEt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_weixin);

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
        sendEt1 = (EditText) findViewById(R.id.wET1);

        sendBtn = (Button) findViewById(R.id.wtBtn1);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXTextObject textObject = new WXTextObject();
                textObject.text  = sendEt1.getText().toString();
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObject;
                msg.description = "text share";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                // req.scene = SendMessageToWX.Req.WXSceneTimeline; //发送到朋友圈
                req.scene = SendMessageToWX.Req.WXSceneSession; //发送朋友
                api.sendReq(req);
            }
        });

    }
}
