package com.expostore.ui.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.expostore.MainActivity
import com.expostore.ui.state.ResponseState
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
     * Поле, хранящее значение первый раз ли вызывается onResume
     */
    private var isFirstResume: Boolean = true

    /**
     * Поле, отвечающее за видимость нижнего навигационного меню
     */
    open var isBottomNavViewVisible = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mainActivity.setVisibleBottomNavView(isBottomNavViewVisible)
    }

    protected fun navigateSafety(destination: NavDirections) =
        findNavController().currentDestination?.getAction(destination.actionId)?.let {
            findNavController().navigate(destination)
        }

    protected fun navigateToCall(number: String) {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        })
    }

    protected fun <T> subscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flow.collect(action)
                }
            }
        }
    }
    protected fun <T> singleSubscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect(action)
        }
    }



    override fun onResume() {
        super.onResume()
        if (!isFirstResume) {
            onRestart()
        }
        isFirstResume = false
    }

    /**
     * Метод вызывается при перезапуске фрагмента
     */
    open fun onRestart() {
        /* no-op */
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private val mainActivity: MainActivity
        get() = requireActivity() as MainActivity

}