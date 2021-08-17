package com.teammovil.pettracker.ui.camera

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CameraConfiguration (
    var fileName: String? = "",
    var mediaType: Int = CameraConstants.REQUEST_IMAGE_CAPTURE, //PHOTO
    var maxResolutionHeight: Int = CameraConstants.MAX_RESOLUTION_HEIGHT,
    var maxResolutionWidth: Int = CameraConstants.MAX_RESOLUTION_WIDTH,
    var videoBitRate: Int = CameraConstants.VIDEO_BIT_RATE,
    var videoFrameRate: Int = CameraConstants.VIDEO_FRAME_RATE,
    var maxVideoTimeInSeconds: Int = CameraConstants.TIME_LIMIT_VIDEO_CAPTURE
): Parcelable{

    companion object{
        const val ARG_NAME = "configuration"
    }
}
