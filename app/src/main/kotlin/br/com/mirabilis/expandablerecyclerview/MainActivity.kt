package br.com.mirabilis.expandablerecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import br.com.mirabilis.expandablerecyclerview.adapter.MovieCategoryList
import br.com.mirabilis.expandablerecyclerview.entity.Movie
import br.com.mirabilis.expandablerecyclerview.entity.MovieCategory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieCategoryList.OpenMovieDetailListener {

    private var adapter: MovieCategoryList.Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)

        val animator = movieCategoryRecyclerView.itemAnimator
        if (animator is DefaultItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        adapter = MovieCategoryList.Adapter(list = getMovieCategories(),
            openMovieDetailListener = this)

        movieCategoryRecyclerView.layoutManager = layoutManager
        movieCategoryRecyclerView.adapter = adapter
    }

    private fun getMovieCategories(): List<MovieCategory> {
        val movieCategories = arrayListOf<MovieCategory>()

        val horror = MovieCategory(getString(R.string.horror), listOf(
            Movie(getString(R.string.exorcist), R.mipmap.exorcist),
            Movie(getString(R.string.rosemary_baby), R.mipmap.rosemary_baby)))

        val sciFi = MovieCategory(getString(R.string.sci_fi), listOf(
            Movie(getString(R.string.a_space_odyssey_2001), R.mipmap.a_space_odyssey_2001),
            Movie(getString(R.string.interstellar), R.mipmap.interstellar),
            Movie(getString(R.string.aliens), R.mipmap.aliens)))

        movieCategories.add(horror)
        movieCategories.add(sciFi)

        return movieCategories
    }

    override fun onClicked(movie: Movie) {
        //TODO open movie details on new screen
    }
}
