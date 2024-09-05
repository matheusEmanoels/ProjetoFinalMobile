
package br.edu.utfpr.trabalhofinal


import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.trabalhofinal.adapter.ElementoListaAdapter
import br.edu.utfpr.trabalhofinal.database.DatabaseHandler


class ListarActivity : AppCompatActivity() {

    private lateinit var lvRegistro : ListView

    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        lvRegistro = findViewById( R.id.lvRegistros )

        banco = DatabaseHandler( this )


    }

    override fun onStart() {
        super.onStart()
        val registros = banco.listCursor()
        val adapter = ElementoListaAdapter( this, registros )
        lvRegistro.adapter = adapter
    }

}
