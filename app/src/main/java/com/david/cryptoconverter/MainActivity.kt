package com.david.cryptoconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View

class MainActivity : AppCompatActivity() {
    val listaCyptos = arrayListOf(R.id.btc_boton,R.id.etherium_btn, R.id.tether_btn ,R.id.xrp_btn)
    val cryptoSeleccionada: Int? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.portraitLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val monedaSeleccionada = 0


    }


    public fun btcSelect(view: View) {

        toastCrypto("Bitcoin")
    }


    public fun etheriumSelect(view: View) {

        toastCrypto("Etherium")
    }

    public fun tetherSelect(view: View) {

        toastCrypto("Tether")
    }

    public fun xrpSelect(view: View) {

        toastCrypto("Xrp")
    }







    //Mensaje selección moneda
    public fun toastCrypto(moneda:String){
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, moneda, duration) // in Activity
        toast.show()
    }
}