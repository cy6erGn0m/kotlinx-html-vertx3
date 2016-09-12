package kotlinx.html.vertx

import io.vertx.core.*
import io.vertx.core.streams.*
import kotlinx.html.*
import org.junit.*
import kotlin.test.*

class WriteStreamTest {
    val written = StringBuilder()

    val out: WriteStream<CharSequence> = object: WriteStream<CharSequence> {
        override fun end() = TODO()

        override fun drainHandler(handler: Handler<Void>?) = TODO()

        override fun writeQueueFull() = TODO()

        override fun exceptionHandler(handler: Handler<Throwable>?) = TODO()

        override fun setWriteQueueMaxSize(maxSize: Int) = TODO()

        override fun write(data: CharSequence?): WriteStream<CharSequence> {
            written.append(data!!)
            return this
        }
    }

    @Test
    fun testSmoke() {
        out.writeHTML { it }.html {
            body {
                p {
                    +"OK"
                }
            }
        }

        assertEquals("<html><body><p>OK</p></body></html>", written.toString())
    }
}
