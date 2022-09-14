package com.expostore.ui.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.expostore.MainActivity
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.chats.repeat
import com.expostore.ui.fragment.note.NoteData
import com.expostore.ui.fragment.note.NoteFragmentDirections
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias Show<T> = (T) -> Unit
typealias Load = (Boolean) -> Unit

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
        mainActivity.setVisibleBottomNavView(isBottomNavViewVisible)
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

    protected fun state(action:suspend ()-> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                action.invoke()
            }
        }
    }
   protected fun <T> showFactory(action:(T) -> Unit) :Show<T> =action

    protected fun loadFactory(action:(Boolean)->Unit):Load=action
    protected fun loaderFactory(action: () -> Unit)=action

    

    protected fun <T> handleState(state:ResponseState<T>,loader: (Boolean) -> Unit,show:Show<T>){
        when(state){
            is ResponseState.Error->handleError(state.throwable.message!!)
            is ResponseState.Loading-> loader.invoke(state.isLoading)
            is ResponseState.Success -> show.invoke(state.item)
        }
    }


    protected fun <T> handleState(state:ResponseState<T>,show:Show<T>){
        when(state){
            is ResponseState.Error->handleError(state.throwable.message!!)
            is ResponseState.Success -> show.invoke(state.item)
            else -> {}
        }
    }

    protected fun <T> handleState(state:ResponseState<T>){
        if(state is ResponseState.Error){
         handleError(state.throwable.message!!)
        }
    }

    protected fun <T> handleState(state:ResponseState<T>,loader:()->Unit,show:Show<T>){
        when(state){
            is ResponseState.Error->handleError(state.throwable.message!!)
            is ResponseState.Loading-> loader.invoke()
            is ResponseState.Success -> show.invoke(state.item)
        }
    }

    protected fun <T> handleLoadingState(state:ResponseState<T>,loader:Load){
        if(state is ResponseState.Loading){
            loader.invoke(state.isLoading)
        }
    }



    private fun handleError(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    protected suspend fun <T> StateFlow<T>.subscribe(action:  (T) -> Unit) {
        collect {
            action.invoke(it)
        }
    }

    protected suspend fun <T,A> subscribeOnStateFlows( a:StateFlow<T>, b:StateFlow<A>,action:  (T,A) -> Unit){
              a.collect { param1->
                  b.collect{ param2->
                      action.invoke(param1,param2)
                  }
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