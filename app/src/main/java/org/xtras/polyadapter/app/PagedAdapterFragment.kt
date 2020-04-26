package org.xtras.polyadapter.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LivePagedListBuilder
import kotlinx.android.synthetic.main.fragment_paged_adapter.*
import org.xtras.polyadapter.app.adapterbuilders.BodyAdapterBuilder

class PagedAdapterFragment : Fragment() {

    private val testDataSourceFactory = TestDataSourceFactory(lifecycleScope)

    private val liveData = LivePagedListBuilder(
        testDataSourceFactory,
        1
    )
        .build()

    private val adapter = BodyAdapterBuilder.buildForPagedListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paged_adapter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
