package br.com.mirabilis.expandablerecyclerview.entity

import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.parcel.Parcelize

/**
 * Created by rodrigosimoesrosa on 08/04/19.
 * Copyright © 2018 Mirabilis. All rights reserved.
 */
@Parcelize
data class MovieCategory(val name: String? = null,
                         val movies: List<Movie>) : ExpandableGroup<Movie>(name, movies), Parcelable
