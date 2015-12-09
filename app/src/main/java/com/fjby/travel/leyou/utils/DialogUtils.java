package com.fjby.travel.leyou.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.activity.LoginActivity;
import com.fjby.travel.leyou.application.LeYouMyApplication;
import com.fjby.travel.leyou.http.HttpCallbackListener;
import com.fjby.travel.leyou.wheel.NumericWheelAdapter;
import com.fjby.travel.leyou.wheel.WheelView;

import java.util.Calendar;

/**
 * 
 * <P>
 * 数据加载提示框显示与取消
 * <P>
 * 
 * @author Lien Li
 * @version 1.00
 */
public class DialogUtils {

	/**
	 * 数据加载对话框框消失方法，避免矿口句柄溢出
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param pd
	 *            目标对话框
	 * 
	 * 
	 * */
	public static void dismiss(Activity ctx, ProgressDialog pd) {
		if (pd != null && pd.isShowing() && ctx != null && !ctx.isFinishing())
			pd.dismiss();
	}

	/**
	 * 普通对话框框消失方法，避免矿口句柄溢出
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param dialog
	 *            目标对话框
	 * 
	 * 
	 * */
	public static void dismiss(Activity ctx, Dialog dialog) {
		if (dialog != null && dialog.isShowing() && ctx != null
				&& !ctx.isFinishing())
			dialog.dismiss();
	}

	/**
	 * 数据加载提示框显示
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static ProgressDialog showTip(Activity ctx, String title,String message) {
		ProgressDialog pd = ProgressDialog.show(ctx, title, message);
		pd.setCancelable(true);
		return pd;
	}

	/**
	 * 提示框显示
	 *
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static void mdialogShowOne( final Activity ctx, String title,String message) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
		builder.theme(Theme.LIGHT);
		builder.content(message);
		builder.title(title);
		builder.positiveText("确认");
        builder.callback(new MaterialDialog.ButtonCallback() {
			@Override
			public void onPositive(MaterialDialog dialog) {
				super.onPositive(dialog);
				dialog.dismiss();
				ctx.finish();
			}
		});
		builder.cancelable(false);
		builder.build().show();
	}
	/**
	 * 提示框显示
	 *
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static void mdialogShowNoFinish( final Activity ctx, String title,String message) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
		builder.theme(Theme.LIGHT);
		builder.content(message);
		builder.title(title);
		builder.positiveText("确认");
        builder.callback(new MaterialDialog.ButtonCallback() {
			@Override
			public void onPositive(MaterialDialog dialog) {
				super.onPositive(dialog);
				dialog.dismiss();
			}
		});
		builder.cancelable(false);
		builder.build().show();
	}
	/**
	 * 提示框显示
	 *
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static void mdialogShowQuit( final Activity ctx, String title,String message) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
		builder.theme(Theme.LIGHT);
		builder.content(message);
		builder.title(title);
		builder.positiveText("确认");
		builder.negativeText("取消");
		builder.negativeColorRes(R.color.accent);
        builder.callback(new MaterialDialog.ButtonCallback() {
			@Override
			public void onPositive(MaterialDialog dialog) {
				super.onPositive(dialog);
				SharePreferenceUtil.getInstance(ctx.getApplicationContext()).removeAll();
				LeYouMyApplication.removeAllActivities();
				IntentUtils.getInstance().startActivity(ctx, LoginActivity.class);
			}
			@Override
			public void onNegative(MaterialDialog dialog) {
				super.onNegative(dialog);
				dialog.dismiss();
			}
		});
		builder.cancelable(false);
		builder.build().show();
	}

	/**
	 * 提示框显示
	 *
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static void mdialogShowProgress( final Activity ctx, String title,String message) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
		builder.title(title).content(message).contentGravity(GravityEnum.CENTER).progress(false, 100, true).show();
	}
	/**
	 * 提示框显示
	 *
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static void mdialogShowTwo( final Activity ctx, String title,String message) {
		MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
		builder.theme(Theme.LIGHT);
		builder.content(message);
		builder.title(title);
		builder.positiveText("确认");
		builder.negativeText("取消");
		builder.negativeColorRes(R.color.accent);
		builder.callback(new MaterialDialog.ButtonCallback() {
			@Override
			public void onPositive(MaterialDialog dialog) {
				super.onPositive(dialog);
				dialog.dismiss();
			}

			@Override
			public void onNegative(MaterialDialog dialog) {
				super.onNegative(dialog);
				dialog.dismiss();
			}
		});
		builder.cancelable(false);
		builder.build().show();
	}
/*
	public static void dialogShowOne( final Activity ctx, String title,String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.MyDialog);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				ctx.finish();
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
	public static void dialogShowTwo( final Activity ctx, String title,String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.MyDialog);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
*/

}
