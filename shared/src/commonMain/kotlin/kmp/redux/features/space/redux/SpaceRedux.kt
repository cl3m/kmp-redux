package kmp.redux.features.space.redux

import kmp.redux.features.space.entity.PeopleInSpace
import kmp.redux.features.space.repository.SpaceRepository
import kmp.redux.network.Response
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// State

data class SpaceState(
    val peopleInSpace: PeopleInSpace? = null,
    val status: Status = Status.IDLE
) {
    enum class Status {
        IDLE, PENDING
    }
}


// Actions
sealed class SpaceAction {
    object FetchPeopleInSpace : SpaceAction()
    object FetchPeopleInSpaceSlow : SpaceAction()
    internal data class FetchPeopleInSpaceSuccess(val peopleInSpace: PeopleInSpace) : SpaceAction()
    internal data class FetchPeopleInSpaceFailure(val message: String?) : SpaceAction()
}

// Reducers

private val spaceRepository: SpaceRepository = SpaceRepository()

fun spaceReducer(state: SpaceState, action: SpaceAction, dispatch: (SpaceAction) -> Unit): SpaceState {
    return when (action) {
        is SpaceAction.FetchPeopleInSpace -> {
            MainScope().launch {
                val result = when (val response = spaceRepository.getPeopleInSpace()) {
                    is Response.Success -> SpaceAction.FetchPeopleInSpaceSuccess(response.result)
                    is Response.Failure -> SpaceAction.FetchPeopleInSpaceFailure("internet_error")
                }
                dispatch(result)
            }
            return state.copy(status = SpaceState.Status.PENDING)
        }
        is SpaceAction.FetchPeopleInSpaceSlow -> {
            MainScope().launch {
                delay(10000)
                dispatch(SpaceAction.FetchPeopleInSpace)
            }
            return state.copy(status = SpaceState.Status.PENDING)
        }
        is SpaceAction.FetchPeopleInSpaceSuccess -> return state.copy(peopleInSpace = action.peopleInSpace, status = SpaceState.Status.IDLE)
        is SpaceAction.FetchPeopleInSpaceFailure ->
            state.copy(status = SpaceState.Status.IDLE)
    }
}