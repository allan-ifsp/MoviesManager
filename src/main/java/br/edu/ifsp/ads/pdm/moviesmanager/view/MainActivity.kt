package br.edu.ifsp.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import br.edu.ifsp.ads.pdm.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.ads.pdm.moviesmanager.controller.MovieRoomController
import br.edu.ifsp.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.UPDATE_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val listaMovies: MutableList<Movie> = mutableListOf()

    // Adapter
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    // Controller
    private val movieController: MovieRoomController by lazy {
        MovieRoomController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        movieAdapter = MovieAdapter(this, listaMovies)
        amb.moviesLv.adapter = movieAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)
                Log.d("temMovie", movie.toString())
                movie?.let { _movie->
                    val position = listaMovies.indexOfFirst { it.nome == _movie.nome }
                    if (position != -1) {
                        movieController.editarMovie(_movie)
                        Log.d("editarMovie", _movie.toString())
                    }
                    else {
                        Log.d("inserirMovie", _movie.toString())
                        movieController.inserirMovie(_movie)
                    }
                }
            }
        }

        registerForContextMenu(amb.moviesLv)

        amb.moviesLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val movie = listaMovies[position]
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, true)
                startActivity(movieIntent)
            }

        // Buscando integrantes no banco
        movieController.getMoviesPorNome()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMovieMi -> {
                carl.launch(Intent(this, MovieActivity::class.java))
                Log.d("foi", "acho q foi")
                // Buscando integrantes no banco
                movieController.getMoviesPorNome()
                true
            }
            R.id.orderByNome -> {
                movieController.getMoviesPorNome()
                true
            }
            R.id.orderByNota -> {
                movieController.getMoviesPorNota()
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
        val movie = listaMovies[position]
        return when(item.itemId) {
            R.id.removerMovieMi -> {
                // Remove o integrante
                movieController.removerMovie(movie)
                true
            }

            R.id.editarMovieMi -> {
                // Chama a tela para editar o integrante
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                carl.launch(movieIntent)
                true
            }
            else -> { false }
        }
    }

    fun atualizarListaMovies(_listaMovies: MutableList<Movie>?) {
        listaMovies.clear()
        listaMovies.addAll(_listaMovies!!)
        movieAdapter.notifyDataSetChanged()
        Log.d("ListaMovies", listaMovies.toString())
    }
}