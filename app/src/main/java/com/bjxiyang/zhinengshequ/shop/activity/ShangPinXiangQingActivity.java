package com.bjxiyang.zhinengshequ.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baisi.imoocsdk.imageloader.ImageLoaderManager;
import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.luban.LuBan;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.dialog.ListViewDialog;
import com.bjxiyang.zhinengshequ.shop.manager.SPManager;
import com.bjxiyang.zhinengshequ.shop.model.ImageUrl;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinAdd;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinFenLei;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinList;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinXiangQing;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class ShangPinXiangQingActivity extends MySwipeBackActivity
        implements View.OnClickListener,TextWatcher{

    private RelativeLayout iv_shangpinxiangqing_fanhui;
    private TextView tv__shangpinxiangqing_save;
    private ImageView iv_activity_tianjiashangpinimg;
    private EditText et_goodsname;
    private LinearLayout ll_goodsparticulars_edit;
    private EditText et_goodprice;
    private ImageView et_youhui_no;
    private ImageView et_youhui_yes;
    private EditText et_shangpinyuanjia;
    private EditText et_shangpinstore;
    private EditText et_shangpinmiaoshu;
    private TextView et_goodsclassify;
    private int ifDiscount=0;
    int productTypeId=0;
    /**
     * 选择图片
     */
    final int RESULT_LOAD_IMAGE=2;
    private File mFile;
    private String picturePath;
    private String image_name=null;
    /***
     * Data
     * @param savedInstanceState
     */

    private String name;
    private double price;
    private double discountPrice=0;
    private String stockNum;
    private String des;


    private Map map=new HashMap();
    private List<ShangPinXiangQing.Result> mList;
    private List<ShangPinFenLei.Result> list;
    private ShangPinXiangQing.Result result;

    private String fenlei=null;
    private String imageString=null;
    private int type;

    private List<String> imageList;

    private ShangPinList.Result.Products products;
    DecimalFormat df=new DecimalFormat("0.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangpinxiangqing);

        quanxian();
        initUI();
        Intent intent=getIntent();
        if (intent!=null){
            type = intent.getIntExtra("TYPE",0);
            switch (type){
                case 1:
                    fenlei=intent.getStringExtra("fenlei");
                    et_goodsclassify.setText(fenlei);
                    productTypeId=intent.getIntExtra("productTypeId",0);

                case 2:
                    products = (ShangPinList.Result.Products) intent.getSerializableExtra("products");
                    fenlei=intent.getStringExtra("fenlei");
                    productTypeId=intent.getIntExtra("productTypeId",0);
                    break;
            }

        }
        setData();
        initData();

    }
    private void setData() {
        if (products!=null){
            ImageLoaderManager.getInstance(ShangPinXiangQingActivity.this)
                    .displayImage(iv_activity_tianjiashangpinimg,products.getLogo());
            imageString=products.getLogo();
            et_goodsname.setText(products.getName());
            et_goodsclassify.setText(fenlei);
            et_goodprice.setText(df.format((double) products.getPrice()/100)+"");
            et_shangpinyuanjia.setText(df.format((double) products.getDiscountPrice()/100)+"");
            et_shangpinstore.setText(products.getStockNum()+"");
            et_shangpinmiaoshu.setText(products.getDes());

            if (products.getIfDiscount()==0){
                ifDiscount=0;
                et_youhui_no.setImageResource(R.drawable.b_c_a_btn_yes_pre);
                et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_no);

            }else {
                ifDiscount=1;
                et_youhui_no.setImageResource(R.drawable.b_c_a_btn_no);
                et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_yes_pre);
            }
        }
    }

    private void initUI() {
        et_goodsclassify= (TextView) findViewById(R.id.et_goodsclassify);
        iv_shangpinxiangqing_fanhui= (RelativeLayout) findViewById(R.id.iv_shangpinxiangqing_fanhui);
        tv__shangpinxiangqing_save= (TextView) findViewById(R.id.tv__shangpinxiangqing_save);
        iv_activity_tianjiashangpinimg= (ImageView) findViewById(R.id.iv_activity_tianjiashangpinimg);
        et_goodsname= (EditText) findViewById(R.id.et_goodsname);
        ll_goodsparticulars_edit= (LinearLayout) findViewById(R.id.ll_goodsparticulars_edit);
        et_goodprice= (EditText) findViewById(R.id.et_goodprice);
        et_goodprice.addTextChangedListener(this);
        et_youhui_no= (ImageView) findViewById(R.id.et_youhui_no);
        et_youhui_yes= (ImageView) findViewById(R.id.et_youhui_yes);
        et_shangpinyuanjia= (EditText) findViewById(R.id.et_shangpinyuanjia);
        et_shangpinyuanjia.addTextChangedListener(this);
        et_shangpinstore= (EditText) findViewById(R.id.et_shangpinstore);
        et_shangpinmiaoshu= (EditText) findViewById(R.id.et_shangpinmiaoshu);
        iv_shangpinxiangqing_fanhui.setOnClickListener(this);
        tv__shangpinxiangqing_save.setOnClickListener(this);
        iv_activity_tianjiashangpinimg.setOnClickListener(this);
        ll_goodsparticulars_edit.setOnClickListener(this);
        et_youhui_no.setOnClickListener(this);
        et_youhui_yes.setOnClickListener(this);
        if (fenlei!=null){
            et_goodsclassify.setText(fenlei);
        }
    }
    private void initData() {
        if (type!=1) {
            DialogUntil.showLoadingDialog(ShangPinXiangQingActivity.this, "正在加载", true);
            String url = RequestURL.URL_PRODUCT_DETAIL + "productId=" + products.getId();
            RequestCenter.product_detail(url, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    DialogUntil.closeLoadingDialog();
                    ShangPinXiangQing shangPinXiangQing = (ShangPinXiangQing) responseObj;
                    if (shangPinXiangQing.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
                        result = shangPinXiangQing.getResult();
                        imageString=result.getProduct().getLogo();
//                    ImageLoaderManager.getInstance(ShangPinXiangQingActivity.this)
//                            .displayImage(iv_activity_tianjiashangpinimg,mList.get(0).getProduct().getLogo());
                        et_goodsname.setText(result.getProduct().getName());
//                        et_goodsclassify.setText(result.getProductTypes().get(0).getName());
                        et_goodprice.setText(df.format((double) result.getProduct().getPrice()/100));
                        //无优惠的
                        if (result.getProduct().getIfDiscount() == 0) {
                            et_youhui_no.setImageResource(R.drawable.b_c_a_btn_yes_pre);
                            et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_no);
                        } else {
                            et_youhui_no.setImageResource(R.drawable.b_c_a_btn_no);
                            et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_yes_pre);
                        }
                        et_shangpinyuanjia.setText(df.format((double) result.getProduct().getDiscountPrice()/100));
                    }else if (shangPinXiangQing.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                        LogOutUntil.logout(ShangPinXiangQingActivity.this);
                        finish();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_shangpinxiangqing_fanhui:
                finish();
                break;
            //保存的按键
            case R.id.tv__shangpinxiangqing_save:
                name= String.valueOf(et_goodsname.getText());

                if (et_goodprice.getText()!=null&&!String.valueOf(et_goodprice.getText()).equals("")){
                    price= Double.parseDouble(String.valueOf(et_goodprice.getText()));
                }

                if (et_shangpinyuanjia.getText()!=null&&!String.valueOf(et_shangpinyuanjia.getText()).equals("")){
                    discountPrice= Double.parseDouble(String.valueOf(et_shangpinyuanjia.getText()));
                }
                stockNum= String.valueOf(et_shangpinstore.getText());
                des= String.valueOf(et_shangpinmiaoshu.getText());

                if (name==null||name.equals("")){
                    MyUntil.show(ShangPinXiangQingActivity.this,"请输入商品名称");
                    break;
                }


                if (String.valueOf(et_goodprice.getText())==null||String.valueOf(et_goodprice.getText()).equals("")){
                    MyUntil.show(ShangPinXiangQingActivity.this,"请输入商品价格");
                    break;
                }
                if (ifDiscount==1&&(String.valueOf(et_shangpinyuanjia.getText())==null
                        ||String.valueOf(et_shangpinyuanjia.getText()).equals(""))){
                    MyUntil.show(ShangPinXiangQingActivity.this,"请输入商品优惠后的价格");
                    break;
                }

                if (stockNum==null||stockNum.equals("")){
                    MyUntil.show(ShangPinXiangQingActivity.this,"请输入商品库存");
                    break;
                }
                if (des==null||des.equals("")){
                    MyUntil.show(ShangPinXiangQingActivity.this,"请输入商品介绍");
                    break;
                }
                if (String.valueOf(et_shangpinyuanjia.getText())!=null&&!String.valueOf(et_shangpinyuanjia.getText()).equals("")) {
                    if (Double.parseDouble(String.valueOf(et_shangpinyuanjia.getText()))
                            > Double.parseDouble(String.valueOf(et_goodprice.getText()))) {
                        MyUntil.show(ShangPinXiangQingActivity.this, "优惠价格不能大于商品原价");
                        break;
                    }
                }



                if (imageString==null||imageString.equals("")){
                    if (map.get("piclist")!=null){
//                        MyUntil.show(ShangPinXiangQingActivity.this,"测试");
                        getUrl();
                    }else {
                        MyUntil.show(ShangPinXiangQingActivity.this,"请添加图片");
                        break;
                    }
                }else {
                    getUrl();
                }
//                if (type==1){
//                    if (image_name==null) {
//                        MyUntil.show(ShangPinXiangQingActivity.this, "正在上传图片，请稍等在试");
//                    }
//                }else {
//
//                }

                break;
            //添加图片的按键
            case R.id.iv_activity_tianjiashangpinimg:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
            //选择商品分类的按键
            case R.id.ll_goodsparticulars_edit:
                getFenLei();

                break;
            //选择是否优惠
            case R.id.et_youhui_no:
                ifDiscount=0;
                et_youhui_no.setImageResource(R.drawable.b_c_a_btn_yes_pre);
                et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_no);
                break;
            case R.id.et_youhui_yes:
                ifDiscount=1;
                et_youhui_no.setImageResource(R.drawable.b_c_a_btn_no);
                et_youhui_yes.setImageResource(R.drawable.b_c_a_btn_yes_pre);
                break;

        }
    }
    private void getFenLei() {
        DialogUntil.showLoadingDialog(ShangPinXiangQingActivity.this,"正在加载",true);
        String url= RequestURL.URL_PRODUCT_PRODUCTTYPE_LIST;
        RequestCenter.product_productType_list(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogUntil.closeLoadingDialog();
                ShangPinFenLei fenlei= (ShangPinFenLei) responseObj;
                if (fenlei.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                    list=fenlei.getResult();
                    ListViewDialog dialog=new ListViewDialog(ShangPinXiangQingActivity.this,list);
                    dialog.setOnsetselect(new ListViewDialog.Onsetselect() {
                        @Override
                        public void getDianMingResult(ShangPinFenLei.Result result) {
                            et_goodsclassify.setText(result.getName());
                        }
                    });

                    dialog.show();
                }else {
                    MyUntil.show(ShangPinXiangQingActivity.this,fenlei.getMsg());
                }
            }
            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();

            }
        });
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
            mFile = new File(picturePath);
            LuBan.setOnGetImage(ShangPinXiangQingActivity.this, mFile, new LuBan.OnGetImage() {
                @Override
                public void getImage(File file) {
                    map.put("piclist", file);

                }
            });
            cursor.close();
            bitmap.recycle();
            ImageLoaderManager.getInstance(ShangPinXiangQingActivity.this)
                    .displayImage(iv_activity_tianjiashangpinimg, String.valueOf(uri1));

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
            DialogUntil.showLoadingDialog(ShangPinXiangQingActivity.this, "正在加载", true);
            if (imageString==null||imageString.equals("")) {
                String url_image = RequestURL.URL_IMAGE;

                RequestCenter.uploadPictures(url_image, map, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        ImageUrl imageUrl = (ImageUrl) responseObj;
                        if (imageUrl.getCode().equals("1000")) {
                            imageList = imageUrl.getResult();
                            imageString = imageList.get(0);
                            Log.i("YYY", "上传成功");
                            if (type == 1) {
                                String url_save = RequestURL.URL_PRODUCT_ADD +
                                        "name=" + name +
                                        "&logo=" + imageString +
                                        "&productTypeId=" + productTypeId +
                                        "&price=" + price +
                                        "&discountPrice=" + discountPrice
                                        + "&ifDiscount=" + ifDiscount +
                                        "&stockNum=" + stockNum +
                                        "&des=" + des;

                                Log.i("YYYY", RequestURL.URL_PRODUCT_ADD +
                                        "name=" + name +
                                        "&logo=" + imageString + "&productTypeId=" + productTypeId +
                                        "&price=" + price + "&discountPrice=" + discountPrice
                                        + "&ifDiscount=" + ifDiscount + "&stockNum=" +
                                        stockNum + "&des=" + des);
                                RequestCenter.product_add(url_save, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        DialogUntil.closeLoadingDialog();
                                        ShangPinAdd shangPinAdd = (ShangPinAdd) responseObj;
                                        if (shangPinAdd.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
                                            finish();
                                            if (ShangPinFenLeiActivity.instance!=null){
                                                ShangPinFenLeiActivity.instance.finish();
                                            }
                                        }else if (shangPinAdd.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                            LogOutUntil.logout(ShangPinXiangQingActivity.this);
                                            finish();
                                        }
                                        Log.i("YYY", "添加成功1");

                                    }

                                    @Override
                                    public void onFailure(Object reasonObj) {
                                        DialogUntil.closeLoadingDialog();
                                        Log.i("YYY", "添加失败1");
                                    }
                                });
                            }
                            if (type == 2) {
                                String[] s1 = imageString.split("/");//以","为分隔符，截取上面的字符串。结果为三段
                                for (int i = 0; i < s1.length; i++) {
                                    if (s1.length - 1 == i) {
                                        imageString = s1[i];
                                        Log.i("YYYY", s1[i]);
                                    }
                                }
                                String url_update = RequestURL.URL_PRODUCT_UPDATE +
                                        "productId=" + products.getId() +
                                        "&logo=" + imageString +
                                        "&name=" + name +
                                        "&productTypeId=" + productTypeId +
                                        "&price=" + price +
                                        "&discountPrice=" + discountPrice +
                                        "&ifDiscount=" + ifDiscount +
                                        "&stockNum=" + stockNum +
                                        "&des=" + des +
                                        "&sellerId=" + products.getSellerId();

                                RequestCenter.product_update(url_update, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        DialogUntil.closeLoadingDialog();
                                        ShangPinAdd shangPinAdd = (ShangPinAdd) responseObj;
                                        if (shangPinAdd.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
                                            finish();

                                        }else if (shangPinAdd.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                            LogOutUntil.logout(ShangPinXiangQingActivity.this);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Object reasonObj) {
                                        DialogUntil.closeLoadingDialog();

                                    }
                                });
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Log.i("YYY", "上传失败");
                    }
                });
            }else {
                String[] s1 = imageString.split("/");//以","为分隔符，截取上面的字符串。结果为三段
                for (int i = 0; i < s1.length; i++) {
                    if (s1.length - 1 == i) {
                        imageString = s1[i];
                        Log.i("YYYY", s1[i]);
                    }
                }
                String url_update = RequestURL.URL_PRODUCT_UPDATE +
                        "productId=" + products.getId() +
                        "&logo=" + imageString +
                        "&name=" + name +
                        "&productTypeId=" + productTypeId +
                        "&price=" + price +
                        "&discountPrice=" + discountPrice +
                        "&ifDiscount=" + ifDiscount +
                        "&stockNum=" + stockNum +
                        "&des=" + des +
                        "&sellerId=" + products.getSellerId();

                RequestCenter.product_update(url_update, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        DialogUntil.closeLoadingDialog();
                        ShangPinAdd shangPinAdd = (ShangPinAdd) responseObj;
                        if (shangPinAdd.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
                            finish();
                        }else if (shangPinAdd.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                            LogOutUntil.logout(ShangPinXiangQingActivity.this);
                            finish();
                        }else {
                            MyUntil.show(ShangPinXiangQingActivity.this,shangPinAdd.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        DialogUntil.closeLoadingDialog();

                    }
                });
            }
//        }else {
//            String[] s1=imageString.split("/");//以","为分隔符，截取上面的字符串。结果为三段
//
//            for(int i=0;i<s1.length;i++){
//                if (s1.length-1==i){
//                    image_name=s1[i];
//                    Log.i("YYYY",s1[i]);
//                }
//            }
//            String url_update = RequestURL.URL_PRODUCT_UPDATE +
//                    "productId=" + products.getId() +
//                    "&logo=" + image_name +
//                    "&name=" + name +
//                    "&productTypeId=" + productTypeId +
//                    "&price=" + price +
//                    "&discountPrice=" + discountPrice +
//                    "&ifDiscount=" + ifDiscount +
//                    "&stockNum=" + stockNum +
//                    "&des=" + des +
//                    "&sellerId=" + products.getSellerId();
//
//            RequestCenter.product_update(url_update, new DisposeDataListener() {
//                @Override
//                public void onSuccess(Object responseObj) {
//                    DialogUntil.closeLoadingDialog();
//                    ShangPinAdd shangPinAdd = (ShangPinAdd) responseObj;
//                    if (shangPinAdd.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onFailure(Object reasonObj) {
//                    DialogUntil.closeLoadingDialog();
//
//                }
//            });
//        }
    }
    //动态权限
    private void quanxian(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!SPManager.getInstance().getBoolean("isQuanXian",false)){
                getQuanXian();
            }

        }

    }
    private void getQuanXian(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, 0);
            SPManager.getInstance().putBoolean("isQuanXian", true);
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (et_goodprice.getText().toString().indexOf(".") >= 0) {
            if (et_goodprice.getText().toString().indexOf(".", et_goodprice.getText().toString().indexOf(".") + 1) > 0) {
                MyUntil.show(ShangPinXiangQingActivity.this,"已经输入\".\"不能重复输入");
                et_goodprice.setText(et_goodprice.getText().toString().substring(0, et_goodprice.getText().toString().length() - 1));
                et_goodprice.setSelection(et_goodprice.getText().toString().length());
            }

        }
        if (et_shangpinyuanjia.getText().toString().indexOf(".") >= 0) {
            if (et_shangpinyuanjia.getText().toString().indexOf(".", et_shangpinyuanjia.getText().toString().indexOf(".") + 1) > 0) {
                MyUntil.show(ShangPinXiangQingActivity.this,"已经输入\".\"不能重复输入");
                et_shangpinyuanjia.setText(et_shangpinyuanjia.getText().toString().substring(0, et_shangpinyuanjia.getText().toString().length() - 1));
                et_shangpinyuanjia.setSelection(et_shangpinyuanjia.getText().toString().length());
            }

        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
