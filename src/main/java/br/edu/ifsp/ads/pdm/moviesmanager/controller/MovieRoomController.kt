package br.edu.ifsp.ads.pdm.moviesmanager.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.ifsp.ads.pdm.moviesmanager.model.dao.MovieRoomDao
import br.edu.ifsp.ads.pdm.moviesmanager.model.database.MovieRoomDaoDatabase
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie
import br.edu.ifsp.ads.pdm.moviesmanager.view.MainActivity

class MovieRoomController(private val mainActivity: MainActivity) {
    private val movieDaoImpl: MovieRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            MovieRoomDaoDatabase::class.java,
            MovieRoomDao.MOVIE_DATABASE_FILE
        ).build().getMovieRoomDao()
    }

    fun inserirMovie(movie: Movie) {
        Thread {
            movieDaoImpl.criarMovie(movie)
            getMoviesPorNome()
        }.start()
    }

    fun getMovie(id: Int) = movieDaoImpl.receberMovie(id)

    fun getMoviesPorNome() {
        object: AsyncTask<Unit, Unit, MutableList<Movie>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Movie> {
                val returnList = mutableListOf<Movie>()
                returnList.addAll(movieDaoImpl.receberMoviesPorNome())
                return returnList
            }

            override fun onPostExecute(result: MutableList<Movie>?) {
                super.onPostExecute(result)
                if (result != null){
                    mainActivity.atualizarListaIntegrantes(result)
                }
            }
        }.execute()
    }

    fun getMoviesPorNota() {
        object: AsyncTask<Unit, Unit, MutableList<Movie>>(){
            override fun doInBackground(vararg params: Unit?): MutableList<Movie> {
                val returnList = mutableListOf<Movie>()
                returnList.addAll(movieDaoImpl.receberMoviesPorNota())
                return returnList
            }

            override fun onPostExecute(result: MutableList<Movie>?) {
                super.onPostExecute(result)
                if (result != null){
                    mainActivity.atualizarListaIntegrantes(result)
                }
            }
        }.execute()
    }

    fun editarMovie(movie: Movie) {
        Thread {
            movieDaoImpl.atualizarMovie(movie)
            getMoviesPorNome()
        }.start()
    }

    fun removerMovie(movie: Movie) {
        Thread {
            movieDaoImpl.deletarMovie(movie)
            getMoviesPorNome()
        }.start()
    }
}