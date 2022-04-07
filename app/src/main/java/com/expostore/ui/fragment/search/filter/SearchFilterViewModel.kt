package com.expostore.ui.fragment.search.filter

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.search.filter.interactor.SearchFilterInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val interactor: SearchFilterInteractor
) : BaseViewModel() {
    override fun onStart() {
        /* no-op */
    }
}