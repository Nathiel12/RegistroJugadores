package edu.ucne.registrojugadores.Presentation.Logros.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase.DeleteLogroUseCase
import edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase.GetLogroUseCase
import edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase.UpsertLogroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLogroViewModel @Inject constructor(
    private val getLogroUseCase: GetLogroUseCase,
    private val upsertLogroUseCase: UpsertLogroUseCase,
    private val deleteLogroUseCase: DeleteLogroUseCase,
    private val logroRepository: LogroRepository
) : ViewModel(){
    private val _state = MutableStateFlow(EditLogroUiState())
    val state: StateFlow<EditLogroUiState> = _state.asStateFlow()

    fun onEvent(event: EditLogroUiEvent){
        when(event){
            is EditLogroUiEvent.Load -> onLoad(event.id)
            is EditLogroUiEvent.Save -> onSave()
            is EditLogroUiEvent.Delete -> onDelete()
            is EditLogroUiEvent.NombreChanged -> onNombreChanged(event.value)
            is EditLogroUiEvent.DescripcionChanged -> onDescripcionChanged(event.value)
            is EditLogroUiEvent.esLogradoChanged -> onLogradoChanged(event.value)
        }
    }

    private fun onLoad(id: Int?){
        if(id == null || id == 0){
            _state.update { it.copy(isNew = true, logroId = null) }
            return
        }
        viewModelScope.launch{
            val logro = getLogroUseCase(id)
            if(logro != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        logroId = logro.logroId,
                        nombre = logro.nombre,
                        descripcion = logro.descripcion,
                        esLogrado = logro.esLogrado
                    )
                }
            }
        }
    }

    private fun onNombreChanged(nombre: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    nombre = nombre,
                    nombreError = if(nombre.isBlank()) "Nombre requerido" else null
                )
            }
        }
    }

    private fun onDescripcionChanged(descripcion: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    descripcion = descripcion,
                    descripcionError = if(descripcion.isBlank()) "Descripcion requerida" else null
                )
            }
        }
    }

    private fun onLogradoChanged(logrado: Boolean){
        _state.update { it.copy(esLogrado = logrado) }
    }

    private fun onSave(){
        viewModelScope.launch {
            if(_state.value.nombre.isBlank()){
                _state.update { it.copy(nombreError = "Nombre requerido") }
                return@launch
            }
            if(_state.value.descripcion.isBlank()){
                _state.update { it.copy(descripcionError = "Descripcion requerida") }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }

            try{
                val logro = Logro(
                    logroId = _state.value.logroId ?: 0,
                    nombre = _state.value.nombre,
                    descripcion = _state.value.descripcion,
                    esLogrado = _state.value.esLogrado
                )

                upsertLogroUseCase(logro)

                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }

            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombreError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }
    private fun onDelete(){
        val id = _state.value.logroId?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteLogroUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isDeleting = false,
                        nombreError = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }

}