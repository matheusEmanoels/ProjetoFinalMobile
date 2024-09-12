package br.edu.utfpr.trabalhofinal

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler
import br.edu.utfpr.trabalhofinal.entity.Lancamento
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var spTipo : Spinner
    private lateinit var spDetalhe : Spinner
    private lateinit var etValor: EditText
    private lateinit var dpData: DatePicker
    private lateinit var banco : DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipo = findViewById(R.id.spTipo)
        spDetalhe = findViewById(R.id.spDetalhe)
        etValor = findViewById(R.id.etValor)
        dpData = findViewById(R.id.dpDataLancamento)

        banco = DatabaseHandler( this )

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

    fun btnLancarOnClick(view: View) {
        val day = dpData.dayOfMonth
        val month = dpData.month // L
        val year = dpData.year

        val selectedDate = "$day/${month + 1}/$year"

        val lancamento = Lancamento(
            0,
            spTipo.selectedItem.toString(),
            spDetalhe.selectedItem.toString(),
            etValor.text.toString().toDouble(),
            selectedDate
        )

        banco.insert(lancamento)

        Toast.makeText( this, "Lançamento feito com Sucesso!", Toast.LENGTH_LONG ).show()
    }
    fun btnVerLancamentosOnClick(view: View) {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity( intent )
    }
    fun btnSaldoOnClick(view: View) {
        var valorFormatado = ""
        val saldo  = banco.retonSaldo()
        val df = DecimalFormat("#,###.00")
        if(saldo > 0){
            valorFormatado = df.format(saldo)
        }else{
            valorFormatado = "0.00"
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Valor")

        builder.setMessage("O seu saldo atual é de: ${valorFormatado}")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}