package com.example.alcoolougasolina

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
class MainActivity : AppCompatActivity() {
    private var percentual: Double = 0.0
    private var gasPrice: Double = 0.0
    private var alcoolPrice: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            percentual = savedInstanceState.getDouble("percentual", 0.70)
        }
        setContentView(R.layout.activity_main)
        val gasPriceText: EditText = findViewById(R.id.edGasolina)
        val alcoolPriceText: EditText = findViewById(R.id.edAlcool)
        Log.d("PDM23", "No onCreate, $percentual")
        val switchButton: Switch = findViewById(R.id.swPercentual)
        switchButton.setOnCheckedChangeListener {_, is_checked ->
            percentual = if (is_checked) {
                0.75
            } else {
                0.70
            }
        }
        val btCalc: Button = findViewById(R.id.btCalcular)
        btCalc.setOnClickListener(View.OnClickListener {
            //código do evento
            val gasPriceValue = gasPriceText.text.toString().toDoubleOrNull()
            if (gasPriceValue != null) {
                gasPrice = gasPriceValue
            }
            val alcoolPriceValue = alcoolPriceText.text.toString().toDoubleOrNull()
//            Log.d("PDM23", "${alcoolPriceValue}")
            if (alcoolPriceValue != null) {
                alcoolPrice = alcoolPriceValue
            }
            if (gasPriceValue == null || alcoolPriceValue == null) {
                invalidAlert(this)
            } else {
                if (calcultePercentagePrice(gasPrice, alcoolPrice, percentual)) {
                    alcoolAlert(this, percentual)
//                    Log.d("PDM23", "O valor do álcool é no mínimo ${percentual*100}% do valor da gasolina. Prefira comprar este.")
                } else {
                    gasAlert(this, percentual)
//                    Log.d("PDM23", "Valor do álcool é menor que ${percentual*100}%. Prefira comprar gasolina.")
                }
            }
//            Log.d("PDM23", "No btCalcular, $percentual")
        })
        if (savedInstanceState != null) {
            savedInstanceState.putDouble("percentual", percentual)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("PDM23", "No onResume, $percentual")
    }

    override fun onStart() {
        super.onStart()
        Log.d("PDM23", "No onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PDM23", "No onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PDM23", "No onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PDM23", "No onResume")
    }

    fun calcultePercentagePrice(gasPrice: Double, alcoolPrice: Double, percentage: Double): Boolean {
        val result = alcoolPrice / gasPrice
        if (result <= percentage) {
            return true
        }
        return false
    }

    fun invalidAlert(context: Context) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Valor inválido")
        alert.setMessage("Digite um valor decimal válido")
        alert.setPositiveButton("OK", null)
        alert.show()
    }

    fun gasAlert(context: Context, percentage: Double) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Qual a melhor opção?")
        alert.setMessage("Pelo valor inserido, está compensando comprar a gasolina, pois o álcool está custando mais que ${percentage*100}% do valor da gasolina.")
        alert.setPositiveButton("OK", null)
        alert.show()
    }

    fun alcoolAlert(context: Context, percentage: Double) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Qual a melhor opção?")
        alert.setMessage("Pelo valor inserido, está compensando comprar o álcool, pois o álcool está custando menos que ${percentage*100}% do valor da gasolina.")
        alert.setPositiveButton("OK", null)
        alert.show()
    }
}