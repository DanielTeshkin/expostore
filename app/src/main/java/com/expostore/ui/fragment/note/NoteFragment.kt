package com.expostore.ui.fragment.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.R
import com.expostore.databinding.NoteFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : BaseFragment<NoteFragmentBinding>(NoteFragmentBinding::inflate) {
    private val viewModel:NoteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("dataNote"){_,bundle ->
            val result=bundle.getParcelable<NoteData>("note")
            viewModel.saveData(result?: NoteData())
        }
        viewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
        binding.delete.click {
            binding.myNote.text?.clear()
        }
        binding.btnSaveNote.click { viewModel.createOrUpdateNoteProduct(binding.myNote.text.toString()) }

    }

}