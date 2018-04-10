package com.example.joy.smartbutler.fragments;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.MyUser;
import com.example.joy.smartbutler.ui.CourieActivity;
import com.example.joy.smartbutler.ui.LoginActivity;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.ShareUtils;
import com.example.joy.smartbutler.view.MyDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.joy.smartbutler.utils.UtilTools.toast;

/**
 * Created by joy on 2018/3/22.
 */

public class UserFragment extends Fragment implements View.OnClickListener {


    private static final String MY_IMAGE = "myphoto.jpg";
    private CircleImageView profile_image;
    private MyDialog myDialog;
    private Button btn_take_photo;
    private Button btn_pircture;
    private Button btn_cancel;

    private EditText et_name;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    public static final int RESULT_CAMERA_CODE = 100;
    public static final int RESULT_PICTURE_CODE = 200;
    private static final int RESULT_CROP_CODE = 300;
    private Button btn_logout;
    private TextView tv_update;
    private Button btn_update;

    //物流查询
    private TextView tv_courie;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        String imgStr = ShareUtils.getString(getActivity(), "image_title", "");
        if (!TextUtils.isEmpty(imgStr)) {
            byte[] byteArray = Base64.decode(imgStr, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            profile_image.setImageBitmap(bitmap);
        }

        myDialog = new MyDialog(getActivity(), R.style.dialogAnim, R.layout.dialog_picture, Gravity.BOTTOM);
        btn_take_photo = myDialog.findViewById(R.id.btn_take_photo);
        btn_pircture = myDialog.findViewById(R.id.btn_picture);
        btn_cancel = myDialog.findViewById(R.id.btn_cancel);

        btn_take_photo.setOnClickListener(this);
        btn_pircture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);


        //设置个人信息
        et_name = view.findViewById(R.id.et_name);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);

        setEditTextEnable(false);

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        et_name.setText(user.getUsername());
        et_sex.setText(user.getGender()?"男":"女");
        et_age.setText(user.getAge() + "");
        et_desc.setText(TextUtils.isEmpty(user.getDesc()) ? "这个人很懒，什么都没有留下" : user.getDesc());

        //编辑个人信息
        tv_update = view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        //物流查询
        tv_courie = view.findViewById(R.id.tv_courie);
        tv_courie.setOnClickListener(this);
    }

    private void setEditTextEnable(boolean isVisiable){

        et_name.setEnabled(isVisiable);
        et_sex.setEnabled(isVisiable);
        et_age.setEnabled(isVisiable);
        et_desc.setEnabled(isVisiable);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                myDialog.show();
                myDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.btn_cancel:
                myDialog.cancel();
                break;
            case R.id.btn_take_photo:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;

            case R.id.btn_logout: //退出
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                if(currentUser==null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(), "退出失败", Toast.LENGTH_SHORT).show();
                }
                break;

                //编辑个人信息
            case R.id.tv_update:

                btn_update.setVisibility(View.VISIBLE);
                setEditTextEnable(true);
                break;
            case R.id.btn_update:
                MyUser myUser = MyUser.getCurrentUser(MyUser.class);
                if(myUser==null)return;
                myUser.setUsername(et_name.getText().toString());
                myUser.setAge(Integer.parseInt(et_age.getText().toString()));
                myUser.setGender(et_sex.equals("男")?true:false);
                myUser.setDesc(et_desc.getText().toString());
                myUser.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            toast("更新用户信息成功");
                            setEditTextEnable(false);
                            btn_update.setVisibility(View.GONE);
                        }else{
                            toast("更新用户信息失败:" + e.getMessage());
                        }
                    }
                });

                break;
                //物流查询
            case R.id.tv_courie:
                CourieActivity.createIntent(getActivity());
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RESULT_CAMERA_CODE://摄像头
                    startPhotoZoom(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), MY_IMAGE)));
                    break;
                case RESULT_PICTURE_CODE://相册
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_CROP_CODE:
                    setImageToView(data);
                    break;
                default:
                    break;
            }
        }
    }

    //打开相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_PICTURE_CODE);
        myDialog.dismiss();
    }

    //打开摄像头
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), MY_IMAGE)));
        startActivityForResult(intent, RESULT_CAMERA_CODE);
        myDialog.dismiss();
    }

    //设置裁剪后的图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据(裁剪返回的时候需要)
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_CROP_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BitmapDrawable bitmapDrawable= (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        ShareUtils.putString(getActivity(),"image_title",imgString);

    }
}
