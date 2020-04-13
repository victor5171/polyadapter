package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

sealed class Supertype
object Children1 : Supertype()
object Children2 : Supertype()
object UnregisteredChildren3 : Supertype()

object SupertypeItemGetter : ItemGetter<Supertype> {
    override fun invoke(position: Int): Supertype {
        return when (position) {
            0 -> Children1
            1 -> Children2
            else -> throw UnsupportedOperationException()
        }
    }
}

object SupertypeViewTypeRetriever : ViewTypeRetriever<Supertype> {
    override fun getViewType(value: Supertype): Int {
        if (value is Children1) return 1
        if (value is Children2) return 2

        throw UnsupportedOperationException()
    }
}

class SupertypeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent)
class SupertypeViewHolderDelegate : ViewHolderDelegate<Supertype, SupertypeViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SupertypeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SupertypeViewHolder, item: Supertype) {}
}

class Children1ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent)
class Children1ViewHolderDelegate : ViewHolderDelegate<Children1, Children1ViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup): Children1ViewHolder {
        return Children1ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: Children1ViewHolder, item: Children1) {}
}

class Children2ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent)
class Children2ViewHolderDelegate : ViewHolderDelegate<Children2, Children2ViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup): Children2ViewHolder {
        return Children2ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: Children2ViewHolder, item: Children2) {}
}
