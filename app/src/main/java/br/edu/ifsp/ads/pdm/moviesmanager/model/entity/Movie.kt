package br.edu.ifsp.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Movie (
    @PrimaryKey(autoGenerate = false)
    var nome: String,

    @NonNull
    var anoLancamento: Date,
    @NonNull
    var estudioProdutora: String,
    @NonNull
    var tempoDuracao: Float,
    @NonNull
    var flag: Boolean,

    var nota: Float,
    @NonNull
    var genero: Genero,
): Parcelable