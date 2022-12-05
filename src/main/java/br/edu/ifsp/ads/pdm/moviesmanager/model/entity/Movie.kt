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

    @NonNull
    var anoLancamento: String,
    @NonNull
    var estudioProdutora: String,
    @NonNull
    var tempoDuracao: String,
    @NonNull
    var flag: Boolean,

    var nota: String,
    @NonNull
    var genero: String,
): Parcelable