# kotlinx-html-vertx3

Adapter for [kotlinx.html](https://github.com/Kotlin/kotlinx.html) to work with vertx3 `WriteStream`

so you can write

```kotlin
fun myPage(out: HttpServerResponse) {
    out.writeHTML().html {
      body {
        h1 { +"Hello, vertx" }
      }
    }
}
```

