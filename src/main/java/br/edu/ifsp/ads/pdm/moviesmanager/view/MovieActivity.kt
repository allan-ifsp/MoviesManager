package br.edu.ifsp.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

class MovieActivity: AppCompatActivity() {
    private val amovb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amovb.root)

        val movieRecebido = intent.getParcelableExtra<Movie>(Constant.EXTRA_MOVIE)
        movieRecebido?.let{ _movieRecebido ->
            with(amovb) {
                with(_movieRecebido) {
                    nomeEt.setText(nome)
                    anoLancamentoEt.setText(anoLancamento)
                    estudioProdutoraEt.setText(estudioProdutora)
                    tempoDuracaoEt.setText(tempoDuracao)
                    flagCb.isChecked = flag.toBoolean()
                    notaEt.setText(nota)
                    generoSp.setSelection(1)
//                    val adapter = ArrayAdapter(this@with, )
//                    generoSp.adapter = genero.toString()

                }
            }
        }
        val viewMovie = intent.getBooleanExtra(Constant.VIEW_MOVIE, false)
        if (viewMovie) {
            amovb.nomeEt.isEnabled = false
            amovb.anoLancamentoEt.isEnabled = false
            amovb.estudioProdutoraEt.isEnabled = false
            amovb.tempoDuracaoEt.isEnabled = false
            amovb.flagCb.isEnabled = false
            amovb.notaEt.isEnabled = false
            amovb.generoSp.isEnabled = false
            amovb.saveBt.visibility = View.GONE
        }

        amovb.saveBt.setOnClickListener {
            val movie = Movie(
                nome = amovb.nomeEt.text.toString(),
                anoLancamento = amovb.anoLancamentoEt.text.toString(),
                estudioProdutora = amovb.estudioProdutoraEt.text.toString(),
                tempoDuracao = amovb.tempoDuracaoEt.text.toString(),
                flag = amovb.flagCb.isChecked.toString(),
                nota = amovb.notaEt.text.toString(),
                genero = amovb.generoSp.toString(),

            )
            Log.d("movie", movie.toString())
            Log.d("amovb", amovb.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(Constant.EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}