package com.entexy.gardenguru.ui.fragments.support

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.support.FeedbackSubjects
import com.entexy.gardenguru.domain.usecases.support.SendFeedbackUseCase
import com.entexy.gardenguru.ui.fragments.support.adapter.FileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(private val sendFeedbackUseCase: SendFeedbackUseCase) : ViewModel() {

    private val _files = MutableLiveData<MutableList<FileModel>>(mutableListOf())
    val files = _files

    private val _filesSize = MutableStateFlow(0L)
    val filesSize = _filesSize

    fun addFile(model: FileModel) {
        _filesSize.value += model.file.length() / (1024 * 1024)
        _files.value = mutableListOf<FileModel>().apply {
            addAll(_files.value ?: emptyList())
            add(0, model)
        }
    }

    fun deleteFiles() {
        val allFiles = _files.value
        val list = _files.value?.filter { it.isSelected }
        list?.let {
            allFiles?.removeAll(it)
        }
        _files.postValue(allFiles ?: emptyList<FileModel>().toMutableList())
        val deletedFilesSize = list?.sumOf { it.file.length() / (1024 * 1024) }
        deletedFilesSize?.let { _filesSize.value -= it }
    }

    fun selectItem(position: Int, isSelected: Boolean) {
        _files.value?.let {
            it[position].apply {
                this.isSelected = isSelected
            }
        }
    }

    fun modeChanged() {
        _files.value?.forEach {
            it.isSelected = false
        }
    }

    fun sendFeedback(email: String, subject: FeedbackSubjects, body: String) =
        sendFeedbackUseCase.send(email, subject.cloudValue, body, _files.value?.map { it.file } ?: listOf())


    override fun onCleared() {
        super.onCleared()
        _files.value?.forEach {
            it.file.delete()
        }
        _files.value = mutableListOf()
    }

}
