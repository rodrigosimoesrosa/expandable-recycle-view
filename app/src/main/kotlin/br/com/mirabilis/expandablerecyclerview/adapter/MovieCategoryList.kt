package br.com.mirabilis.expandablerecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mirabilis.expandablerecyclerview.R
import br.com.mirabilis.expandablerecyclerview.entity.Movie
import br.com.mirabilis.expandablerecyclerview.entity.MovieCategory

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_category_item.*
import kotlinx.android.synthetic.main.movie_item.*

/**
 * Created by rodrigosimoesrosa on 08/04/19.
 * Copyright © 2019 Mirabilis. All rights reserved.
 */
object MovieCategoryList {

    interface OpenMovieDetailListener {
        fun onClicked(movie: Movie)
    }

    class Adapter(list: List<MovieCategory>, private val openMovieDetailListener: OpenMovieDetailListener) :
        ExpandableRecyclerViewAdapter<MovieCategoryViewHolder, MovieViewHolder>(list) {

        override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.movie_item, parent, false)

            return MovieViewHolder(view, openMovieDetailListener)
        }

        init { MovieCellState.start() }

        override fun onCreateGroupViewHolder(parent: ViewGroup?,
                                             viewType: Int): MovieCategoryViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.movie_category_item, parent, false)
            return MovieCategoryViewHolder(view)
        }



        override fun onBindChildViewHolder(holder: MovieViewHolder?,
                                           flatPosition: Int,
                                           group: ExpandableGroup<*>?, childIndex: Int) {

            val movieCategory: MovieCategory = group as MovieCategory

            val movie = movieCategory.movies[childIndex]
            val parentIndex = groups.indexOf(movieCategory)

            holder?.bind(movie, parentIndex)
        }

        override fun onBindGroupViewHolder(holder: MovieCategoryViewHolder?,
                                           flatPosition: Int,
                                           group: ExpandableGroup<*>?) {
            val movieCategory: MovieCategory = group as MovieCategory
            holder?.bind(movieCategory)
        }
    }

    class MovieCellState {

        companion object {
            private var instance: MovieCellState? = null

            fun clear() {
                instance = null
            }

            fun start() {
                instance = MovieCellState()
            }

            fun isExpanded(department: String?): Boolean {
                return instance?.movieCellsState?.get(department) ?: false
            }

            fun setExpanded(department: String?, expanded: Boolean) {
                if (department == null) {
                    return
                }

                instance?.movieCellsState?.put(department, expanded)
            }
        }

        private val movieCellsState = hashMapOf<String, Boolean>()
    }

    class MovieCategoryViewHolder(itemView: View) : GroupViewHolder(itemView), LayoutContainer {

        override val containerView: View? = itemView
        private var movieCategory: MovieCategory? = null

        fun bind(movieCategory: MovieCategory) {
            this.movieCategory = movieCategory

            lblMovieCategory.text = movieCategory.name

            applyState()
        }

        private fun applyState() {
            val expanded = MovieCellState.isExpanded(movieCategory?.title)
            if (expanded) {
                btnExpand.visibility = View.GONE
                btnCollapse.visibility = View.VISIBLE
                return
            }

            btnCollapse.visibility = View.GONE
            btnExpand.visibility = View.VISIBLE
        }

        override fun collapse() {
            MovieCellState.setExpanded(movieCategory?.title, false)

            btnCollapse.visibility = View.GONE
            btnExpand.visibility = View.VISIBLE
            super.collapse()
        }

        override fun expand() {
            MovieCellState.setExpanded(movieCategory?.title, true)

            btnExpand.visibility = View.GONE
            btnCollapse.visibility = View.VISIBLE
            super.expand()
        }
    }

    class MovieViewHolder(itemView: View, private val openMovieDetailListener: OpenMovieDetailListener) :
        ChildViewHolder(itemView), LayoutContainer {

        override val containerView: View? = itemView

        private var movie: Movie? = null
        private var parentIndex: Int = 0
        private var animationInProgress = false

        private fun showMovieDetail() {
            if (movie == null) {
                return
            }

            openMovieDetailListener.onClicked(movie as Movie)
        }

        fun bind(movie: Movie, parentIndex: Int) {
            if (animationInProgress) {
                return
            }

            this.movie = movie
            this.parentIndex = parentIndex

            lblMovie.text = movie.name
            loadImage(movie.resource)
        }

        private fun loadImage(resource: Int?) {
            if (resource == null) {
                return
            }

            imgMovie.setImageResource(resource)
        }
    }
}
