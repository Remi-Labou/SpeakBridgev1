package fr.remi.speakbridge.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.util.forEach
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.snackbar.Snackbar
import fr.Remi.speakbridge.R
import java.io.IOException

class ScanQrCodeFragment : Fragment() {

    companion object {
        private const val TAG = "ScanQrCodeFragment"
        const val QR_CODE_VALUE = "qr_code_value"
        const val REQUEST_CAMERA_PERMISSION = 1
    }

    private lateinit var cameraSurfaceView: SurfaceView
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var rootView: View

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isCameraPermissionGranted ->
            if (isCameraPermissionGranted) {
                startCamera()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied.", Toast.LENGTH_SHORT)
                    .show()

                // Show a Snackbar with a button to request the permission again
                Snackbar.make(
                    rootView,
                    "Camera permission is required.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Request") {
                        requestCameraPermission()
                    }
                    .show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan_qrcode, container, false)
        rootView = view // Assign the root view to the variable
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraSurfaceView = view.findViewById(R.id.camera_surface_view)
    }

    override fun onResume() {
        super.onResume()
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        if (ActivityCompat.checkSelfPermission(requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestCameraPermission.launch(cameraPermission)
        }
    }

    private fun startCamera() {
        initBarcodeDetector()
        initCameraSource()
        initCameraSurfaceView()
    }

    override fun onPause() {
        super.onPause()
        if (::cameraSource.isInitialized) {
            cameraSource.release()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::cameraSource.isInitialized) {
            cameraSource.release()
        }
    }



    private fun initBarcodeDetector() {
        barcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Log.d(TAG, "Camera has been released.")
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems

                if (barcodes.isNotEmpty()) {
                    barcodes.forEach { _, value ->
                        if (value.displayValue.isNotEmpty()) {
                            onQrCodeScanned(value.displayValue)
                        }
                    }
                }
            }
        })
    }

    private fun initCameraSource() {
        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()
    }


    private fun initCameraSurfaceView() {
        val surfaceView = requireView().findViewById<SurfaceView>(R.id.camera_surface_view)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource.start(surfaceView.holder)
                    } else {
                        requestCameraPermission.launch(Manifest.permission.CAMERA)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.release()
            }
        })
    }

    private fun onQrCodeScanned(qrCodeValue: String) {
        val intentResult = Intent()
        intentResult.putExtra(QR_CODE_VALUE, qrCodeValue)
        activity?.setResult(Activity.RESULT_OK, intentResult)
        activity?.finish()
    }
}
