@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)

package com.eeema.android.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eeema.android.data.datasource.local.LocalDB
import com.eeema.android.data.fake.charactersPageOne
import com.eeema.android.data.fake.charactersPageTwo
import com.eeema.android.data.model.Character
import com.eeema.android.data.server.MockDispatcher
import com.eeema.android.data.server.MockWebServerRule
import com.eeema.android.data.server.ResponseStatus
import com.eeema.android.data.server.RetrofitRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersRemoteMediatorTest {

    @get:Rule
    val serverRule = MockWebServerRule(MockDispatcher())

    @get:Rule
    val retrofitRule = RetrofitRule(
        serverRule.url,
        RemoteApi::class.java
    )

    private lateinit var localDb: LocalDB
    private lateinit var sut: CharactersRemoteMediator

    @Before
    fun setUp() {
        localDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDB::class.java
        ).build()

        sut = CharactersRemoteMediator(localDb, retrofitRule.service)
    }

    @Test
    fun refreshLoadReturnsEndOfPaginationFalseWhenMoreDataIsPresent() = runTest {
        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.REFRESH, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isFalse()
    }

    @Test
    fun refreshLoadReturnsEndOfPaginationTrueResultWhenNoMoreDataIsPresent() = runTest {
        serverRule.setResponseStatus(ResponseStatus.CustomOk("characters_no_more_pages.json"))

        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.REFRESH, pagingState)

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isTrue()
    }

    @Test
    fun prependLoadReturnsEndOnPaginationTrueWhenNoPreviousPageAvailable() = runTest {
        val refreshingStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.REFRESH, refreshingStatus)

        val prependStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
            1,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.PREPEND, prependStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isTrue()
    }

    @Test
    fun prependLoadReturnsEndOnPaginationFalseWhenNoRemoteKeyStored() = runTest {

        val prependStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.PREPEND, prependStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isFalse()
    }

    @Test
    fun prependLoadReturnsEndOnPaginationFalseWhenPreviousPageAvailableAndMoreDataAvailable() =
        runTest {
            val refreshingStatus = PagingState<Int, Character>(
                listOf(),
                null,
                PagingConfig(3),
                0
            )

            sut.load(LoadType.REFRESH, refreshingStatus)

            val appendStatus = PagingState(
                listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
                1,
                PagingConfig(3),
                0
            )

            sut.load(LoadType.APPEND, appendStatus)

            val prependStatus = PagingState(
                listOf(PagingSource.LoadResult.Page(charactersPageTwo, 2, 3)),
                null,
                PagingConfig(3),
                0
            )

            val result = sut.load(LoadType.PREPEND, prependStatus)

            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val mediatorResult = result as RemoteMediator.MediatorResult.Success
            assertThat(mediatorResult.endOfPaginationReached).isFalse()
        }

    @Test
    fun appendReturnsEndOfPaginationFalseWhenNoRemoteKeyIsStored() = runTest {
        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isFalse()
    }

    @Test
    fun appendLoadReturnsEndOnPaginationTrueWhenNoMorePagesAvailable() = runTest {
        val refreshingStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.REFRESH, refreshingStatus)

        val appendStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
            1,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.APPEND, appendStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isTrue()
    }

    @Test
    fun appendLoadReturnsEndOnPaginationFalseWhenMorePagesAvailable() = runTest {
        val refreshingStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.REFRESH, refreshingStatus)

        val appendStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
            1,
            PagingConfig(3),
            0
        )

        val customResponse = ResponseStatus.CustomOk("characters_page_2_with_more_pages.json")
        serverRule.setResponseStatus(customResponse)

        val result = sut.load(LoadType.APPEND, appendStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        val mediatorResult = result as RemoteMediator.MediatorResult.Success
        assertThat(mediatorResult.endOfPaginationReached).isFalse()
    }

    @Test
    fun refreshReturnsErrorWhenRequestFails() = runTest {
        serverRule.setResponseStatus(ResponseStatus.ServerNotFound)

        val status = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.REFRESH, status)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }

    @Test
    fun prependReturnsErrorWhenRequestFails() = runTest {
        val refreshingStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.REFRESH, refreshingStatus)

        val appendStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
            1,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.APPEND, appendStatus)

        val prependStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageTwo, 2, 3)),
            null,
            PagingConfig(3),
            0
        )

        serverRule.setResponseStatus(ResponseStatus.ServerNotFound)

        val result = sut.load(LoadType.PREPEND, prependStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }

    @Test
    fun appendReturnsErrorWhenRequestFails() = runTest {

        val refreshingStatus = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )

        sut.load(LoadType.REFRESH, refreshingStatus)

        serverRule.setResponseStatus(ResponseStatus.ServerNotFound)

        val appendStatus = PagingState(
            listOf(PagingSource.LoadResult.Page(charactersPageOne, null, 2)),
            1,
            PagingConfig(3),
            0
        )

        val result = sut.load(LoadType.APPEND, appendStatus)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }
}
