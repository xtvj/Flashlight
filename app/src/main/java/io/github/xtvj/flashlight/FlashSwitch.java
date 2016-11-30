package io.github.xtvj.flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;



public class FlashSwitch {

    public static void setFlashlightEnabled(Context context, boolean isEnable) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();

            for (int i = 0; i < cameraIdList.length; i++) {
                CameraCharacteristics camera = cameraManager.getCameraCharacteristics(cameraIdList[i]);
                Boolean has = camera.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (has != null && has) {
                    cameraManager.setTorchMode(cameraIdList[i], isEnable);
                }
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}
