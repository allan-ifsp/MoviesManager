package br.edu.ifsp.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.controller.IntegranteRoomController
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.EXTRA_INTEGRANTE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.LISTA_INTEGRANTES
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.VIEW_INTEGRANTE
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.ads.pdm.splitthebill.adapter.MovieAdapter

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val listaIntegrantes: MutableList<Movie> = mutableListOf()

    // Adapter
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    // Controller
    private val movieController: IntegranteRoomController by lazy {
        IntegranteRoomController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        movieAdapter = MovieAdapter(this, listaIntegrantes)
        amb.moviesLv.adapter = movieAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let { _movie->
                    if (_movie.nome != null) {
                        val position = listaIntegrantes.indexOfFirst { it.nome == _movie.nome }
                        if (position != -1) {
                            movieController.editarIntegrante(_movie)
                        }
                    }
                    else {
                        movieController.inserirIntegrante(_movie)
                    }
                }
            }
        }

        registerForContextMenu(amb.integrantesLv)

        amb.integrantesLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val integrante = listaIntegrantes[position]
                val integranteIntent = Intent(this@MainActivity, IntegranteActivity::class.java)
                integranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                integranteIntent.putExtra(VIEW_INTEGRANTE, true)
                startActivity(integranteIntent)
            }

        // Buscando integrantes no banco
        movieController.getIntegrantes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addIntegranteMi -> {
                carl.launch(Intent(this, IntegranteActivity::class.java))
                true
            }
            R.id.rachaMi -> {
                var total: Float = 0.0F
                for (integrante: Integrante in listaIntegrantes){
                    total += integrante.valorPago.toFloat()
                }
                for (integrante: Integrante in listaIntegrantes){
                    integrante.saldo = ((integrante.valorPago).toFloat()).minus(total.div(listaIntegrantes.count())).toString()
                }

                val rachaIntent = Intent(this, RachaActivity::class.java)
                rachaIntent.putExtra(LISTA_INTEGRANTES, ArrayList(listaIntegrantes))
                rachaIntent.putExtra("KEK", "KEKW")
                carl.launch(rachaIntent)

                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.main_menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        val integrante = listaIntegrantes[position]
        return when(item.itemId) {
            R.id.removerIntegranteMi -> {
                // Remove o integrante
                movieController.removerIntegrante(integrante)
                true
            }

            R.id.editarIntegranteMi -> {
                // Chama a tela para editar o integrante
                val integranteIntent = Intent(this, IntegranteActivity::class.java)
                integranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                integranteIntent.putExtra(VIEW_INTEGRANTE, false)
                carl.launch(integranteIntent)
                true
            }
            else -> { false }
        }
    }

    fun atualizarListaIntegrantes(_listaIntegrantes: MutableList<Integrante>?) {
        listaIntegrantes.clear()
        listaIntegrantes.addAll(_listaIntegrantes!!)
        movieAdapter.notifyDataSetChanged()
    }
}