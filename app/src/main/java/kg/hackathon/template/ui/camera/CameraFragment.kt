package kg.hackathon.template.ui.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import kg.hackathon.template.databinding.FragmentCameraBinding
import kg.hackathon.template.utils.popBack
import java.io.ByteArrayOutputStream

class CameraFragment : Fragment(), PhotoCaptureCallback {

    private var _vb: FragmentCameraBinding? = null
    val vb: FragmentCameraBinding
        get() = _vb!!

    private val previewView: CameraProcessor by lazy {
        CameraProcessor(requireContext(), vb.previewView, this)
    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri == null) setUpCameraProcessor()
        else setImageFromUri(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = FragmentCameraBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCameraProcessor()
        setupView()
    }

    private fun setupView() {
        vb.ivClose.setOnClickListener { popBack() }

        vb.btnChooseFromGallery.setOnClickListener {
            getContent.launch("image/*")
        }
        vb.captureButton.setOnClickListener {
            previewView.captureImage()
        }
    }

    private fun setResult(byteArray: ByteArray) {
        val bundle = Bundle().apply { putByteArray(PHOTO, byteArray) }
        setFragmentResult(PHOTO, bundle)
        requireActivity().runOnUiThread { popBack() }
    }

    private fun setUpCameraProcessor() {
        previewView.setupCamera()
    }

    private fun setImageFromUri(uri: Uri?) {
        if (uri != null) {
            val outputStream = ByteArrayOutputStream()
            try {
                val contentResolver = requireContext().contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                val byteArray = outputStream.toByteArray()
                setResult(byteArray)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                outputStream.close()
            }
        }
    }


    override fun onPhotoCapturedSuccess(byteArray: ByteArray) {
        setResult(byteArray)
    }

    override fun onPhotoCapturedError(exception: Exception) {
        exception.printStackTrace()
    }

    override fun onDestroyView() {
        _vb = null
        super.onDestroyView()
        previewView.stopProcess()
    }


    companion object {
        const val PHOTO = "photo_result"
    }

}

interface PhotoCaptureCallback: LifecycleOwner {

    fun onPhotoCapturedSuccess(byteArray: ByteArray)
    fun onPhotoCapturedError(exception: Exception)
}