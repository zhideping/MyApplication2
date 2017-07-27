package com.bjxiyang.zhinengshequ.shop.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baisi.imoocsdk.imageloader.ImageLoaderManager;
import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.dialog.MyFuWuTimeDialog;
import com.bjxiyang.zhinengshequ.shop.luban.LuBan;
import com.bjxiyang.zhinengshequ.shop.model.ImageUrl;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaSave;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaXinXi;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class ShangJiaXinXiActivity extends MySwipeBackActivity implements View.OnClickListener {
    final int RESULT_LOAD_IMAGE=2;
    private File mFile;
    private String picturePath;
    private String image_name=null;
    private List<String> imageList;
    private String imageString;
    private Map map=new HashMap();
    /**
     * UI
     */
    private TextView startTime;
    private LinearLayout start;
    private LinearLayout end;
    private TextView endTime;
    private RelativeLayout iv_shangjiaxinxi_fanhui;
    private TextView tv__shangjiaxinxi_save;
    private TextView et_shangjianame;
    private EditText et_shangjiatel;
    private ImageView iv_activity_tianjiashangpinimg;
    private LinearLayout ll_shangjiaaddress_selecte;
    private EditText et_xiangxidizhi;
    private LinearLayout ll_yingyeshijian_selecte;
    private LinearLayout ll_songdashijian_selecte;
    private LinearLayout ll_shangjiagonggao_selecte;
    private TextView textView_songdashijian;
    private EditText et_shangjiagonggao;
    private TextView tv_shangjiadizhi;


    /**
     * Data
     */

    private String sjName;
    private String sjPhone;
    private String sjDiZhi;
    private int startTime_S;
    private int endTime_S;
    private int songdaTime;
    private String des;
    private boolean isShangchuan=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjiaxinxi);
        Intent intent=getIntent();
        isShangchuan=false;
        initUi();
        initData();
    }

    private void initData() {

        String seller_get= RequestURL.URL_SELLER_GET;
        RequestCenter.seller_get(seller_get, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                ShangJiaXinXi shangjiaxinxi= (ShangJiaXinXi) responseObj;
                if (shangjiaxinxi.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                    ShangJiaXinXi.ResultBean resultBean= shangjiaxinxi.getResult();
                    ImageLoaderManager.getInstance(ShangJiaXinXiActivity.this)
                            .displayImage(iv_activity_tianjiashangpinimg,resultBean.getLogo());
                    imageString=resultBean.getLogo();
                    startTime_S=resultBean.getStartTime();
                    endTime_S=resultBean.getEndTime();
                    songdaTime=resultBean.getServiceTime();
                    tv_shangjiadizhi.setText(resultBean.getAddress());
                    et_shangjianame.setText(resultBean.getShopName());
                    et_shangjiatel.setText(resultBean.getLinkphone());
                    et_xiangxidizhi.setText(resultBean.getAddress());
                    et_shangjiagonggao.setText(resultBean.getDes());
                    startTime.setText(resultBean.getStartTime()/60+":"+resultBean.getStartTime()%60);
                    endTime.setText(resultBean.getEndTime()/60+":"+resultBean.getEndTime()%60);
                    if (resultBean.getServiceTime()==0){
                        textView_songdashijian.setText("尽快送达");
                    }else {
                        textView_songdashijian.setText(resultBean.getServiceTime()+"小时");
                    }
                }
                if (shangjiaxinxi.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                    LogOutUntil.logout(ShangJiaXinXiActivity.this);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });

    }

    private void initUi() {
        tv_shangjiadizhi= (TextView) findViewById(R.id.tv_shangjiadizhi);
        et_shangjiagonggao= (EditText) findViewById(R.id.et_shangjiagonggao);
        startTime = (TextView) this.findViewById(R.id.tv1);
        endTime = (TextView) this.findViewById(R.id.tv2);
        start = (LinearLayout) findViewById(R.id.ll_qishishijian);
        end = (LinearLayout) findViewById(R.id.ll_jiezhishijian);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        textView_songdashijian= (TextView) findViewById(R.id.textView_songdashijian);
        iv_shangjiaxinxi_fanhui= (RelativeLayout) findViewById(R.id.iv_shangjiaxinxi_fanhui);
        tv__shangjiaxinxi_save= (TextView) findViewById(R.id.tv__shangjiaxinxi_save);
        et_shangjianame= (TextView) findViewById(R.id.et_shangjianame);
        et_shangjiatel= (EditText) findViewById(R.id.et_shangjiatel);
        iv_activity_tianjiashangpinimg= (ImageView) findViewById(R.id.iv_activity_tianjiashangpinimg);
        ll_shangjiaaddress_selecte= (LinearLayout) findViewById(R.id.ll_shangjiaaddress_selecte);
        et_xiangxidizhi= (EditText) findViewById(R.id.et_xiangxidizhi);
        ll_yingyeshijian_selecte= (LinearLayout) findViewById(R.id.ll_yingyeshijian_selecte);
        ll_songdashijian_selecte= (LinearLayout) findViewById(R.id.ll_songdashijian_selecte);
        ll_shangjiagonggao_selecte= (LinearLayout) findViewById(R.id.ll_shangjiagonggao_selecte);
        iv_shangjiaxinxi_fanhui.setOnClickListener(this);
        iv_activity_tianjiashangpinimg.setOnClickListener(this);
        tv__shangjiaxinxi_save.setOnClickListener(this);
        ll_shangjiaaddress_selecte.setOnClickListener(this);
        ll_yingyeshijian_selecte.setOnClickListener(this);
        ll_songdashijian_selecte.setOnClickListener(this);
        ll_shangjiagonggao_selecte.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回的按键
            case R.id.iv_shangjiaxinxi_fanhui:
                finish();
                break;
            //添加图片的按键
            case R.id.iv_activity_tianjiashangpinimg:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);

                break;
            //保存的按键
            case R.id.tv__shangjiaxinxi_save:
                sjName= String.valueOf(et_shangjianame.getText());
                sjPhone= String.valueOf(et_shangjiatel.getText());
                sjDiZhi= String.valueOf(et_xiangxidizhi.getText());
                des=String.valueOf(et_shangjiagonggao.getText());
                getUrl();
                break;
            //选择商家地址的按键
            case R.id.ll_shangjiaaddress_selecte:
                break;
            //选择营业时间的按键
//            case R.id.ll_yingyeshijian_selecte:
//                Intent intent1=new Intent(ShangJiaXinXiActivity.this,DateActivity.class);
//                startActivity(intent1);
//                break;
            case R.id.ll_qishishijian:
                new TimePickerDialog(ShangJiaXinXiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(String.format("%d:%d", hourOfDay, minute));
                        startTime_S=hourOfDay*60+minute;

                    }    //0,0指的是时间，true表示是否为24小时，true为24小时制
                }, 0, 0, true).show();
                break;
            //结束时间的按键
            case R.id.ll_jiezhishijian:
                new TimePickerDialog(ShangJiaXinXiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(String.format("%d:%d", hourOfDay, minute));
                        endTime_S=hourOfDay*60+minute;
                    }
                }, 0, 0, true).show();
                break;

            //选择送达时间的按键
            case R.id.ll_songdashijian_selecte:
                MyFuWuTimeDialog dialog=new MyFuWuTimeDialog(ShangJiaXinXiActivity.this);
                dialog.show();
                dialog.setOnGetSelectTime(new MyFuWuTimeDialog.OnGetSelectTime() {
                    @Override
                    public void getSelectTime(String selectTime) {
                        textView_songdashijian.setText(selectTime);
                    }

                    @Override
                    public void getSelectTime_int(int selectTime_int) {
                        songdaTime=selectTime_int;
                    }
                });
                break;
            //跳转到商家公告页面
            case R.id.ll_shangjiagonggao_selecte:

                break;


        }
    }
    private Bitmap getBitmapFromUri(Uri uri) {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
    private void getUrl(){
        DialogUntil.showLoadingDialog(ShangJiaXinXiActivity.this,"正在加载",true);

        if (isShangchuan){



        String url_image=RequestURL.URL_IMAGE;

        map.put("piclist",mFile);
//        RequestCenter.text_url(url_image, new DisposeDataListener() {
//            @Override
//            public void onSuccess(Object responseObj) {
//                Log.i("YYY","上传成功");
//            }
//
//            @Override
//            public void onFailure(Object reasonObj) {
//                Log.i("YYY","上传失败");
//            }
//        });
        RequestCenter.uploadPictures(url_image, map, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                ImageUrl imageUrl= (ImageUrl) responseObj;
                if (imageUrl.getCode().equals("1000")) {
                    imageList = imageUrl.getResult();
                    imageString = imageList.get(0);
                    Log.i("YYY", "上传成功");
                    //startTime_S    endTime_S
                    String url_save=RequestURL.URL_SELLER_SAVE
                            +"logo="+ imageString+"&phone="+sjPhone+"&startTime="+
                            startTime_S+"&endTime="+endTime_S+"&serviceTime="+songdaTime+
                            "&des="+des;
                    Log.i("YYY", RequestURL.URL_SELLER_SAVE
                            +"logo="+ imageString+"&phone="+sjPhone+"&startTime="+
                            startTime_S+"&endTime="+endTime_S+"&serviceTime="+songdaTime+
                            "&des="+des);
//                    +"&startPrice="+1+"&dispatchPrice="+1
                    RequestCenter.seller_save(url_save, new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DialogUntil.closeLoadingDialog();
                            ShangJiaSave shangJiaSave= (ShangJiaSave) responseObj;
                            if (shangJiaSave.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){

                                MyUntil.show(ShangJiaXinXiActivity.this,"保存成功");
                                finish();
                            }else if (shangJiaSave.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                LogOutUntil.logout(ShangJiaXinXiActivity.this);
                                finish();
                            }else {
                                MyUntil.show(ShangJiaXinXiActivity.this,shangJiaSave.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            DialogUntil.closeLoadingDialog();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();
                Log.i("YYY","上传失败");
            }
        });
        }else {
//            String info = "百度经验,分享经验,欢迎你";

            String[] s1=imageString.split("/");//以","为分隔符，截取上面的字符串。结果为三段

            for(int i=0;i<s1.length;i++){
                if (s1.length-1==i){
                    image_name=s1[i];
                    Log.i("YYYY",s1[i]);
                }
            }
            String url_save=RequestURL.URL_SELLER_SAVE
                    +"logo="+ image_name+"&phone="+sjPhone+"&startTime="+
                    startTime_S+"&endTime="+endTime_S+"&serviceTime="+songdaTime+
                    "&des="+des;
            Log.i("YYY", RequestURL.URL_SELLER_SAVE
                    +"logo="+ image_name+"&phone="+sjPhone+"&startTime="+
                    startTime_S+"&endTime="+endTime_S+"&serviceTime="+songdaTime+
                    "&des="+des);
//                    +"&startPrice="+1+"&dispatchPrice="+1
            RequestCenter.seller_save(url_save, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    DialogUntil.closeLoadingDialog();
                    ShangJiaSave shangJiaSave= (ShangJiaSave) responseObj;
                    if (shangJiaSave.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){
                        MyUntil.show(ShangJiaXinXiActivity.this,"保存成功");
                        finish();
                    }else if (shangJiaSave.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                        LogOutUntil.logout(ShangJiaXinXiActivity.this);
                        finish();
                    }else {
                        MyUntil.show(ShangJiaXinXiActivity.this,shangJiaSave.getMsg());
                    }
                }

                @Override
                public void onFailure(Object reasonObj) {
                    DialogUntil.closeLoadingDialog();

                }
            });
        }
    }
    /**
     * 用onActivityResult()接收传回的图像，当用户拍完照片，或者取消后，系统都会调用这个函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
//        if (requestCode == RESULT_CANCELED && resultCode == RESULT_OK) {
//            Bundle extras = intent.getExtras();//从Intent中获取附加值
//            Bitmap bitmap=(Bitmap) extras.get("data");
//            mFile=compressImagefile(bitmap);
////            saveBitmapToSharedPreferences((Bitmap) extras.get("data"));
////            从附加值中获取返回的图像
////            iv_xiugaixinxi_xiugai_touxiang.setImageBitmap(bitmap);
//            bitmap.recycle();
//        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri uri1 = intent.getData();
            Bitmap bitmap = getBitmapFromUri(uri1);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri1,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            isShangchuan=true;
            mFile = new File(picturePath);
            LuBan.setOnGetImage(ShangJiaXinXiActivity.this, mFile, new LuBan.OnGetImage() {
                @Override
                public void getImage(File file) {
                    map.put("piclist", file);

                }
            });
            cursor.close();
            bitmap.recycle();
            ImageLoaderManager.getInstance(ShangJiaXinXiActivity.this)
                    .displayImage(iv_activity_tianjiashangpinimg, String.valueOf(uri1));

        }
    }
}
