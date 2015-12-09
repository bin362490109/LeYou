package com.fjby.travel.leyou.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.tools.HttpTools;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.MyAppealActivity;
import com.fjby.travel.leyou.activity.SelectPic2Activity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpUtil;
import com.fjby.travel.leyou.pojo.Result;
import com.fjby.travel.leyou.utils.BitmapUtils;
import com.fjby.travel.leyou.utils.LogUtil;
import com.fjby.travel.leyou.utils.StringUtils;
import com.fjby.travel.leyou.utils.ToastUtils;
import com.fjby.travel.leyou.utils.Tools;
import com.fjby.travel.leyou.wheel.CustomMaterialDate;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

public class MyAppealFragment extends Fragment {
    private Button buttonSubmit;
    private Spinner typeSpinner;  //诉求类型
    private Spinner categorySpinner;  //诉求类别
    private EditText timeEdit;  //诉求时间
    private TextView addressText;  //诉求地点
    private TextView more_Text;  //诉求更多
    private ImageView sy_pic;
    private ImageView sy_pic2;
    private ImageButton sy_camera_imgbtn;
    private ImageButton sy_camera_imgbtn2;
    private ImageButton sy_camera_deleteBtn;
    private ImageButton sy_camera_deleteBtn2;
    private TextView sy_filesize;
    private TextView sy_filesize2;

    private ArrayAdapter<String> type_adapter;
    private ArrayAdapter<String> category_adapter;

    private MyAppealActivity myAppealActivity;
    private Animation shake;
    private String[] myappealType;
    private String[] myappealCategory;
    private Bitmap photo;
    private Bitmap photo2;
    private boolean hasPic = false;
    private boolean hasPic2 = false;
    private File file;
    private File file2;
    private  HttpTools httpTools;
    private MaterialDialog.Builder builder ;
    private MaterialDialog materialDialog;
    private CustomMaterialDate custonMaterialDate;
    /* 内容类型 */
    private String contentType = "application/octet-stream";
    //上传参数
    //private Map<String, String> params = new HashMap<>();//post的StringBody

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myappealType = getResources().getStringArray(R.array.myappeal_type);
        myappealCategory = getResources().getStringArray(R.array.myappeal_category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myappeal, container, false);
        setHasOptionsMenu(true);
        initView(view);
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        myAppealActivity = (MyAppealActivity) getActivity();
        myAppealActivity.setToolbarTitle(R.string.myappeal_title);
        initLinsten();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (photo != null && !photo.isRecycled()) {
            LogUtil.e("onResume1");
            sy_pic.setImageBitmap(photo);
        }
        if (photo2 != null && !photo2.isRecycled()) {
            LogUtil.e("onResume2");
            sy_pic2.setImageBitmap(photo2);
        }
        LogUtil.e("onResume3" + myAppealActivity.titile + " ----   " + myAppealActivity.content);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destoryBimap();
        destoryBimap2();
        if (materialDialog!=null&&materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog=null;
        }
        if (custonMaterialDate!=null){
            custonMaterialDate.dismiss();
            custonMaterialDate=null;
        }

        LogUtil.e("onDestroy   onDestroy   onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2: {
                    destoryBimap();
                    String filename = data.getStringExtra("filename");
                    LogUtil.v(filename);
                    file = new File(filename);
                    if (file != null && file.exists()) {
                        LogUtil.v("开始处理图片");
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //设置把图片的大小 缩放多少 几分之几  ，1 不缩放
                        options.inSampleSize = 2;
                        photo = BitmapFactory.decodeFile(file.getPath(), options);
                        photo = BitmapUtils.decodeThumbBitmapForFile(file.getPath(), sy_pic.getWidth(), sy_pic.getHeight());
                        LogUtil.v("图片地址:" + file.getPath());
                        /**
                         * 把图片旋转为正的方向
                         */
                        //  int degree = Tools.readPictureDegree(file1.getAbsolutePath());
                        //  Bitmap newbitmap = Tools.rotaingImageView(degree, photo);
                        sy_pic.setImageBitmap(photo);
                        hasPic = true;
                        sy_filesize.setText("文件大小:" + Tools.FormetFileSize(file.length()));
                        sy_filesize.setVisibility(View.VISIBLE);

                    } else {
                        ToastUtils.show(getActivity(), "图片文件未找到", Toast.LENGTH_LONG);
                        hasPic = false;
                        sy_filesize.setVisibility(View.GONE);
                    }
                    break;
                }
                case 3: {
                    destoryBimap2();
                    String filename = data.getStringExtra("filename");
                    LogUtil.v(filename);
                    file2 = new File(filename);
                    if (file2 != null && file2.exists()) {
                        LogUtil.v("开始处理图片");
                        photo2 = BitmapUtils.decodeThumbBitmapForFile(file2.getPath(),sy_pic2.getWidth(), sy_pic2.getHeight());
                        LogUtil.v("图片地址:" + file2.getPath());
                        /**
                         * 把图片旋转为正的方向
                         */
                        // int degree = Tools.readPictureDegree(file2.getAbsolutePath());
                        //   Bitmap newbitmap = Tools.rotaingImageView(degree, photo2);
                        sy_pic2.setImageBitmap(photo2);
                        hasPic2 = true;
                        sy_filesize2.setText("文件大小:" + Tools.FormetFileSize(file2.length()));
                        sy_filesize2.setVisibility(View.VISIBLE);

                    } else {
                        ToastUtils.show(getActivity(), "图片文件未找到", Toast.LENGTH_LONG);
                        hasPic2 = false;
                        sy_filesize2.setVisibility(View.GONE);
                    }
                    break;
                }
                default:
                    break;
            }
        } else {
            LogUtil.v("未选择图片");
        }

    }


    /**
     * 销毁图片文件
     */
    private void destoryBimap() {
        if (photo != null && !photo.isRecycled()) {
            photo.recycle();
            photo = null;
        }
    }


    private void destoryBimap2() {
        if (photo2 != null && !photo2.isRecycled()) {
            photo2.recycle();
            photo2 = null;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_more:
                myAppealActivity.repalceFragmentWithTag(new MyAppealAllFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        HttpTools.init(getActivity());
          httpTools=new HttpTools(getActivity());
        builder = new MaterialDialog.Builder(getActivity());
        builder.title("文件上传").content("文件上传中").contentGravity(GravityEnum.CENTER).progress(false, 100, true).theme(Theme.LIGHT);
        custonMaterialDate=new CustomMaterialDate(getActivity(),new CustomMaterialDate.TextCallbackListener() {
            @Override
            public void getTime(String response) {
                timeEdit.setText(response);
            }}
        );
        buttonSubmit = (Button) view.findViewById(R.id.sy_up1);
        typeSpinner = (Spinner) view.findViewById(R.id.myappeal_type_spinner);
        categorySpinner = (Spinner) view.findViewById(R.id.myappeal_category_spinner);
        timeEdit = (EditText) view.findViewById(R.id.myappeal_time_edit);
        addressText = (TextView) view.findViewById(R.id.myappeal_address_text);
        more_Text = (TextView) view.findViewById(R.id.myappeal_more_text);
        sy_pic = (ImageView) view.findViewById(R.id.sy_pic);
        sy_camera_imgbtn = (ImageButton) view.findViewById(R.id.sy_camera_imgbtn);
        sy_camera_deleteBtn = (ImageButton) view.findViewById(R.id.sy_camera_deleteBtn);
        sy_filesize = (TextView) view.findViewById(R.id.sy_filesize);
        sy_pic2 = (ImageView) view.findViewById(R.id.sy_pic2);
        sy_camera_imgbtn2 = (ImageButton) view.findViewById(R.id.sy_camera_imgbtn2);
        sy_camera_deleteBtn2 = (ImageButton) view.findViewById(R.id.sy_camera_deleteBtn2);
        sy_filesize2 = (TextView) view.findViewById(R.id.sy_filesize2);

        //将可选内容与ArrayAdapter连接起来
        type_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, myappealType);
             /* //设置下拉列表的风格 */
        type_adapter.setDropDownViewResource(R.layout.spinner_layout);
        //将adapter 添加到spinner中
        typeSpinner.setAdapter(type_adapter);
        //将可选内容与ArrayAdapter连接起来
        category_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, myappealCategory);
                 /* //设置下拉列表的风格 */
        category_adapter.setDropDownViewResource(R.layout.spinner_layout);
        //将adapter 添加到spinner中
        categorySpinner.setAdapter(category_adapter);
    }

    private void initLinsten() {
        more_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAppealActivity.repalceFragmentWithTag(new MyAppealTwoFragment());
            }
        });

        sy_camera_imgbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), SelectPic2Activity.class);
                intent1.putExtra("picname", 2);
                startActivityForResult(intent1, 2);

            }
        });
        sy_camera_imgbtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), SelectPic2Activity.class);
                intent1.putExtra("picname", 3);
                startActivityForResult(intent1, 3);

            }
        });
        sy_camera_deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPic) {
                    sy_pic.setImageResource(R.color.image_bg);
                    hasPic = false;
                    sy_filesize.setVisibility(View.GONE);
                    destoryBimap();
                } else {
                    ToastUtils.show(getActivity(), "还未上传图片!无法删除默认图片", Toast.LENGTH_LONG);
                }

            }
        });
        sy_camera_deleteBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (hasPic2) {
                    sy_pic2.setImageResource(R.color.image_bg);
                    hasPic2 = false;
                    sy_filesize2.setVisibility(View.GONE);
                    destoryBimap2();
                } else {
                    ToastUtils.show(getActivity(), "还未上传图片!无法删除默认图片", Toast.LENGTH_LONG);
                }

            }
        });
       timeEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               custonMaterialDate.show();
           }
       });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("长度："+sy_pic2.getWidth()+"  ----  "+sy_pic2.getHeight());
                LogUtil.v("crb", "点击UP");
                if (StringUtils.isEmpty(timeEdit.getText().toString())) {
                    timeEdit.requestFocus();
                    ToastUtils.show(getActivity(), "事件时间未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (StringUtils.isEmpty(addressText.getText().toString())) {
                    addressText.startAnimation(shake);
                    ToastUtils.show(getActivity(), "事件地点未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (StringUtils.isEmpty(myAppealActivity.titile) || StringUtils.isEmpty(myAppealActivity.content)) {
                    more_Text.startAnimation(shake);
                    ToastUtils.show(getActivity(), "诉求详情未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (hasPic || hasPic2) {
                    HashMap<String , Object >paramsMap=new HashMap<String, Object>();
                    if (LeYouMyApplication.mUser != null) {
                        paramsMap.put("guid", LeYouMyApplication.mUser.getGuid());
                        paramsMap.put("verifycode", LeYouMyApplication.mUser.getVerifyCode());
                    } else {
                        paramsMap.put("guid", LeYouMyApplication.mCashHhid);
                        paramsMap.put("verifycode", "-1");
                    }
                    paramsMap.put("check", "");
                    paramsMap.put("ver", LeYouMyApplication.versionName);
                    paramsMap.put("usertype", "1");
                    paramsMap.put("imei", LeYouMyApplication.imei);

                    paramsMap.put("addressk", addressText.getText().toString());
                    paramsMap.put("timek", timeEdit.getText().toString().replaceAll("-",""));
                    paramsMap.put("classk", categorySpinner.getSelectedItem().toString());
                    paramsMap.put("typek", typeSpinner.getSelectedItem().toString());
                    paramsMap.put("titelk", myAppealActivity.titile);
                    paramsMap.put("cntnk", myAppealActivity.content);
                    paramsMap.put("file", new File("/storage/emulated/0/fjds/download/bbb.txt"));
                    paramsMap.put("file", new File("/storage/emulated/0/fjds/download/aaa.txt"));
                    if (hasPic && !hasPic2) {
                        paramsMap.put("file", file);
                    } else if (hasPic2 && !hasPic) {
                        paramsMap.put("file", file2);
                    } else if (hasPic2 && hasPic) {
                        paramsMap.put("file", file);
                        paramsMap.put("file2", file2);
                    }
                  	httpTools.upload(HttpUtil.UPLOAD_URL, paramsMap, new HttpCallback() {
                        @Override
                        public void onStart() {
                            // TODO Auto-generated method stub
                            materialDialog=builder.show();
                            LogUtil.e( "onStart");
                        }

                        @Override
                        public void onResult(String string) {
                            // TODO Auto-generated method stub
                            LogUtil.e( "onResult="+string);
                            Gson gson=new Gson();
                            Result result=gson.fromJson(string,Result.class);
                            if (result.getStateCode()==600){
                                ToastUtils.show(getActivity(),"上传成功",Toast.LENGTH_LONG);
                            }else{
                                ToastUtils.show(getActivity(),"上传失败",Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onLoading(long count, long current) {
                            // TODO Auto-generated method stub
                            LogUtil.e( "onLoading="+count+"     "+current);
                            materialDialog.setContent("文件上传中");
                            materialDialog.setProgress((int)(current/count*100));

                        }

                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            LogUtil.e( "onFinish");
                            materialDialog.setContent("文件上传结");
                            materialDialog.dismiss();
                        }

                        @Override
                        public void onError(Exception e) {
                            // TODO Auto-generated method stub
                            LogUtil.e( "onError="+e);
                            ToastUtils.show(getActivity(),"上传失败："+e,Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onCancelled() {
                            // TODO Auto-generated method stub
                            materialDialog.dismiss();
                            LogUtil.e( "onCancelled");
                        }
                    });
                } else {
                    ToastUtils.show(getActivity(), "至少上传一张图片", Toast.LENGTH_LONG);
                }
            }
        });
/*
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.v("crb", "点击UP");
                if (StringUtils.isEmpty(timeEdit.getText().toString())) {
                    timeEdit.requestFocus();
                    ToastUtils.show(getActivity(), "事件时间未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (StringUtils.isEmpty(addressText.getText().toString())) {
                    addressText.startAnimation(shake);
                    ToastUtils.show(getActivity(), "事件地点未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (StringUtils.isEmpty(myAppealActivity.titile) || StringUtils.isEmpty(myAppealActivity.content)) {
                    more_Text.startAnimation(shake);
                    ToastUtils.show(getActivity(), "诉求详情未填写", Toast.LENGTH_LONG);
                    return;
                }
                if (hasPic || hasPic2) {
                    try {
                        LogUtil.v("crb", "开始准备");
                        if (LeYouMyApplication.mUser != null) {
                            params.put("guid", LeYouMyApplication.mUser.getGuid());
                            params.put("verifycode", LeYouMyApplication.mUser.getVerifyCode());
                        } else {
                            params.put("guid", LeYouMyApplication.mCashHhid);
                            params.put("verifycode", "-1");
                        }
                        params.put("check", "");
                        params.put("ver", LeYouMyApplication.versionName);
                        params.put("usertype", "1");
                        params.put("imei", LeYouMyApplication.imei);

                        params.put("addressk", addressText.getText().toString());
                        params.put("timek", timeEdit.getText().toString());
                        params.put("classk", categorySpinner.getSelectedItem().toString());
                        params.put("typek", typeSpinner.getSelectedItem().toString());
                        params.put("titelk", myAppealActivity.titile);
                        params.put("cntnk", myAppealActivity.content);
                        FormFile fromFile1 = null;
                        FormFile fromFile2 = null;
                        if (hasPic) {
                            fromFile1 = new FormFile(file.getName(), file, "file1", contentType);
                        }
                        if (hasPic2) {
                            fromFile2 = new FormFile(file2.getName(), file2, "file2", contentType);
                        }
                        if (hasPic && !hasPic2) {
                            files = new FormFile[]{fromFile1};
                        } else if (hasPic2 && !hasPic) {
                            files = new FormFile[]{fromFile2};
                        } else if (hasPic2 && hasPic) {
                            files = new FormFile[]{fromFile1, fromFile2};
                        }
                    } catch (Exception e) {
                        LogUtil.v("获取参数出错:" + e.getMessage() + "--" + e.toString());
                    }
                    new Thread(new Runnable() {  //开启线程上传文件
                        public void run() {
                            Looper.prepare();
                            try {
                                LogUtil.v("进入线程");
                                // MutlUploadUtil.post( "http://192.168.0.54:8080/tour/tourApi?req=UploadBean", params, files);
                                //   MutlUploadUtil.post( "http://192.168.0.54:8080/TestUpload/servlet/UpFromAndroid", params, files);
                                boolean s = MutlUploadUtil.post(MutlUploadUtil.API_URL, params, files);
                                ToastUtils.show(getActivity(), "图片上传" + s, Toast.LENGTH_LONG);
                            } catch (Exception e) {
                                LogUtil.v("上传出错:" + e.getMessage() + "--" + e.toString());

                            }
                            Looper.loop();
                        }
                    }).start();

                } else {
                    ToastUtils.show(getActivity(), "至少上传一张图片", Toast.LENGTH_LONG);
                }
            }
        });
*/
    }
}
