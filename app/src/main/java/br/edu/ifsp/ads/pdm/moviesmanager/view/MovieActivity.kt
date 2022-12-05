package br.edu.ifsp.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMoviesBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Integrante
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

class MovieActivity: AppCompatActivity() {
    private val amovb: ActivityMoviesBinding by lazy {
        ActivityMoviesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amovb.root)

        val movieRecebido = intent.getParcelableExtra<Movie>(Constant.EXTRA_MOVIE)
        movieRecebido?.let{ _movieRecebido ->
            with(amovb) {
                with(_movieRecebido) {
                    nomeEt.setText(nome)
                    valorPagoEt.setText(valorPago)
                    comprasEt.setText(compras)
                }
            }
        }
        val viewIntegrante = intent.getBooleanExtra(Constant.VIEW_INTEGRANTE, false)
        if (viewIntegrante) {
            amovb.nomeEt.isEnabled = false
            amovb.valorPagoEt.isEnabled = false
            amovb.comprasEt.isEnabled = false
            amovb.saveBt.visibility = View.GONE
        }

        amovb.saveBt.setOnClickListener {
            val integrante = Integrante(
                id = movieRecebido?.id,
                nome = amovb.nomeEt.text.toString(),
                valorPago = amovb.valorPagoEt.text.toString(),
                compras = amovb.comprasEt.text.toString(),
                saldo = "0"
            )
            val resultIntent = Intent()
            resultIntent.putExtra(Constant.EXTRA_INTEGRANTE, integrante)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}