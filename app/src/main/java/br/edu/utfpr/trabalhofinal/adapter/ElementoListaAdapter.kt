package br.edu.utfpr.trabalhofinal.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.edu.utfpr.trabalhofinal.R
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler
import br.edu.utfpr.trabalhofinal.entity.Lancamento
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler.Companion.ID
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler.Companion.TIPO
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler.Companion.DETALHE
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler.Companion.VALOR
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler.Companion.DATA
import java.text.DecimalFormat

class ElementoListaAdapter (val context : Context, val cursor : Cursor) : BaseAdapter() {
    private lateinit var banco : DatabaseHandler

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition(position)

        return Lancamento(
            cursor.getInt(ID),
            cursor.getString(TIPO),
            cursor.getString(DETALHE),
            cursor.getDouble(VALOR),
            cursor.getString(DATA)
        )

    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)

        return cursor.getInt(ID).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvTipoElementoLista = v.findViewById<TextView>(R.id.tvTipoElementoLista)
        val tvDataElementoLita = v.findViewById<TextView>(R.id.tvDataElementoLista)
        val tvDetalheElementoLita = v.findViewById<TextView>(R.id.tvDetalheElementoLista)
        val tvValorElementoLita = v.findViewById<TextView>(R.id.tvValorElementoLista)



        banco = DatabaseHandler( context )


        cursor.moveToPosition(position)
        if(cursor.getString(TIPO) == "Credito"){
            tvTipoElementoLista.setText("C")
        }else{
            tvTipoElementoLista.setText("D")
        }
        tvDataElementoLita.setText(cursor.getString(DATA))
        tvDetalheElementoLita.setText(cursor.getString(DETALHE))

        val df = DecimalFormat("#,###.00")
        println(cursor.getString(VALOR).toDouble())
        val valorformatado = df.format(cursor.getString(VALOR).toDouble())
        tvValorElementoLita.setText(valorformatado)

        return v
    }
}