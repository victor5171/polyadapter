# About
Polyadapter is a library with the goal to make it easier to work with Polymorphic Adapters for RecyclerViews!

It's build to be lightweight and entirely written in **Kotlin**, you don't need to create a lot of classes and a lot of boilerplates but in the meantime it's also really flexible so you can plug it into your own adapter!

It supports the basic **RecyclerView.Adapter** but also **ListAdapter** and **PagedListAdapter**.

Also supports:
* Sealed classes
* Enums
* Testing

## TLDR; How to import it?
If you just want to try the library before reading the documentation, you can download the package following the instructions below or clicking into the badge.

[ ![Download](https://api.bintray.com/packages/victor5171/xtras/polyadapter/images/download.svg?version=1.0.0) ](https://bintray.com/victor5171/xtras/polyadapter/1.0.0/link)

```
repositories {
    jcenter()
    //Or
    maven { url  "https://dl.bintray.com/victor5171/xtras" }
}
```
And include the library:
```
implementation "org.xtras.polyadapter:polyadapter:$LATEST_VERSION"
```

## Documentation
You can check the complete documentation and guide [here](https://victor5171.github.io/polyadapter)