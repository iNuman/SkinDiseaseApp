package com.example.skindiseaseapp.ui.screens.scan_lesion

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.permission.RequestCameraPermission
import com.example.skindiseaseapp.ui.screens.bottom_sheet.CommonBottomSheet
import com.example.skindiseaseapp.ui.screens.common.CameraCaptureButton
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButtonSmall
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.screens.home.events.BottomSheetOnBoardingScreenEvent
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

    val scanLesionOnBoardingList by viewModel.bottomSheetDataState.collectAsStateWithLifecycle()

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val scope = rememberCoroutineScope()

    val controller = remember {
        activity?.applicationContext?.let { LifecycleCameraController(it) }.apply {
            this!!.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
//            this!!.setEnabledUseCases(
//                CameraController.IMAGE_CAPTURE or
//                        CameraController.VIDEO_CAPTURE
//            )
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE, lifecycleOwner = lifecycleOwner) {
        viewModel.handleBottomSheetEvent(BottomSheetOnBoardingScreenEvent.GetScanLesionOItems)
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

    Scaffold(modifier = modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CommonBottomSheet(
                list = scanLesionOnBoardingList,
                modifier = Modifier.fillMaxWidth()
            )

            CameraPreview(
                controller = controller!!,
                modifier = Modifier
                    .fillMaxSize()
            )

            ImageCropper(
                bitmap = bitmaps.lastOrNull(),
                onZoom1x = {},
                onZoom2x = {},
                onRetakePhoto1 = {},
                onRetakePhoto2 = {})

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
//                            if (hasFlash) {
//                                isTorchOn = !isTorchOn
//                                cameraControl.enableTorch(isTorchOn)
//                            }
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
                    image = Icons.Default.DateRange, // gallery
                    contentColor = Black,
                    backgroundColor = Green,
                    shape = CircleShape,
                    onClick = {
                        scope.launch {
                        }
                    },
                )
                Spacer(Modifier.size(60.sdp))
                CameraCaptureButton(
                    modifier = Modifier
                        .size(64.sdp)
                        .clip(RoundedCornerShape(50.sdp))
                        .border(
                            1.sdp,
                            Color.White,
                            shape = RoundedCornerShape(50.sdp)
                        ),
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
fun ImageCropper(
    bitmap: Bitmap? = null,
    onZoom1x: () -> Unit = {},
    onZoom2x: () -> Unit = {},
    onRetakePhoto1: () -> Unit = {},
    onRetakePhoto2: () -> Unit = {}
) {
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

        val imageWidth = constraints.maxWidth.toFloat()
        val imageHeight = constraints.maxHeight.toFloat()

        // Load the image with correct scaling mode
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,  // Make sure it's aligned with the displayed image
            painter = rememberAsyncImagePainter(
                model = bitmap
            ),
            contentDescription = stringResource(R.string.image_for_cropping)
        )

        val extractedBitmap = extractImageForOverlay(
            bitmap,
            overlayWidth,
            overlayHeight,
            imageWidth,
            imageHeight,
            minSize // 550f x 550f
        )

        val croppedImageBitmap = extractedBitmap?.asImageBitmap()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Overlay box with border
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(overlayWidthInDp, overlayHeightInDp)
                    .border(
                        border = BorderStroke(5.sdp, Color.White),
                        shape = RoundedCornerShape(12.sdp)
                    )
            ) {
                croppedImageBitmap?.let {
                    Image(
                        modifier = Modifier
                            .offset(y = 31.sdp),
                        contentScale = ContentScale.Crop,
                        bitmap = it,
                        contentDescription = stringResource(R.string.image_for_cropping)
                    )
                    Spacer(modifier = Modifier.size(12.sdp))
                }
            }

            Spacer(modifier = Modifier.size(12.sdp))

            // Zoom level buttons
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 12.sdp, start = 12.sdp, end = 12.sdp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(
                    onClick = { onZoom1x() }, // Separate lambda for Zoom level 1x
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    )
                ) {
                    CommonText(
                        text = stringResource(R.string._1x),
                        fontFamily = medium,
                        textColor = Black,
                        textSize = 12.ssp,
                    )
                }
                Spacer(modifier = Modifier.size(18.sdp))
                ElevatedButton(
                    onClick = { onZoom2x() }, // Separate lambda for Zoom level 2x
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    )
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

        // Retake photo buttons
        if (croppedImageBitmap != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.sdp, start = 12.sdp, end = 12.sdp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                ElevatedButton(
                    onClick = { onRetakePhoto1() }, // Separate lambda for first Retake button
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    )
                ) {
                    CommonText(
                        text = stringResource(R.string.retake_photo),
                        fontFamily = medium,
                        textColor = Black,
                        textSize = 12.ssp,
                    )
                }
                Spacer(modifier = Modifier.size(18.sdp))
                ElevatedButton(
                    onClick = { onRetakePhoto2() }, // Separate lambda for second Retake button
                    shape = RoundedCornerShape(32.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    )
                ) {
                    CommonText(
                        text = stringResource(R.string.retake_photo),
                        fontFamily = medium,
                        textColor = Black,
                        textSize = 12.ssp,
                    )
                }
            }
        }
    }
}

fun extractImageForOverlay(
    bitmap: Bitmap?,
    overlayWidth: Float,
    overlayHeight: Float,
    imageWidth: Float,
    imageHeight: Float,
    targetSize: Float = 550f // Fixed overlay size: 550x550
): Bitmap? {
    if (bitmap == null) return null

    // Calculate scaling factors between the displayed image and the original bitmap
    val scaleX = bitmap.width / imageWidth
    val scaleY = bitmap.height / imageHeight

    // Use the minimum scale to ensure correct aspect ratio
    val scale = minOf(scaleX, scaleY)

    // Calculate the width and height of the overlay in terms of the bitmap's dimensions
    val overlayBitmapWidth = (overlayWidth * scale).toInt()
    val overlayBitmapHeight = (overlayHeight * scale).toInt()

    // Centering the overlay, calculate the crop's top-left position in bitmap
    val cropX = ((bitmap.width / 2) - (overlayBitmapWidth / 2)).coerceAtLeast(0)
    val cropY = ((bitmap.height / 2) - (overlayBitmapHeight / 2)).coerceAtLeast(0)

    // Ensure the crop area stays within the bitmap bounds
    val safeCropX = cropX.coerceAtMost(bitmap.width - overlayBitmapWidth)
    val safeCropY = cropY.coerceAtMost(bitmap.height - overlayBitmapHeight)

    // Create the cropped bitmap using the calculated crop region
    val croppedBitmap = Bitmap.createBitmap(
        bitmap,
        safeCropX,
        safeCropY,
        overlayBitmapWidth.coerceAtMost(bitmap.width - safeCropX),
        overlayBitmapHeight.coerceAtMost(bitmap.height - safeCropY)
    )

    // Resize the cropped bitmap to exactly match the overlay target size
    return Bitmap.createScaledBitmap(
        croppedBitmap,
        targetSize.toInt(),   // Width: 550
        targetSize.toInt(),   // Height: 550
        true                  // Apply filtering for smooth resizing
    )
}
