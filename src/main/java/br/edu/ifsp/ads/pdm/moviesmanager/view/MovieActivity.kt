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

    private fun getPosition(string: String): Int {
        Log.d("StrPAcharPos", string)
        var n = -1
        if (string == "Romance") n = 0
        if (string == "Aventura") n = 1
        if (string == "Terror") n = 2
        if (string == "Ação") n = 3
        if (string == "Drama") n = 4
        if (string == "Comédia") n = 5
        if (string == "Ficção Científica") n = 6
        if (string == "Animação") n = 7
        return n
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
                    notaEt.setText(nota.toString())
                    generoSp.setSelection(3)
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

        fun convertToFloat(num: String): kotlin.Float {
            try {
                num as kotlin.Float
                return num
            }catch (e: java.lang.Error){
                return 0.0F
            }
        }

        amovb.saveBt.setOnClickListener {

            val movie = Movie(
                nome = amovb.nomeEt.text.toString(),
                anoLancamento = amovb.anoLancamentoEt.text.toString(),
                estudioProdutora = amovb.estudioProdutoraEt.text.toString(),
                tempoDuracao = amovb.tempoDuracaoEt.text.toString(),
                flag = amovb.flagCb.isChecked.toString(),
                nota = (amovb.notaEt.text.toString()).toDoubleOrNull(),
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