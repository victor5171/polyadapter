# About
Polyadapter is a library with the goal to make it easier to work with Polymorphic Adapters for RecyclerViews!

It's build to be lightweight and entirely written in **Kotlin**, you don't need to create a lot of classes and a lot of boilerplates but in the meantime it's also really flexible so you can plug it into your own adapter!

It supports the basic **RecyclerView.Adapter** but also **ListAdapter** and **PagedListAdapter**.

Also supports:
* Sealed classes
* Enums
* Testing

![demo](/_media/polyadapter-demo.gif ':size=270x480')

## TLDR; How to import it?
If you just want to try the library before reading the documentation, you can download the package following the instructions below or clicking into the badge.

[ ![Download](https://api.bintray.com/packages/victor5171/xtras/polyadapter/images/download.svg?version=1.0.0) ](https://bintray.com/victor5171/xtras/polyadapter/1.0.0/link)

```groovy
repositories {
    jcenter()
    //Or
    maven {
		url  "https://dl.bintray.com/victor5171/xtras"
	}
}
```
And include the library:
```groovy
implementation "org.xtras.polyadapter:polyadapter:$LATEST_VERSION"
```

## TLDR; How to use it
You find more instructions on this documentation, but if you want to have a quick try, there's a quick set of instructions below.

Imagine you have the sealed class below, which represents types of fruits:

```kotlin
sealed class Fruit
data class Apple(val color: Int, val weight: Float)
data class Orange(val acidity: Int, val countryOfOrigin: String)
```

And the following View Holders:
```kotlin
class AppleViewHolder(parent: ViewGroup) :
    BindableViewHolder<Apple>(parent, R.layout.list_item_apple) {
    
    override fun bind(item: Apple) {
        //Configure your view here
        with(itemView) {
            itemView.setBackgroundColor(item.color)
            weightTextView.text = item.weight.toString()
        }
    }
}

class OrangeViewHolder(parent: ViewGroup) :
    BindableViewHolder<Orange>(parent, R.layout.list_item_apple) {
    
    override fun bind(item: Orange) {
        //Configure your view here
        with(itemView) {
            acidityTextView.text = item.acidity.toString()
            countryTextView.text = item.countryOfOrigin
        }
    }
```

To create an adapter you have 4 options:
* RecyclerView.Adapter
* ListAdapter
* PagedListAdapter: Not covered here to keep it simple, check more inside the documentation
* CustomAdapter: Not covered here to keep it simple, check more inside the documentation

Create a class called FruitAdapterBuilder, it can be a singleton

```kotlin
internal object FruitAdapterBuilder {
    @VisibleForTesting
    fun createPolyAdapterBuilder(): 
        PolyAdapterBuilder<Fruit, ClassViewTypeRetriever<Fruit>> {
        
        val classViewTypeRetriever =
            ClassViewTypeRetriever<Fruit>()

        return PolyAdapterBuilder(classViewTypeRetriever)
            .registerDelegate(BindableViewHolderDelegate { AppleViewHolder(it) })
            .registerDelegate(BindableViewHolderDelegate { OrangeViewHolder(it) }))
    }

    fun buildForRecyclerView(items: List<Fruit>) =
        createPolyAdapterBuilder().buildForRecyclerView(items)

    fun buildForListAdapter() = 
        createPolyAdapterBuilder().buildForListAdapter(itemCallback)
}

private val itemCallback = object : DiffUtil.ItemCallback<Fruit>() {
    override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        return oldItem == newItem
    }
}
```

And that's it! Now you can just build the adapter you want to use and plug it inside a RecyclerView!

The library provides ways to make it easier to test also if you remembered to register delegates for all possible children of the sealed class, you can check more about that in the documentation.

Remember also to keep your sealed class and its children on Proguard because it uses reflection to know all the children for a given sealed class:
```
-keep class com.package.Fruit { *; }
```

## Demo app
You can clone the repository and try the demonstration app (app folder). There you can check examples for all types of adapter and use cases that this library supports. Including the new [MergeAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/MergeAdapter) from Google.