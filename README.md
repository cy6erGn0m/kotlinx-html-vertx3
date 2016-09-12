# kotlinx-html-vertx3

Adapter to work with vertx3 `WriteStream`

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

