package org.xtras.polyadapter.app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_custom_adapter.*
import org.xtras.polyadapter.app.adapters.CustomAdapter
import org.xtras.polyadapter.app.items.BackgroundCategory
import org.xtras.polyadapter.app.items.IconCategory
import org.xtras.polyadapter.app.items.LoadingItem
import org.xtras.polyadapter.app.items.TextCategory

class CustomAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_adapter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CustomAdapter(
            listOf(
                IconCategory(R.drawable.ic_category_grill, "Grilling"),
                BackgroundCategory(Color.rgb(255, 0, 0)),
                IconCategory(R.drawable.ic_category_people, "People"),
                BackgroundCategory(Color.rgb(0, 255, 0)),
                BackgroundCategory(Color.rgb(0, 0, 255)),
                IconCategory(R.drawable.ic_category_pets, "Pets"),
                TextCategory("Text only Category 1"),
                TextCategory("Text only Category 2"),
                LoadingItem
            )
        )

        recyclerView.adapter = adapter
    }
}
