package com.kunize.uswtimetable.ui.notice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlin.math.max

private const val API_STARTING_PAGE = 1

class NoticePagingSource(private val api: IRetrofit): PagingSource<Int, NoticeDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoticeDto> {
        return try {
            val page = params.key ?: API_STARTING_PAGE
            val response = api.getNoticeList(page).data
            LoadResult.Page(
                data = response,
                prevKey = if (page == API_STARTING_PAGE) null else ensureValidKey(page - 1),
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NoticeDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun ensureValidKey(key: Int) = max(API_STARTING_PAGE, key)
}