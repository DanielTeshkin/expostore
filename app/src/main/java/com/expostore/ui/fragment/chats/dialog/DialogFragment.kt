package com.expostore.ui.fragment.chats.dialog

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.expostore.data.remote.api.pojo.getchats.*
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.controllers.DialogControllerUI
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
 class DialogFragment : BaseFragment<DialogFragmentBinding>(DialogFragmentBinding::inflate),BottomSheetImage.OnImagesSelectedListener{
    private val viewModel: DialogViewModel by viewModels()
    private val controller by lazy { DialogControllerUI(requireContext(),binding, arguments?.getString("author")!!,
            arguments?.getString("id")!!,
           requireActivity().supportFragmentManager)

    }
    override var isBottomNavViewVisible: Boolean=false
    private val sendText:((String,MessageRequest)->Unit) by lazy {{id,body->viewModel.sentMessageOrUpdate(id,body)}  }
    private val saveFiles:((List<SaveFileRequestData>) -> Unit) by lazy {{data-> viewModel.saveFile(data)} }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateMessages( arguments?.getString("id")!!)
        subscribeViewModel()
    }

    override fun onStart() {
        super.onStart()
        controller.apply {
            initUI(sendText, {viewModel.saveImageNetwork(it)},childFragmentManager,saveFiles)
            setupVisibleControllerForTextInput { viewModel.saveMessageText(it) }
        }
        subscribe( PagerChatRepository.getInstance().getUriFiles()) {
            if (it.isNotEmpty())controller.filesRv(it as MutableList<Uri>)
        }


    }


    private fun subscribeViewModel() {
        viewModel.apply {
            subscribe(item) { handleState(it,loaderFactory { controller.progressBarState() },
                showFactory {  chat-> chat.messages?.let { messages -> controller.loadOrUpdate(messages) } })
            }
            subscribe(message) { handleState(it){controller.clearInput()} }
            subscribe(save){ handleState(it,loadFactory { controller.loadingImage()},
                showFactory
            { controller.sendImages { id-> viewModel.sendImages(id,it.id)}
            })
            }

            subscribe(saveFile) {handleState(it, loaderFactory { controller.visiblePanelFiles(false) },
                showFactory { controller.clearMultimedia()
                    viewModel.sendFiles(arguments?.getString("id")!!,it.files) })} }
        //subscribe(PagerChatRepository.getInstance().getUriFiles()){controller.filesRv(it as MutableList<Uri>)}
    }
    override fun onImagesSelected(uris: MutableList<Uri>, tag: String?) {

        controller.imagesRv(uris,tag)
    }
}









