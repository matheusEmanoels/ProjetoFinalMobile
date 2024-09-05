package br.edu.utfpr.trabalhofinal.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.trabalhofinal.entity.Lancamento
import java.text.DecimalFormat

class DatabaseHandler  (context : Context) : SQLiteOpenHelper(context, DATABESE_NAME,null, DATABESE_VERSION){
    companion object{
        private const val  DATABESE_NAME = "dbfile.sqlite"
        private const val  DATABESE_VERSION = 1
        private const val  TABLE_NAME = "lancamento"
        public const val  ID = 0
        public const val  TIPO = 1
        public const val  DETALHE = 2
        public const val  VALOR = 3
        public const val  DATA = 4
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tipo TEXT, detalhe TEXT, valor REAL, data TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    fun insert(lancamento: Lancamento ){
        val db = this.writableDatabase

        val registro = ContentValues()

        registro.put("tipo", lancamento.tipo)
        registro.put("detalhe", lancamento.detalhe)
        registro.put("valor", lancamento.valor)
        registro.put("data", lancamento.data)

        db.insert("lancamento", null, registro)

    }

    fun listCursor() : Cursor {
        val db = this.writableDatabase
        val registro = db.query(TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)

        return registro
    }

    fun retonSaldo() : Double{
        var retorno = 0.00
        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        while(cursor.moveToNext()){
            val tipo = cursor.getString(TIPO)
            val valor = cursor.getString(VALOR).toDouble()


            if(tipo == "Credito" )
            {
                retorno += valor
            }
            else{
                retorno -= valor
            }
        }

        return  retorno
    }
}