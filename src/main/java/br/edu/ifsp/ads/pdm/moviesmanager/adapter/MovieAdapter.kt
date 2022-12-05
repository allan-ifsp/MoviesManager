package br.edu.ifsp.ads.pdm.moviesmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.pdm.moviesmanager.R
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

class MovieAdapter (
    context: Context,
    private val listaMovies: MutableList<Movie>
) : ArrayAdapter<Movie> (context, R.layout.tile_movie, listaMovies){
    private data class MovieTileHolder(val nomeTv: TextView,
                                       val anoLancamentoTv: TextView,
                                       val notaTv: TextView )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = listaMovies[position]
        var movieTileView = convertView
        if (movieTileView == null) {
            // Inflo uma nova célula
            movieTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_movie,
                    parent,
                    false
                )

            val movieTileHolder = MovieTileHolder(
                movieTileView.findViewById(R.id.nomeTv),
                movieTileView.findViewById(R.id.anoLancamentoTv),
                movieTileView.findViewById(R.id.notaTv)
            )
            movieTileView.tag = movieTileHolder
        }

        with(movieTileView?.tag as MovieTileHolder) {
            nomeTv.text = movie.nome
            anoLancamentoTv.text = movie.anoLancamento.toString()
            notaTv.text = movie.nota.toString()
        }

        return movieTileView

    }
}