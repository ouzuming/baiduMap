package com.example.lbsMpa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wxapi.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoFileObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class weixinActivity extends AppCompatActivity {
    private static final String APP_ID = "wx476039cdaabc491b";
    private IWXAPI api;

    private Button wBtn1;
    private Button wBtn2;
    private Button wBtn3;
    private Button wBtn4;
    private Button wBtn5;
    private Button wBtn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
        //打开微信
        wBtn1 = (Button) findViewById(R.id.wBtn1);
        wBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.openWXApp();
            }
        });
        // 微信发送文字
        wBtn2 = (Button) findViewById(R.id.wBtn2);
        wBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weixinActivity.this, textWeixinActivity.class);
                startActivity(intent);

            }
        });

        //微信发送图片
        wBtn3 = (Button) findViewById(R.id.wBtn3);
        wBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tadpole);
                WXImageObject imgObj = new WXImageObject(bmp);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 18, 18, true);
                bmp.recycle();
                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);

            }
        });

        wBtn4 = (Button) findViewById(R.id.wBtn4);
        wBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXWebpageObject webobject = new WXWebpageObject();
                webobject.webpageUrl = "www.baidu.com";
                WXMediaMessage msg = new WXMediaMessage(webobject);
                msg.title = "web share";
                msg.description = "share baidu url";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "web";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
            }
        });

        wBtn5 = (Button) findViewById(R.id.wBtn5);
        wBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weixinActivity.this,"share music", Toast.LENGTH_SHORT).show();

                WXMusicObject musicObject = new WXMusicObject();
                musicObject.musicUrl="https://y.qq.com/n/yqq/song/0041w8IG2fENEX.html";

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = musicObject;
                msg.title = "中国人";
                msg.description = "share music";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
            }
        });

        wBtn6 = (Button) findViewById(R.id.wBtn6);
        wBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXVideoObject wxVideoObject = new WXVideoObject();
                wxVideoObject.videoUrl = "http://baishi.baidu.com/watch/08106150382742096490.html?page=videoMultiNeed";

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = wxVideoObject;
                msg.title = "科比飞人";
                msg.description = "蓝球";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);

            }
        });
    }
}