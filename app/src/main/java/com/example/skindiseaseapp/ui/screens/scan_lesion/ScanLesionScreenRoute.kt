package com.example.skindiseaseapp.ui.screens.scan_lesion

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.permission.RequestCameraPermission
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButton
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButtonSmall
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButtonWithText
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.scan_lesion.zoom.ZoomableImage
import com.example.skindiseaseapp.ui.theme.White
import com.example.skindiseaseapp.ui.theme.medium
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.plcoding.cameraxguide.CameraPreview
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ScanLesionScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateBack: () -> Unit,
) {

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val controller = remember {
        activity?.applicationContext?.let { LifecycleCameraController(it) }.apply {
            this!!.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
//            this!!.setEnabledUseCases(
//                CameraController.IMAGE_CAPTURE or
//                        CameraController.VIDEO_CAPTURE
//            )
        }
    }

    val bitmaps by viewModel.bitmaps.collectAsState()

    if (!permissionState.status.isGranted) {
        RequestCameraPermission(
            onPermissionGranted = {
                // Show permission granted message
//                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
            },
            onPermissionDenied = {
                // Handle denial scenario
//                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        )
    }


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.sdp,
        sheetContent = {
            PhotoBottomSheetContent(
                bitmaps = bitmaps,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CameraPreview(
                controller = controller!!,
                modifier = Modifier
                    .fillMaxSize()
            )

            ImageCropper(bitmap = bitmaps.lastOrNull())

            Column {
                ElevatedButton(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(all = 12.sdp),
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                    onClick = { },
                ) {

                    CommonText(
                        modifier = Modifier.padding(3.sdp),
                        text = stringResource(R.string.instructions),
                        textSize = 12.ssp,
                        fontFamily = medium,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        textColor = White,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.sdp, start = 12.sdp, end = 12.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    CommonFloatingButtonSmall(
                        image = Icons.Filled.Close,
                        contentColor = Black,
                        backgroundColor = White,
                        shape = CircleShape,
                        onClick = {
                            navigateBack.invoke()
                        }
                    )
                    CommonFloatingButtonSmall(
                        image = Icons.Filled.Refresh,
                        contentColor = Black,
                        backgroundColor = White,
                        shape = CircleShape,
                        onClick = {
                            controller.imageCaptureFlashMode =
                                if (controller.imageCaptureFlashMode == ImageCapture.FLASH_MODE_OFF) {
                                    ImageCapture.FLASH_MODE_ON
                                } else ImageCapture.FLASH_MODE_OFF
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomStart)
                    .padding(bottom = 12.sdp, start = 12.sdp, end = 12.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(Modifier.size(8.sdp))
                CommonFloatingButtonSmall(
                    image = Icons.Default.DateRange,
                    contentColor = Black,
                    backgroundColor = Green,
                    shape = CircleShape,
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                )
                Spacer(Modifier.size(60.sdp))
                CommonFloatingButton(
                    image = Icons.Filled.Refresh,
                    contentColor = Black,
                    backgroundColor = Green,
                    shape = CircleShape,
                    onClick = {
                        takePhoto(
                            activity = activity,
                            controller = controller,
                            onPhotoTaken = viewModel::onTakePhoto
                        )
                    }
                )
            }
        }
    }
}

private fun takePhoto(
    activity: Activity?,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(activity!!.applicationContext),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                onPhotoTaken(rotatedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showBackground = true)
@Composable
fun ImageCropper(bitmap: Bitmap? = null) {
    BoxWithConstraints {
        val minSize = 550f
        var overlayWidth by remember { mutableFloatStateOf(minSize) }
        var overlayHeight by remember { mutableFloatStateOf(minSize) }

        val overlayWidthInDp: Dp
        val overlayHeightInDp: Dp

        with(LocalDensity.current) {
            overlayWidthInDp = overlayWidth.toDp()
            overlayHeightInDp = overlayHeight.toDp()
        }

        val imageWidth = constraints.maxWidth
        val imageHeight = constraints.maxHeight
        // Load the image
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = bitmap
            ),
            contentDescription = stringResource(R.string.image_for_cropping)
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

            val croppedBitmap = cropBitmap(
                bitmap,
                overlayWidth,
                overlayHeight,
                imageWidth.toFloat(),
                imageHeight.toFloat()
            )
            val croppedImageBitmap = croppedBitmap?.asImageBitmap()
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center horizontally
                    .size(overlayWidthInDp, overlayHeightInDp)
                    .border(
                        border = BorderStroke(5.sdp, Color.White),
                        shape = RoundedCornerShape(12.sdp)
                    )

            ) {
                croppedImageBitmap?.let {
                    ZoomableImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                border = BorderStroke(0.sdp, Color.White),
                                shape = RoundedCornerShape(12.sdp)
                            )
                            .padding(3.sdp),
                        imageBitmap = it,
                        contentScale = ContentScale.Crop,
                        clipTransformToContentScale = true
                    )
                }
            }

            Spacer(modifier = Modifier.size(size = 12.sdp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.sdp, start = 12.sdp, end = 12.sdp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                ElevatedButton(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = { },
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    ),
                ) {
                    CommonText(
                        text = stringResource(R.string._1x),
                        fontFamily = medium,
                        textColor = Black,
                        textSize = 12.ssp,
                    )
                }
                Spacer(modifier = Modifier.size(size = 18.sdp))
                ElevatedButton(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = { },
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    ),
                ) {
                    CommonText(
                        text = stringResource(R.string._2x),
                        fontFamily = medium,
                        textColor = Black,
                        textSize = 12.ssp,
                    )
                }

            }
        }
    }
}

// Function to crop the bitmap based on the overlay box
fun cropBitmap(
    bitmap: Bitmap?,
    overlayWidth: Float,
    overlayHeight: Float,
    imageWidth: Float,
    imageHeight: Float,
): Bitmap? {
    if (bitmap == null) return null

    // Calculate the cropping area based on overlay and image scaling
    val scaleX = bitmap.width / imageWidth
    val scaleY = bitmap.height / imageHeight

    val cropX = (imageWidth / 2 - overlayWidth / 2) * scaleX
    val cropY = (imageHeight / 2 - overlayHeight / 2) * scaleY
    val cropWidth = overlayWidth * scaleX
    val cropHeight = overlayHeight * scaleY

    return Bitmap.createBitmap(
        bitmap,
        cropX.toInt(),
        cropY.toInt(),
        cropWidth.toInt(),
        cropHeight.toInt()
    )
}