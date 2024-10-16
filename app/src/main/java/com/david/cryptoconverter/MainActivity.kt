// MainActivity.kt
package com.david.cryptoconverter

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Lista de IDs de los botones de criptomonedas
    private val listaCryptos = listOf(R.id.btc_boton, R.id.etherium_btn, R.id.tether_btn, R.id.xrp_btn)

    var criptoSeleccionada: String = ""
    private lateinit var textView: TextView

    // Mapa para almacenar las criptomonedas creadas
    private val criptomonedasMap = mutableMapOf<String, criptomoneda>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.portraitLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById<TextView>(R.id.numeroEntrado)
    }



    // Métodos de selección de criptomonedas
    fun btcSelect(view: View) {
        val nombre = "Bitcoin"

        cambiarColorBoton(view)
        mostrarAlertaPrecio(nombre)
        criptoSeleccionada = nombre
    }

    fun etheriumSelect(view: View) {
        val nombre = "Etherium"

        cambiarColorBoton(view)
        mostrarAlertaPrecio(nombre)
        criptoSeleccionada = nombre
    }

    fun tetherSelect(view: View) {
        val nombre = "Tether"

        cambiarColorBoton(view)
        mostrarAlertaPrecio(nombre)
        criptoSeleccionada = nombre
    }

    fun xrpSelect(view: View) {
        val nombre = "XRP"

        cambiarColorBoton(view)
        mostrarAlertaPrecio(nombre)
        criptoSeleccionada = nombre
    }


    private fun cambiarColorBoton(view: View) {

        listaCryptos.forEach { id ->
            findViewById<View>(id).setBackgroundColor(Color.parseColor("#1F1F1F"))
        }
        view.setBackgroundColor(Color.parseColor("#3CFF33"))
    }




    private fun manejarSeleccionCripto(nombre: String) {
        val cripto = criptomonedasMap[nombre]
        if (cripto == null) {
           mostrarAlertaPrecio(nombre)
        } else {

            toastCrypto("$nombre")
        }
    }

    private fun toastCrypto(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }





    private fun mostrarAlertaPrecio(nombre: String) {
        val edtDada = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(nombre)
            .setMessage(R.string.introPrecio)
            .setView(edtDada)
            .setNeutralButton(R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(R.string.aceptar) { dialog, which ->

                val valorIntroducido = edtDada.text.toString().toDoubleOrNull()
                if (valorIntroducido != null) {

                    val nuevaCripto = criptomoneda(valorIntroducido, nombre)
                    criptomonedasMap[nombre] = nuevaCripto  //SE CREA EL OBJETO DENTRO DEL ARRAY
                    toastCrypto(" $nombre ha sido creado")

                } else {
                    println("No se ha introducido un valor válido.")
                }
            }
            .show()
    }



    //Funcion de cada numero
     fun clicNumero(view: View){

        val numActual: Double = textView.text.toString().toDouble()
        val botonNum = (view as Button).text.toString()

        if (numActual == 0.0){

            textView.text = botonNum

        }else{
            var tamanoTexto = textView.text.toString().length
            if (tamanoTexto < 10){

                textView.text = textView.text.toString() + botonNum

            }
        }


    }


    //Funcion a llamar cada vez que se añade un valor
    //private fun convertir(){





    //}


}
