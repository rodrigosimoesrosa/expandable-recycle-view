package br.com.mirabilis.expandablerecyclerview.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rodrigosimoesrosa on 08/04/19.
 * Copyright © 2018 Mirabilis. All rights reserved.
 */
@Parcelize
data class Movie(val name: String? = null,
                 val resource: Int? = null) : Parcelable
