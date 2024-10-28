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
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {


    private val listaCryptos = listOf(R.id.btc_boton, R.id.etherium_btn, R.id.tether_btn, R.id.xrp_btn)

    var criptoSeleccionada: String = ""
    private lateinit var textView: TextView
    private lateinit var textCrypto: TextView
    var botonSeleccionadoId: Int = -1


    private val criptomonedasMap = mutableMapOf<String, criptomoneda>()
    var hayComa = false
    var cifrasComa = 0

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
        textCrypto = findViewById<TextView>(R.id.cryptoValue)


        if (savedInstanceState != null) {
            criptoSeleccionada = savedInstanceState.getString("criptoSeleccionada", "")
            textView.text = savedInstanceState.getString("textViewText", "0")
            textCrypto.text = savedInstanceState.getString("textCryptoText", "0")
            hayComa = savedInstanceState.getBoolean("hayComa", false)
            cifrasComa = savedInstanceState.getInt("cifrasComa", 0)
            botonSeleccionadoId = savedInstanceState.getInt("botonSeleccionadoId", -1)

            val mapSerializable = savedInstanceState.getSerializable("criptomonedasMap") as? Map<String, criptomoneda>
            if (mapSerializable != null) {
                criptomonedasMap.clear()
                criptomonedasMap.putAll(mapSerializable)
            }
        }



        if (botonSeleccionadoId != -1){

            val botonSeleccionado = findViewById<View>(botonSeleccionadoId)
            botonSeleccionado.setBackgroundColor(Color.parseColor("#3CFF33"))

        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("criptoSeleccionada", criptoSeleccionada)
        outState.putString("textViewText", textView.text.toString())
        outState.putString("textCryptoText", textCrypto.text.toString())
        outState.putBoolean("hayComa", hayComa)
        outState.putInt("cifrasComa", cifrasComa)
        outState.putInt("botonSeleccionadoId",botonSeleccionadoId)

        outState.putSerializable("criptomonedasMap", HashMap(criptomonedasMap))

    }





    fun btcSelect(view: View) {
        seleccionarCripto(view, "Bitcoin")
    }

    fun etheriumSelect(view: View) {
        seleccionarCripto(view, "Etherium")
    }

    fun tetherSelect(view: View) {
        seleccionarCripto(view, "Tether")
    }

    fun xrpSelect(view: View) {
        seleccionarCripto(view, "XRP")
    }


    private fun seleccionarCripto(view: View, nombre: String) {
        cambiarColorBoton(view)

        textCrypto.text = "0"
        textView.text = "0"
        if (criptomonedasMap.containsKey(nombre)) {
            toastCrypto(nombre)
        } else {
            mostrarAlertaPrecio(nombre, true)
        }

        criptoSeleccionada = nombre
        botonSeleccionadoId = view.id
    }


    private fun cambiarColorBoton(view: View) {

        listaCryptos.forEach { id ->
            findViewById<View>(id).setBackgroundColor(Color.TRANSPARENT)
        }
        view.setBackgroundColor(Color.parseColor("#3CFF33"))
    }





    private fun toastCrypto(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }



    fun editarValor(view: View){


        if (criptoSeleccionada == ""){

            var edit = getString(R.string.edit)
            toastCrypto(edit)


        }else{

            val edtDada = EditText(this).apply {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }

            MaterialAlertDialogBuilder(this)
                .setTitle(criptoSeleccionada + ": " + getString(R.string.editTitle))
                .setMessage(R.string.introPrecio)
                .setView(edtDada)

                .setNeutralButton(R.string.cancel) { dialog, which ->
                }
                .setPositiveButton(R.string.aceptar) { dialog, which ->

                    val valorIntroducido = edtDada.text.toString().toDoubleOrNull()
                    if (valorIntroducido != null) {

                        criptomonedasMap[criptoSeleccionada]?.valor = valorIntroducido

                        val num = textView.text.toString().toDouble()
                        convertir(num)


                    }
                }.show()

        }





    }



    private fun mostrarAlertaPrecio(nombre: String, nueva: Boolean) {
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
                    if (nueva == true){
                        val nuevaCripto = criptomoneda(valorIntroducido, nombre)
                        criptomonedasMap[nombre] = nuevaCripto

                    }else{

                        criptomonedasMap[nombre]?.valor = valorIntroducido

                    }


                }
            }
            .show()
    }



    fun clicCE(view: View){

        textView.text = "0"
        textCrypto.text = "0"
        hayComa = false
        cifrasComa = 0

    }


    //Funcion de cada numero
     fun clicNumero(view: View){

        var numero = textView.text.toString()
        numero = numero.replace(",",".")
        var numActual = numero.toDouble()
        val botonNum = (view as Button).text.toString()


        if(criptoSeleccionada != ""){

            if (botonNum == ","){

                if (hayComa == false){
                    hayComa=true
                    var tamanoTexto = textView.text.toString().length
                    if (tamanoTexto < 14){

                        textView.text = textView.text.toString() + botonNum
                    }

                }

            }else{
                if (numActual == 0.0){

                    textView.text = botonNum

                }else{
                    if (cifrasComa < 2){
                        if (hayComa){
                            ++cifrasComa
                        }
                        var tamanoTexto = textView.text.toString().length
                        if (tamanoTexto < 15){

                            textView.text = textView.text.toString() + botonNum
                        }
                    }

                }
            }



            numero = textView.text.toString().replace(",",".")
            numActual = numero.toDouble()
            convertir(numActual)

        }else{
            val mensaje = getString(R.string.warnNoCrypto)
            toastCrypto(mensaje)
        }


    }


    fun clicComa(view: View){

        var botonClic = (view as Button).text.toString()
        if (hayComa == false){

            textView.text = textView.text.toString() + botonClic
            hayComa = true
        }
    }

    fun borrar(view: View){

        if(textView.text.length > 1){

            if (textView.text.last() == ','){
                hayComa = false
            }
            if (cifrasComa > 0){ cifrasComa --}
            textView.text = textView.text.dropLast(1)
        }else{
            textView.text = "0"
        }

        var valor = textView.text.toString().replace(",",".")
        val valorNum = valor.toDouble()
        convertir(valorNum)

    }




    private fun convertir(numActual: Double) {
        val valorCriptomoneda: Double? = criptomonedasMap[criptoSeleccionada]?.valor
        val resultado: Double = if (valorCriptomoneda != null) numActual / valorCriptomoneda else 0.0
        val resultadoBien: Double = BigDecimal(resultado).setScale(6, RoundingMode.HALF_UP).toDouble()


        textCrypto.text = String.format("%.6f", resultadoBien).replace(".", ",")
    }






}
