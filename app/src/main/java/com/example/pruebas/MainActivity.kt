package com.example.pruebas

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste de márgenes para la compatibilidad con gestos en pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos del layout
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val rbRadio = findViewById<RadioButton>(R.id.rb_radio)
        val rbDiametro = findViewById<RadioButton>(R.id.rb_diametro)
        val rbCircunferencia = findViewById<RadioButton>(R.id.rb_circunferencia)

        val etRadio = findViewById<EditText>(R.id.et_radio)
        val etDiametro = findViewById<EditText>(R.id.et_diametro)
        val etCircunferencia = findViewById<EditText>(R.id.et_circunferencia)

        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val tvResultado = findViewById<TextView>(R.id.tv_resultado)

        // Ocultar todos los EditText al inicio
        etRadio.visibility = View.GONE
        etDiametro.visibility = View.GONE
        etCircunferencia.visibility = View.GONE

        // Manejo del cambio de selección en los RadioButtons
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            etRadio.visibility = View.GONE
            etDiametro.visibility = View.GONE
            etCircunferencia.visibility = View.GONE

            when (checkedId) {
                R.id.rb_radio -> etRadio.visibility = View.VISIBLE
                R.id.rb_diametro -> etDiametro.visibility = View.VISIBLE
                R.id.rb_circunferencia -> etCircunferencia.visibility = View.VISIBLE
            }
        }

        // Botón de cálculo
        btnCalcular.setOnClickListener {
            var area = 0.0
            var perimetro = 0.0

            when {
                rbRadio.isChecked -> {
                    val radio = etRadio.text.toString().toDoubleOrNull()
                    if (radio != null) {
                        area = PI * radio.pow(2)
                        perimetro = 2 * PI * radio
                    } else {
                        Toast.makeText(this, "Introduce un valor válido para el radio", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                rbDiametro.isChecked -> {
                    val diametro = etDiametro.text.toString().toDoubleOrNull()
                    if (diametro != null) {
                        val radio = diametro / 2
                        area = PI * radio.pow(2)
                        perimetro = PI * diametro
                    } else {
                        Toast.makeText(this, "Introduce un valor válido para el diámetro", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                rbCircunferencia.isChecked -> {
                    val circunferencia = etCircunferencia.text.toString().toDoubleOrNull()
                    if (circunferencia != null) {
                        val radio = circunferencia / (2 * PI)
                        area = PI * radio.pow(2)
                        perimetro = circunferencia
                    } else {
                        Toast.makeText(this, "Introduce un valor válido para la circunferencia", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }

            // Mostrar resultado
            tvResultado.text = "Área: %.2f\nPerímetro: %.2f".format(area, perimetro)
        }
    }
}
