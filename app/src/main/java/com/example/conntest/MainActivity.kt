package com.example.conntest

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        var telephonyManager = getSystemService(Context.TELEPHONY_SERVICE)
//        if (telephonyManager is TelephonyManager) {
//            telephonyManager.carrierConfig.toString()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var text : String = ""

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        if (connectivityManager is ConnectivityManager) {

            val ns = connectivityManager.allNetworks
            for (n: Network in ns) {
                text += "N:"
                text += n
                text += "\n\n"

                // connectivityManager.activeNetwork
                val link: LinkProperties = connectivityManager.getLinkProperties(n) as LinkProperties
                text += "NETWORK: " + link.interfaceName.toString() + "\n"
                text += link.toString()
                text += "\n\n"

                val caps = connectivityManager.getNetworkCapabilities(n)
                text += "NETWORK CAPABILITIES:\n"
                text += caps.toString()
                text += "\n\n\n"
            }
        }

        // This one needs Carrier Privileges or READ_PRIVILEGED_PHONE_STATE (Android 10 or before only)
//        var mSimpleExecutor : Executor = Executors.newSingleThreadExecutor()
//        var resultFuture : CompletableFuture<NetworkSlicingConfig> = CompletableFuture<NetworkSlicingConfig>()
//        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE)
//        if (telephonyManager is TelephonyManager) {
//            telephonyManager.getNetworkSlicingConfiguration(mSimpleExecutor, resultFuture::complete)
//        }
//
//        text += "\n\n...\n\n" + resultFuture.toString()


//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_PHONE_STATE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.READ_PHONE_STATE), 1);
//            return
//        }

        val tv = findViewById<TextView>(R.id.textView1)
        tv.setPadding(50, 0, 50, 0)
        tv.setTextIsSelectable(true)
        tv.setText(text)
        tv.movementMethod = ScrollingMovementMethod.getInstance()

        tv.setOnClickListener(View.OnClickListener {
            val cm = getSystemService(CLIPBOARD_SERVICE)
            if (cm is ClipboardManager) {
                cm.setPrimaryClip(ClipData.newPlainText("Connection Data", text))
            }
        })

    }
}