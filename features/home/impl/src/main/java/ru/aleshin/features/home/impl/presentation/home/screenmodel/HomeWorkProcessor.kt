/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */

package ru.aleshin.features.home.impl.presentation.home.screenmodel

import ru.aleshin.core.common.functional.Either
import ru.aleshin.core.common.functional.audio.AudioInfoUi
import ru.aleshin.core.common.functional.audio.AudioPlayListUi
import ru.aleshin.core.common.platform.screenmodel.work.ActionResult
import ru.aleshin.core.common.platform.screenmodel.work.EffectResult
import ru.aleshin.core.common.platform.screenmodel.work.WorkCommand
import ru.aleshin.core.common.platform.screenmodel.work.WorkProcessor
import ru.aleshin.core.common.platform.screenmodel.work.WorkResult
import ru.aleshin.features.home.api.di.HomeScreens
import ru.aleshin.core.common.functional.audio.AudioPlayListType
import ru.aleshin.core.common.functional.video.VideoInfoUi
import ru.aleshin.features.home.impl.domain.interactors.AudioInteractor
import ru.aleshin.features.home.impl.domain.interactors.VideosInteractor
import ru.aleshin.features.home.impl.navigation.HomeNavigationManager
import ru.aleshin.features.home.impl.presentation.home.contract.HomeAction
import ru.aleshin.features.home.impl.presentation.home.contract.HomeEffect
import ru.aleshin.features.home.impl.presentation.mappers.mapToUi
import ru.aleshin.features.home.impl.presentation.models.MediaSearchResponse
import ru.aleshin.features.player.api.navigation.PlayerScreens
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 11.07.2023.
 */
internal interface HomeWorkProcessor : WorkProcessor<HomeWorkCommand, HomeAction, HomeEffect> {

    class Base @Inject constructor(
        private val audioInteractor: AudioInteractor,
        private val videosInteractor: VideosInteractor,
        private val navigationManager: HomeNavigationManager,
    ) : HomeWorkProcessor {

        override suspend fun work(command: HomeWorkCommand) = when (command) {
            is HomeWorkCommand.LoadMedia -> loadMediaWork()
            is HomeWorkCommand.SearchRequest -> searchRequestWork(command.playLists, command.videos, command.request)
            is HomeWorkCommand.OpenDetails -> openDetailsWork(command.type, command.playlists)
            is HomeWorkCommand.OpenAudio -> openAudioWork(command.audio, command.type)
            is HomeWorkCommand.OpenVideo -> openVideoWork(command.video)
        }

        private suspend fun loadMediaWork(): WorkResult<HomeAction, HomeEffect> {
            val videos = videosInteractor.fetchPlaylists().let { videoEither ->
                when (videoEither) {
                    is Either.Right -> videoEither.data.map { it.mapToUi() }
                    is Either.Left -> return EffectResult(HomeEffect.ShowError(videoEither.data))
                }
            }
            val playLists = audioInteractor.fetchPlaylists().let { audioEither ->
                when (audioEither) {
                    is Either.Right -> audioEither.data.map { it.mapToUi() }
                    is Either.Left -> return EffectResult(HomeEffect.ShowError(audioEither.data))
                }
            }
            return ActionResult(HomeAction.UpdateMedia(playLists, videos))
        }

        private fun searchRequestWork(
            playLists: List<AudioPlayListUi>,
            videos: List<VideoInfoUi>,
            request: String
        ): WorkResult<HomeAction, HomeEffect> {
            val filteredPlaylists = mutableListOf<AudioPlayListUi>().apply {
                playLists.forEach { playListModel ->
                    val tracks = playListModel.audioList.filter { track ->
                        track.title.contains(request, ignoreCase = true)
                    }
                    add(playListModel.copy(audioList = tracks))
                }
            }
            val filteredVideos = videos.filter { it.title.contains(request, ignoreCase = true) }
            val response = MediaSearchResponse(filteredPlaylists, filteredVideos)
            return ActionResult(HomeAction.UpdateResponse(searchResponse = response))
        }

        private suspend fun openDetailsWork(
            type: AudioPlayListType,
            playLists: List<AudioPlayListUi>
        ): WorkResult<HomeAction, HomeEffect> {
            val playlist = playLists.find { it.listType == type }
            navigationManager.navigateToLocalScreen(HomeScreens.Details(checkNotNull(playlist)))
            return ActionResult(HomeAction.Navigate)
        }

        private fun openAudioWork(
            audio: AudioInfoUi,
            playListType: AudioPlayListType,
        ): WorkResult<HomeAction, HomeEffect> {
            navigationManager.navigateToPlayer(PlayerScreens.Audio(audio, playListType))
            return ActionResult(HomeAction.Navigate)
        }

        private fun openVideoWork(video: VideoInfoUi): WorkResult<HomeAction, HomeEffect> {
            navigationManager.navigateToPlayer(PlayerScreens.Video(video))
            return ActionResult(HomeAction.Navigate)
        }
    }
}

internal sealed class HomeWorkCommand : WorkCommand {
    object LoadMedia : HomeWorkCommand()

    data class SearchRequest(
        val playLists: List<AudioPlayListUi>,
        val videos: List<VideoInfoUi>,
        val request: String
    ) : HomeWorkCommand()

    data class OpenDetails(val type: AudioPlayListType, val playlists: List<AudioPlayListUi>) : HomeWorkCommand()
    data class OpenAudio(val audio: AudioInfoUi, val type: AudioPlayListType) : HomeWorkCommand()
    data class OpenVideo(val video: VideoInfoUi) : HomeWorkCommand()
}
