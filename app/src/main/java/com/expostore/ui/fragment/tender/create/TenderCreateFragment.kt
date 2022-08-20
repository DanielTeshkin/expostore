package com.expostore.ui.fragment.tender.create

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.expostore.databinding.TenderCreateFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenderCreateFragment :
    BaseFragment<TenderCreateFragmentBinding>(TenderCreateFragmentBinding::inflate),CharacteristicState {
    private  val tenderCreateViewModel :TenderCreateViewModel by viewModels()
    private  val mAdapter: TenderCreateImageRecyclerViewAdapter by lazy { TenderCreateImageRecyclerViewAdapter(list) }
    private val list= mutableListOf("")
    private val selectList = mutableListOf<String>()
    private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(requireContext(),this,"other")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         makeStartRequest()
         observerUIStates()
        setFragmentListeners()
    }

    override fun onStart() {
        super.onStart()
        initUI()
    }
    private  fun setFragmentListeners(){
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<TenderModel>("tender")
            if (result != null) {
                tenderCreateViewModel.saveInfo(result)
                showEditInformation(result)
            }
        }
    }

    private fun initUI(){
        mAdapter.onClick = addPhoto()
        binding.apply {
            rvTenderImages.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mAdapter
            }
            btnSave.click {
                val connect= if(message.isChecked) "chatting"
                else "call_and_chatting"
                tenderCreateViewModel.createTender(
                    name = etTenderName.text.toString(),
                    description = etTenderDescription.text.toString(),
                    count = etTenderCount.text.toString(),
                    location = etTenderLocation.text.toString(),
                    price = etTenderPrice.text.toString(),
                    contact = connect,
                    context = requireContext(),
                    status = null

                )
            }
            btnSaveDraft.click {
                val connect= if(message.isChecked) "chatting"
                else "call_and_chatting"
                tenderCreateViewModel.createTender(
                    name = etTenderName.text.toString(),
                    description = etTenderDescription.text.toString(),
                    count = etTenderCount.text.toString(),
                    location = etTenderLocation.text.toString(),
                    price = etTenderPrice.text.toString(),
                    contact = connect,
                    context = requireContext(),
                    status = "draft"
                )
            }
            btnCancel.click { tenderCreateViewModel.navigateToTendersList() }
        }
    }

    private fun makeStartRequest(){
        tenderCreateViewModel.apply {
            getMyShop()
            getCategories()
        }
    }
    private fun showEditInformation(tenderModel: TenderModel){
        binding.apply {
            list.addAll(tenderModel.images?.map { it.file }?: listOf())
            etTenderName.setText(tenderModel.title)
            etTenderCount.setText(tenderModel.count.toString())
            etTenderPrice.setText(tenderModel.price)
            etTenderDescription.setText(tenderModel.description)

        }
    }

    private fun observerUIStates(){
        val showCategories:Show<List<ProductCategoryModel>> = { showBottomSheetCategories(it)}
        val showCharacteristics: Show<List<CategoryCharacteristicModel>> = { showBottomSheetCharacteristics(it) }
        tenderCreateViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(categories){handleState(it,showCategories)}
            subscribe(characteristics){handleState(it,showCharacteristics)}
        }
    }

    private fun addPhoto(): TenderCreateImageRecyclerViewAdapter.OnClickListener {
        return object : TenderCreateImageRecyclerViewAdapter.OnClickListener {
            override fun addPhoto() {
                ImagePicker.with(this@TenderCreateFragment)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent { intent -> launchSomeActivity.launch(intent) }
            }

            override fun removePhoto(index: Int) = tenderCreateViewModel.deleteUri(index)
        }
    }

    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    tenderCreateViewModel.saveUri(fileUri)
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


     private fun showBottomSheetCategories(list:List<ProductCategoryModel>){
         binding.llTenderCreateCategory.click {
             val categoryChose: CategoryChose = { tenderCreateViewModel.saveCategory(it.id) }
             showBottomSpecifications(
                 context = requireContext(),
                 categoryChose = categoryChose,
                 list = list
             )
         }
     }

    private fun showBottomSheetCharacteristics(list:List<CategoryCharacteristicModel>){
        binding.apply {
            llAddTenderCharacteristic.apply {
                visibility = View.VISIBLE

                characteristicAdapter.addElement(list)
                rvTenderCharacteristic.layoutManager=LinearLayoutManager(context)
                rvTenderCharacteristic.adapter=characteristicAdapter

            }
        }
    }



    override fun inputListener(left: String, right: String?, name: String) = tenderCreateViewModel.addFilterInput(left,right?:"",name)

    override fun radioListener(id: String, name: String)= tenderCreateViewModel.addFilterRadio(id,name)

    override fun selectListener(id: String, name: String, checked: Boolean){

            when(checked){
                true-> selectList.add(id)
                false->selectList.remove(id)
            }
            tenderCreateViewModel.addFilterSelect(name,selectList)


    }

    override fun checkBoxListener(name: String, checked: Boolean)=tenderCreateViewModel.addFilterCheckbox(name,checked)
    }