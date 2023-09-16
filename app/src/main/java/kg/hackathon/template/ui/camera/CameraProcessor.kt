package kg.hackathon.template.ui.camera

import android.content.Context
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraProcessor(
    private val context: Context,
    private val previewView: PreviewView,
    private val photoCaptureCallback: PhotoCaptureCallback
) : LifecycleEventObserver {

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    init {
        initObserve()
    }

    fun initObserve() {
        photoCaptureCallback.lifecycle.addObserver(this)
    }


    fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            lensFacing = CameraSelector.LENS_FACING_BACK
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(previewView.surfaceProvider) }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                photoCaptureCallback, cameraSelector, preview, imageCapture
            )

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }


    fun captureImage() {
        imageCapture?.takePicture(
            cameraExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val byteArray = image.planes[0].buffer.toByteArray()
                    photoCaptureCallback.onPhotoCapturedSuccess(byteArray)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    photoCaptureCallback.onPhotoCapturedError(exception)
                }
            })
    }

    fun stopProcess() {
        photoCaptureCallback.lifecycle.removeObserver(this)
        cameraProvider?.unbindAll()
        cameraProvider = null
        preview = null
        camera = null

    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> stopProcess()
            else -> {}
        }
    }

}

fun ByteBuffer.toByteArray(): ByteArray {
    rewind()
    val data = ByteArray(remaining())
    get(data)
    return data
}