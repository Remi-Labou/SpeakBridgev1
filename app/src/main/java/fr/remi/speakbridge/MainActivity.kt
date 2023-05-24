package fr.Remi.speakbridge

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.Remi.speakbridge.fragments.LanguageFragment
import fr.Remi.speakbridge.fragments.QRCodeFragment
import fr.remi.speakbridge.DrapeauModel
import fr.remi.speakbridge.fragments.ConversationFragment
import fr.remi.speakbridge.fragments.ScanQrCodeFragment


class MainActivity : AppCompatActivity() {
    private val flagViewModel: DrapeauModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,LanguageFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()


        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.language_page -> {
                    loadFragment(LanguageFragment(this@MainActivity))
                    return@setOnItemSelectedListener true
                }
                R.id.QRcode_page -> {
                    loadFragment(QRCodeFragment())

                    return@setOnItemSelectedListener true
                }
                R.id.Scan_page -> {
                    loadFragment(ScanQrCodeFragment())

                    return@setOnItemSelectedListener true
                }
                /*R.id.Conversation_page -> {
                    loadFragment(ConversationFragment())

                    return@setOnItemSelectedListener true
                }*/
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



}
