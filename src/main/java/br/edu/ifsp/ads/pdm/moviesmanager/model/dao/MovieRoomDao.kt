package br.edu.ifsp.ads.pdm.moviesmanager.model.dao

import androidx.room.*
import br.edu.ifsp.ads.pdm.moviesmanager.model.entity.Movie

@Dao
interface MovieRoomDao {
    companion object Constant {
        const val MOVIE_DATABASE_FILE = "movies_room"
        const val MOVIE_TABLE = "movie"
        const val ID_COLUMN = "nome"
        const val NOME_COLUMN = "nome"
        const val NOTA_COLUMN = "nota"
    }

    @Insert
    fun criarMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE $ID_COLUMN = :id")
    fun receberMovie(id: Int): Movie?

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY $NOME_COLUMN")
    fun receberMoviesPorNome(): MutableList<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY $NOTA_COLUMN")
    fun receberMoviesPorNota(): MutableList<Movie>

    @Update
    fun atualizarMovie(movie: Movie): Int

    @Delete
    fun deletarMovie(movie: Movie): Int
}