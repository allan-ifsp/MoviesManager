package br.edu.ifsp.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    var nome: String,

    var anoLancamento: String,

    var estudioProdutora: String,

    var tempoDuracao: String,

    var flag: String,

    var nota: String,

    var genero: String,
): Parcelable