package com.expostore.ui.fragment.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.expostore.MessagingWorker
import com.expostore.NoteWorker
import com.expostore.R
import com.expostore.databinding.NoteFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_personal_product_fragment.*
import java.util.concurrent.Executor

@AndroidEntryPoint
class NoteFragment : BaseFragment<NoteFragmentBinding>(NoteFragmentBinding::inflate) {
    private val viewModel:NoteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id=NoteFragmentArgs.fromBundle(requireArguments()).id
        val text=NoteFragmentArgs.fromBundle(requireArguments()).text
        if(text!=null) binding.myNote.setText(text)
        val flag=NoteFragmentArgs.fromBundle(requireArguments()).flag
        val isLiked=NoteFragmentArgs.fromBundle(requireArguments()).isLiked
        val flagNavigation=NoteFragmentArgs.fromBundle(requireArguments()).flagNavigation
        viewModel.saveData(NoteData(id,flag,isLiked,flagNavigation))
        viewModel.apply {
            start(findNavController())
            subscribe(navigation){navigateSafety(it)}
        }
        binding.delete.click {
            binding.myNote.text?.clear()
        }
        binding.btnSaveNote.click{
            val data= workDataOf(Pair ("data",
                NoteData(id,flag,isLiked,flagNavigation)),
                Pair("text",binding.myNote.text.toString())
            )
            val myWorkRequest =  OneTimeWorkRequest.Builder(NoteWorker::class.java).addTag("note").setInputData(data).build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
            WorkManager.getInstance(requireContext())
                .getWorkInfosByTagLiveData("note")
                .observe(viewLifecycleOwner) {
                    if(it.size>0){
                    val info=it[0].state
                    if (info.isFinished)
                        viewModel.navigate()
                }
            }

        }

    }
}