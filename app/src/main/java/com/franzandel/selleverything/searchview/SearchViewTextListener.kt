package com.franzandel.selleverything.searchview

import com.miguelcatalan.materialsearchview.MaterialSearchView

/**
 * Created by Franz Andel on 10/05/20.
 * Android Engineer
 */

abstract class SearchViewTextListener : MaterialSearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = false
}