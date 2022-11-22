package com.expostore.ui.base.fragments.create

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.ui.base.fragments.CharacteristicsFragment
import com.expostore.ui.base.fragments.Inflate
import com.expostore.ui.base.vms.BaseCreatorViewModel
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.ImagesEdit
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.general.ImagePick
import com.expostore.utils.OnClickImage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseCreatorFragment<Binding : ViewBinding,T,A,E,I>(private val inflate: Inflate<Binding>) :
    CharacteristicsFragment<Binding>(inflate),ImagesEdit,BottomSheetImage.OnImagesSelectedListener {
    val list= mutableListOf (" ")
    protected  val mAdapter: ProductCreateImageAdapter by lazy {
        ProductCreateImageAdapter(list, this) }
     abstract val item:I?
     abstract val call:CheckBox
     abstract val message:CheckBox
     abstract override val viewModel: BaseCreatorViewModel<T,A,E>
     abstract val connectionLayout:LinearLayout
     abstract val btnSave:Button
     abstract val btnDraft:Button
     abstract val btnCancel:Button
     abstract val rvImages:RecyclerView
     override var isBottomNavViewVisible: Boolean= false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            subscribe(images){ state -> handleState(state){ handleImages(it) } }
            subscribe(new){ state -> handleState(state){handleNewItem(it)} }
            subscribe(public){state -> handleState(state){handlePublic(it)}}
            subscribe(enabled){
                btnSave.isEnabled=it
                btnDraft.isEnabled=it
            }
        }
        setupImageAdapter()
        checkListenersInstall()
        setTextChangeListeners()
    }

    override fun onStart() {
        super.onStart()
        item?.let { loadSaveInformation(it) }
    }

    override fun onResume() {
        super.onResume()
        connectionLayout.selectListener {
            message.isVisible=it
            call.isVisible=it
        }
        btnSave.click {
            viewModel.run {
                disabledButton()
                updateStatus("my")
                check()
            }
        }
        btnDraft.click {
            viewModel.run {
                disabledButton()
                updateStatus("draft")
                check()
            }
        }
        btnCancel.click { viewModel.navigateToBack() }

    }
    fun saveItem(id:String)=viewModel.saveItem(id)
     private fun handleImages(images:SaveImageResponseData)=viewModel.handleLoadImages(images.id)
     abstract fun loadSaveInformation(item: I)
     abstract fun setTextChangeListeners()
     abstract fun handleNewItem(model: T)
     abstract fun handlePublic(model: A)
     private fun checkListenersInstall(){
        call.setOnCheckedChangeListener { _, b ->
            message.isChecked=!b
            call.isEnabled=!b
            val type=if(b)"chatting" else{"call_and_chatting"}
            viewModel.saveConnectionType(type)
        }
        message.setOnCheckedChangeListener { _, b ->
            call.isChecked=!b
            message.isEnabled=!b
            val type=if(b)"chatting" else{"call_and_chatting"}
            viewModel.saveConnectionType(type)
        }
    }
   private fun setupImageAdapter(){
       rvImages.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
       rvImages.adapter=mAdapter
   }
    private fun addPhoto() {
        com.expostore.ui.fragment.chats.general.ImagePicker().bottomSheetImageSetting().build().show(childFragmentManager,"ff")
    }
    private var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    viewModel.addPhoto(fileUri,requireContext())
                    CropImage.activity(fileUri).start(requireContext(),this)
                  mAdapter.update(fileUri.toString())
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Вы вышли", Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun openGallery() {
        addPhoto()
    }

    override fun onImagesSelected(uris: MutableList<Uri>, tag: String?) {
        viewModel.addImages(uris,requireContext())
        mAdapter.addImages( uris.map { it.toString() })
    }
    override fun removePhoto(string: String) {
        viewModel.removeImage(string)
    }

    override fun cropImage(uri: Uri) {
        CropImage.activity(uri).start(requireContext(),this)
    }
}