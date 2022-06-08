package com.example.gardenguru.ui.my_plants.garden_managment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gardenguru.data.garden.models.Participant
import com.example.gardenguru.domain.app.UserEmailUseCase
import com.example.gardenguru.domain.garden.*
import com.example.gardenguru.ui.my_plants.MyPlantsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GardenManagementViewModel @Inject constructor(
    private val userEmailUseCase: UserEmailUseCase,
    private val editGardensUseCase: EditGardensUseCase,
    private val addParticipantUseCase: AddParticipantUseCase,
    private val editParticipantRoleUseCase: EditParticipantRoleUseCase,
    private val deleteParticipantUseCase: DeleteParticipantUseCase,
    private val deleteGardenUseCase: DeleteGardenUseCase
    ) : ViewModel() {

    fun getEmail() = userEmailUseCase.getEmail()

    suspend fun deleteParticipant(participantId: String): Boolean{
        return deleteParticipantUseCase.perform(participantId)
    }

    suspend fun editParticipantRole(id: String, newRole: String): Boolean {
        return editParticipantRoleUseCase.perform(id, newRole)
    }

    suspend fun addNewParticipant(email: String, gardenId: String): Boolean {
        return addParticipantUseCase.perform(email, gardenId)
    }

    suspend fun deleteGarden(gardenId: String): Boolean{
        return deleteGardenUseCase.perform(gardenId)
    }

    suspend fun editGarden(id: String, name: String, summerClimateType: String): Boolean {
        return editGardensUseCase.perform(id, name, summerClimateType)
    }
}
