package org.xtras.polyadapter.app

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.xtras.polyadapter.app.items.BodyItem
import org.xtras.polyadapter.app.items.TextCategory

class TestDataSourceFactory(private val coroutineScope: CoroutineScope) : DataSource.Factory<String, BodyItem>() {
    override fun create(): DataSource<String, BodyItem> {
        return TestDataSource(coroutineScope)
    }
}

class TestDataSource(private val coroutineScope: CoroutineScope) :
    PageKeyedDataSource<String, BodyItem>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, BodyItem>
    ) {
        coroutineScope.launch {
            callback.onResult(listOf(TextCategory("First item")), 0, 2, null, "next")
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, BodyItem>) {
        coroutineScope.launch {
            delay(1000L)
            callback.onResult(listOf(TextCategory("Second item")), null)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, BodyItem>) {
    }
}
