package com.TefillinGuide;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Mirror extends Activity implements PictureCallback {
	private final static String DEBUG_TAG = "MirrorActivity";
	private Camera mCam;
	private MirrorView mCamPreview;
	private int mCameraId = 0;
	private FrameLayout mPreviewLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mirror);

		// do we have a camera?
		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, "No camera feature on this device",
					Toast.LENGTH_LONG).show();
		} else {

			mCameraId = findFirstFrontFacingCamera();

			if (mCameraId >= 0) {
				mPreviewLayout = (FrameLayout) findViewById(R.id.camPreview);
				mPreviewLayout.removeAllViews();

				startCameraInLayout(mPreviewLayout, mCameraId);

			} else {
				Toast.makeText(this, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
				
				/**
				 * Call this method and use this code for the back camera if desired
				 */
//				mCameraId = useBackCamera();
//				 if (mCameraId == 0) {
//				 mPreviewLayout = (FrameLayout) findViewById(R.id.camPreview);
//				 mPreviewLayout.removeAllViews();
//				
//				 startCameraInLayout(mPreviewLayout, mCameraId);
//				 }
			}
		}
	}

	private int findFirstFrontFacingCamera() {
		int foundId = -1;
		// find the first front facing camera
		int numCams = Camera.getNumberOfCameras();
		for (int camId = 0; camId < numCams; camId++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(camId, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(DEBUG_TAG, "Found front facing camera");
				foundId = camId;
				break;
			}
		}
		return foundId;
	}

	/**
	 * Use this method for the back camera if desired
	 */
	// private int useBackCamera() {
	// int foundId = -1;
	// // find the first front facing camera
	// int numCams = Camera.getNumberOfCameras();
	// for (int camId = 0; camId < numCams; camId++) {
	// CameraInfo info = new CameraInfo();
	// Camera.getCameraInfo(camId, info);
	// if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
	// Log.d(DEBUG_TAG, "Found back facing camera");
	// foundId = camId;
	// break;
	// }
	// }
	// return foundId;
	// }

	private void startCameraInLayout(FrameLayout layout, int cameraId) {

		// TODO pull this out of the UI thread.
		mCam = Camera.open(cameraId);
		if (mCam != null) {
			mCamPreview = new MirrorView(this, mCam);
			layout.addView(mCamPreview);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mCam == null && mPreviewLayout != null) {
			mPreviewLayout.removeAllViews();
			startCameraInLayout(mPreviewLayout, mCameraId);
		}
	}

	@Override
	protected void onPause() {
		if (mCam != null) {
			mCam.release();
			mCam = null;
		}
		super.onPause();
	}

	public class MirrorView extends SurfaceView implements
			SurfaceHolder.Callback {
		private SurfaceHolder mHolder;
		private Camera mCamera;

		public MirrorView(Context context, Camera camera) {
			super(context);
			mCamera = camera;
			mHolder = getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		public void surfaceCreated(SurfaceHolder holder) {
			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (Exception error) {
				Log.d(DEBUG_TAG,
						"Error starting mPreviewLayout: " + error.getMessage());
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			if (mHolder.getSurface() == null) {
				return;
			}

			// can't make changes while mPreviewLayout is active
			try {
				mCamera.stopPreview();
			} catch (Exception e) {

			}

			try {
				// set rotation to match device orientation
				setCameraDisplayOrientationAndSize();

				// start up the mPreviewLayout
				mCamera.setPreviewDisplay(mHolder);
				mCamera.startPreview();

			} catch (Exception error) {
				Log.d(DEBUG_TAG,
						"Error starting mPreviewLayout: " + error.getMessage());
			}
		}

		public void setCameraDisplayOrientationAndSize() {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(mCameraId, info);
			int rotation = getWindowManager().getDefaultDisplay().getRotation();
			int degrees = rotation * 90;

			/*
			 * // the above is just a shorter way of doing this, but could break
			 * if the values change switch (rotation) { case Surface.ROTATION_0:
			 * degrees = 0; break; case Surface.ROTATION_90: degrees = 90;
			 * break; case Surface.ROTATION_180: degrees = 180; break; case
			 * Surface.ROTATION_270: degrees = 270; break; }
			 */

			int result;
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				result = (info.orientation + degrees) % 360;
				result = (360 - result) % 360;
			} else {
				result = (info.orientation - degrees + 360) % 360;
			}
			mCamera.setDisplayOrientation(result);

			Camera.Size previewSize = mCam.getParameters().getPreviewSize();
			if (result == 90 || result == 270) {
				// swap - the physical camera itself doesn't rotate in relation
				// to the screen ;)
				mHolder.setFixedSize(previewSize.height, previewSize.width);
			} else {
				mHolder.setFixedSize(previewSize.width, previewSize.height);

			}
		}

	}

	@Override
	public void onPictureTaken(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub

	}
}
