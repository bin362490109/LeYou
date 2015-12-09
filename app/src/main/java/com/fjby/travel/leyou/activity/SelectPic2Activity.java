package com.fjby.travel.leyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.fjby.travel.leyou.R;

import java.io.File;
import java.io.IOException;

public class SelectPic2Activity extends Activity {

    private Button selectpic2_carmebtn;
    private Button selectpic2_xiangce;
    private Button selectpic2_cancel;

    //参数
    private Bitmap photo;
    private boolean hasPic1 = false;
    private File file;

    //使用照相机拍照获取图片
    public static final int PIC_FromCamea = 1;
    //使用相册中的图片
    public static final int PIC_FromDCIM = 2;


    private Uri photoUri;
    private String picPath;

    //是从哪个界面来的
    private int picnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic2);
        bindViews();

        picnum = getIntent().getIntExtra("picname", 0);
        selectpic2_carmebtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {

                    String saveDir = Environment.getExternalStorageDirectory() + "/porktemp/";
                    Log.v("crb", "临时路径:" + saveDir);
                    try {
                        File dir = new File(saveDir);
                        if (!dir.exists()) {
                            Log.v("crb", "新建目录:" + saveDir);
                            dir.mkdir();
                            dir.setReadable(true);
                            dir.setWritable(true);
                        }
                        if (picnum == 2) {
                            file = new File(saveDir + "temp2.jpg");
                        } else if (picnum == 3) {
                            file = new File(saveDir + "temp3.jpg");
                        } else {
                            file = new File(saveDir + "temp.jpg");
                        }

                        if (file.exists()) {
                            try {
                                Log.v("crb", "删除文件:" + file.getAbsolutePath());
                                file.delete();
                            } catch (Exception e) {
                                Log.v("crb", "删除文件失败");
                                e.printStackTrace();
                            }
                        }


                        if (!file.exists()) {
                            try {
                                Log.v("crb", "创建文件:" + file.getAbsolutePath());
                                file.createNewFile();
                                file.setReadable(true);
                                file.setWritable(true);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.v("crb", e.toString() + e.getMessage());
                                Toast.makeText(SelectPic2Activity.this, "文件创建失败", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(SelectPic2Activity.this, "文件操作失败", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                    startActivityForResult(intent, PIC_FromCamea);

                } else {
                    Toast.makeText(SelectPic2Activity.this, "存储卡不存在", Toast.LENGTH_LONG).show();
                }

            }

        });


        selectpic2_xiangce.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //修改为以下两句代码
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//使用以上这种模式，并添加以上两句
                startActivityForResult(intent, PIC_FromDCIM);

            }
        });

        selectpic2_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PIC_FromCamea: {
                    Intent intent = new Intent();
                    intent.putExtra("filename", file.getAbsolutePath());
                    intent.putExtra("picnum", picnum);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                    break;
                }
                case PIC_FromDCIM: {
                    Log.v("crb", "返回到selectpicactivity");
                    if (data == null) {
                        Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                    photoUri = data.getData();
                    if (photoUri == null) {
                        Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    cursor.close();
                }
                Log.i("zms", "imagePath = " + picPath);
                    /*if(picPath != null && ( picPath.endsWith(".png") || picPath.endsWith(".PNG") ||picPath.endsWith(".jpg") ||picPath.endsWith(".JPG")  ))*/
                if (picPath != null) {
                    Intent intent = new Intent();
                    intent.putExtra("filename", picPath);
                    intent.putExtra("picnum", picnum);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "选择文件不正确!", Toast.LENGTH_LONG).show();

                }
                break;
            }
        } else {
            Log.v("crb", "未选择 或者未拍照");
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

    private void bindViews() {
        selectpic2_carmebtn = (Button) findViewById(R.id.selectpic2_carmebtn);
        selectpic2_xiangce = (Button) findViewById(R.id.selectpic2_xiangce);
        selectpic2_cancel = (Button) findViewById(R.id.selectpic2_cancel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryBimap();
    }
}
