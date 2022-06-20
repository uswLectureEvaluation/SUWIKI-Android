package com.kunize.uswtimetable.repository.my_post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlin.math.max

private const val API_STARTING_PAGE = 1

class MyEvaluationPagingSource(
    val api: IRetrofit
) : PagingSource<Int, MyEvaluationDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyEvaluationDto> {
        return try {
            val page = params.key ?: API_STARTING_PAGE
            val response = api.getEvaluatePosts(page).data
            LoadResult.Page(
                data = response,
                prevKey = if (page == API_STARTING_PAGE) null else ensureValidKey(page - 1),
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MyEvaluationDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun ensureValidKey(key: Int) = max(API_STARTING_PAGE, key)
}