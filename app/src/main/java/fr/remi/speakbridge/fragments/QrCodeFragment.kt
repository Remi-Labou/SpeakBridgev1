package fr.Remi.speakbridge.fragments

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import fr.Remi.speakbridge.R

import java.util.*

class QRCodeFragment : Fragment() {

    private lateinit var qrIV: ImageView
    private lateinit var generateQRBtn: Button
    private lateinit var bitmap: Bitmap
    private lateinit var qrEncoder: QRGEncoder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qrcode, container, false)

        qrIV = view.findViewById(R.id.idIVQrcode)
        generateQRBtn = view.findViewById(R.id.idBtnGenerateQR)

        generateQRBtn.setOnClickListener {
            val windowManager: WindowManager = requireActivity().getSystemService(WindowManager::class.java)
            val display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            val width = point.x
            val height = point.y
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            val randomValue = UUID.randomUUID().toString() // Generate a random value
            qrEncoder = QRGEncoder(randomValue, null, QRGContents.Type.TEXT, dimen)

            try {
                bitmap = qrEncoder.bitmap
                qrIV.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }
}
