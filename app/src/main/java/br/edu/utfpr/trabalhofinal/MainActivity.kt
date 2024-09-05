package br.edu.utfpr.trabalhofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var spTipo : Spinner
    private lateinit var spDetalhe : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipo = findViewById(R.id.spTipo)
        spDetalhe = findViewById(R.id.spDetalhe)

        val tipos =listOf<String>( "Credito", "Debito")
        val detalheCredito =listOf<String>( "Salario", "Extra")
        val detalheDebito =listOf<String>( "Alimentação", "Transporte", "Saúde", "Moradia")
        val adTipo = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, tipos )
        val adDetalheCredito = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, detalheCredito )
        val adDetalheDebito = ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, detalheDebito )
        spTipo.adapter = adTipo

        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spTipo.selectedItem.toString() == "Credito"){
                    spDetalhe.adapter = adDetalheCredito
                }else{
                    spDetalhe.adapter = adDetalheDebito
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    fun btnLancarOnClick(view: View) {}
    fun btnVerLancamentosOnClick(view: View) {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity( intent )
    }
    fun btnSaldoOnClick(view: View) {}
}