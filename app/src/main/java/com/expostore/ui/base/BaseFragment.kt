package com.expostore.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Fedotov Yakov
 */
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>) :
    Fragment() {
    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    /**
     * Первый раз ли вызывается onResume
     */
    private var isFirstResume: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun navigateSafety(destination: NavDirections) =
        findNavController().currentDestination?.getAction(destination.actionId)?.let {
            findNavController().navigate(destination)
        }

    protected fun <T> subscibe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flow.collect(action)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isFirstResume){
            onRestart()
        }
        isFirstResume = false
    }

    /**
     * Метод вызывающийся при перезапуске фрагмента
     */
    open fun onRestart() {
        /* no-op */
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}