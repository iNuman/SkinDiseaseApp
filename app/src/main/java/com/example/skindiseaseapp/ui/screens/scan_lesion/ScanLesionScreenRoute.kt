package com.example.skindiseaseapp.ui.screens.scan_lesion

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skindiseaseapp.R
import com.example.skindiseaseapp.ui.permission.RequestCameraPermission
import com.example.skindiseaseapp.ui.screens.common_view_model.HomeViewModel
import com.example.skindiseaseapp.ui.theme.medium
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.skindiseaseapp.ui.screens.common.CommonFloatingButtonSmall
import com.example.skindiseaseapp.ui.screens.common.CommonText
import com.example.skindiseaseapp.ui.theme.White
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.plcoding.cameraxguide.CameraPreview
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


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
                        .padding(bottom = 12.sdp, start = 8.sdp, end = 8.sdp),
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

            ImageCroper(bitmaps)
//            IconButton(
//                onClick = {
//                    controller.cameraSelector =
//                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
//                            CameraSelector.DEFAULT_FRONT_CAMERA
//                        } else CameraSelector.DEFAULT_BACK_CAMERA
//                },
//                modifier = Modifier
//                    .offset(12.sdp, 12.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.CheckCircle, // cam switch
//                    contentDescription = "Switch camera"
//                )
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(6.sdp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // open gallery
                        contentDescription = "Open gallery"
                    )
                }
                IconButton(
                    onClick = {
                        takePhoto(
                            activity = activity,
                            controller = controller,
                            onPhotoTaken = viewModel::onTakePhoto
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle, // capture image
                        contentDescription = "Take photo"
                    )
                }
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
fun ImageCroper(bitmaps: List<Bitmap>) {
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = bitmaps.lastOrNull()
                    ?: "https://i.picsum.photos/id/216/200/200.jpg?hmac=7Weas8POu49YrmUyJ6tWdqVMx-hw6lryzl8HnHZBzjc"
            ),
            contentDescription = "Image for cropping"
        )
        val croppedBitmap = cropBitmap(
            bitmaps.lastOrNull(),
            overlayWidth,
            overlayHeight,
            imageWidth.toFloat(),
            imageHeight.toFloat()
        )
        val croppedImageBitmap = croppedBitmap?.asImageBitmap()
        // Overlay Box - This is where the cropping will be done
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .border(
                    border = BorderStroke(2.dp, Color.White),
                    shape = RoundedCornerShape(12.sdp)
                ) // Rounded border
                .size(overlayWidthInDp, overlayHeightInDp)
        ) {
            croppedImageBitmap?.let {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = it,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Cropped image"
                )
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
    imageHeight: Float
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