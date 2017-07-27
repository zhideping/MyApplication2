package com.bjxiyang.zhinengshequ.shop.bluetooth;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bjxiyang.zhinengshequ.shop.R;
import com.gprinter.io.CustomerDisplay;
import com.gprinter.io.GpEquipmentPort;

import java.io.IOException;

public class CustomerDiaplayActivity extends Activity implements View.OnClickListener, GpEquipmentPort.OnDataReceived {

	private CustomerDisplay port;

	private EditText mInputText;
	private EditText mEtDisplayTimeout;
	private EditText mEtX;
	private EditText mEtY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);

		mEtDisplayTimeout = (EditText) findViewById(R.id.et_display_timeout);
		mEtX = (EditText) findViewById(R.id.et_x);
		mEtY = (EditText) findViewById(R.id.et_y);
		mInputText = (EditText) findViewById(R.id.et_input_text);

		findViewById(R.id.btnClear).setOnClickListener(this);
		findViewById(R.id.btnReset).setOnClickListener(this);
		findViewById(R.id.btnDisplayBitmap).setOnClickListener(this);
		findViewById(R.id.btnSetTextCurrentCursor).setOnClickListener(this);
		findViewById(R.id.btnSetTextBebindCursor).setOnClickListener(this);
		findViewById(R.id.btnTurnOnBackLight).setOnClickListener(this);
		findViewById(R.id.btnSetDisplayTimeout).setOnClickListener(this);
		findViewById(R.id.btnSetCursorPosition).setOnClickListener(this);
		findViewById(R.id.btnOffBackLight).setOnClickListener(this);
		findViewById(R.id.btnGetCursorPosition).setOnClickListener(this);
		findViewById(R.id.btnGetDisplayRowAndColumn).setOnClickListener(this);
		findViewById(R.id.btnGetDisplayStatus).setOnClickListener(this);
		findViewById(R.id.btnGetDisplayTimeout).setOnClickListener(this);
		findViewById(R.id.btnSetContrast).setOnClickListener(this);
		findViewById(R.id.btnBrightness).setOnClickListener(this);
		findViewById(R.id.btnOpenPort).setOnClickListener(this);
		findViewById(R.id.btnClosePort).setOnClickListener(this);
		findViewById(R.id.btnGetPortStatus).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int clickId = v.getId();
		if (clickId == R.id.btnOpenPort) {
			openPort();
           
			return;
		} else if (clickId == R.id.btnClosePort) {

			closePort();
           // toast("端口关闭");
			return;
		}
		if (port == null || !port.isPortOpen()) {
			toast("请打开端口");
			return;
		}
		switch (clickId) {
		case R.id.btnGetPortStatus:
			getPortStatus();
			break;
		case R.id.btnReset:
			reset();
			break;
		case R.id.btnDisplayBitmap:
			displayBitmap();
			break;
		case R.id.btnClear:
			clear();
			break;
		case R.id.btnTurnOnBackLight:
			turnOnBacklight();
			break;
		case R.id.btnOffBackLight:
			turnOffBacklight();
			break;
		case R.id.btnSetDisplayTimeout:
			setDisplayTimeout();
			break;
		case R.id.btnSetCursorPosition:
			setCursorPosition();
			break;
		case R.id.btnSetTextCurrentCursor:
			setTextCurrentCursor();
			break;
		case R.id.btnSetTextBebindCursor:
			setTextBebindCursor();
			break;
		case R.id.btnGetDisplayStatus:
			getDisplayStatus();
			break;
		case R.id.btnGetCursorPosition:
			getCursorPosition();
			break;
		case R.id.btnGetDisplayRowAndColumn:
			getDisplayRowAndColumn();
			break;
		case R.id.btnGetDisplayTimeout:
			getDisplayTimeout();
			break;
		case R.id.btnSetContrast:
			setContrast();
			break;
		case R.id.btnBrightness:
			setBrightness();
			break;
		}
	}

	private void openPort() {
		port = CustomerDisplay.getInstance(this);
		try {
			// 打开端口
			port.openPort();
			
		} catch (IOException e) {
			e.printStackTrace();
			toast("端口打开");
		}
		// 设置监听回调数据
		port.setReceivedListener(this);
		port.getBacklightTimeout();
	
	}

	private void closePort() {
		if (port != null) {
			port.closeSerialPort();
			toast("关闭端口");
		}
	}

	private void getPortStatus() {

		if (port != null) {
			toast("端口状态：" + (port.isPortOpen() ? "打开" : "关闭"));
		} else {
			toast("端口状态：关闭");
		}

	}

	private void setBrightness() {
		byte brightness = Byte.valueOf(((Spinner) findViewById(R.id.spinner_brightness)).getSelectedItem().toString());
		port.setBrightness(brightness);
	}

	private void setContrast() {
		byte contrast = Byte.valueOf(((Spinner) findViewById(R.id.spinner_contrast)).getSelectedItem().toString());
		port.setContrast(contrast);
	}

	/**
	 * set 部分
	 */
	private void turnOffBacklight() {
		port.setBacklight(false);
	}

	private void reset() {
		port.reset();
	}

	private void clear() {
		port.clear();
	}

	private void displayBitmap() {
		Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.fl));
		port.displayBitmap(bitmap, 48);
	}

	private void turnOnBacklight() {
		port.setBacklight(true);
	}

	private void setDisplayTimeout() {
		String displayTimeoutStr = mEtDisplayTimeout.getText().toString();
		if (TextUtils.isEmpty(displayTimeoutStr)) {
			Toast.makeText(CustomerDiaplayActivity.this, R.string.str_input_display_timeout, Toast.LENGTH_SHORT).show();
			return;
		}

		int timeout = Integer.parseInt(displayTimeoutStr);
		port.setBacklightTimeout(timeout);

	}

	private void setCursorPosition() {
		String xStr = mEtX.getText().toString();
		String yStr = mEtY.getText().toString();
		if (TextUtils.isEmpty(xStr) || TextUtils.isEmpty(yStr)) {
			Toast.makeText(this, R.string.str_please_input_x_y, Toast.LENGTH_SHORT).show();
			return;
		}

		int x = Integer.parseInt(xStr);
		int y = Integer.parseInt(yStr);

		port.setCursorPosition(x, y);

		// mEtX.setText(xStr);
		// mEtY.setText(yStr);
	}

	/**
	 * 得到的inputText的GB2312编码的长度小于256且大于0
	 */
	private void setTextCurrentCursor() {
		String inputText = mInputText.getText().toString();
		port.setTextCurrentCursor(inputText);
	}

	/**
	 * 得到的inputText的GB2312编码的长度小于256且大于0
	 */
	private void setTextBebindCursor() {
		String inputText = mInputText.getText().toString();
		port.setTextBebindCursor(inputText);
	}

	/**
	 * get 部分
	 */

	// 获取背光灯状态
	private void getDisplayStatus() {
		port.getBacklight();
	}

	// 获取客显超时时间
	private void getDisplayTimeout() {
		port.getBacklightTimeout();
	}

	// 获取客显光标位置坐标
	private void getCursorPosition() {
		port.getCursorPosition();
	}

	// 获取客显光行列数
	private void getDisplayRowAndColumn() {
		port.getDisplayRowAndColumn();
	}

	/**
	 * 获取客显屏的状态开启或关闭
	 *
	 * @param isOpen
	 *            客显屏背光灯开启或关闭
	 */
	@Override
	public void onPortOpen(boolean isOpen) {
		if (isOpen) {
			toast("打开端口成功");
		} else {
			toast("打开端口失败");
		}

	}

	/**
	 * 获取客显屏背光灯开启或关闭
	 *
	 * @param isOn
	 *            客显屏背光灯开启或关闭
	 */
	@Override
	public void onBacklightStatus(final boolean isOn) {
		Log.d("==onBacklightStatus==", String.valueOf(isOn));

		toast("==onBacklightStatus== 背光灯状态->" + String.valueOf(isOn));
	}

	/**
	 * 获取客显屏光标的位置
	 *
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 */
	@Override
	public void onCursorPosition(final int x, final int y) {
		toast("==onCursorPosition==x = " + x + ",y =" + y);
		Log.d("==onCursorPosition==", "x坐标 = " + x + ",y坐标 =" + y);
	}

	/**
	 * 获取客显屏的行和列
	 *
	 * @param row
	 *            行
	 * @param column
	 *            列
	 */
	@Override
	public void onDisplayRowAndColumn(final int row, final int column) {
		toast("行数 = " + row + ",列数 =" + column);
		Log.d("==onCursorPosition==", "row = " + row + ",column =" + column);
	}

	/**
	 * 获取客显屏背光灯超时时间
	 *
	 * @param timeout
	 *            单位：秒
	 */
	@Override
	public void onBacklightTimeout(final int timeout) {
		toast("超时时间 = " + timeout);
		Log.d("==onBacklightTimeout==", "timeout = " + timeout);
	}

	/**
	 * 销毁activity时，关闭端口
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (port != null) {
			port.closeSerialPort();
		}
	}

	private void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
