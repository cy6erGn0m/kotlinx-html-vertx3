package kotlinx.html.vertx

import io.vertx.core.buffer.*
import io.vertx.core.streams.*
import kotlinx.html.*
import kotlinx.html.stream.*
import org.w3c.dom.events.*

private class WriteStreamConsumer<T, out S : WriteStream<T>>(private val out: S, private val adapter: (CharSequence) -> T, prettyPrint: Boolean) : TagConsumer<S> {
    private val appendableAdapter = object : Appendable {
        override fun append(csq: CharSequence?): Appendable {
            if (csq == null) return append("null")

            out.write(adapter(csq))
            return this
        }

        override fun append(csq: CharSequence?, start: Int, end: Int): Appendable {
            if (csq == null) {
                return append("null", start, end)
            }

            out.write(adapter(csq.subSequence(start, end)))
            return this
        }

        override fun append(c: Char): Appendable {
            return append(c.toString())
        }
    }

    private val delegate = HTMLStreamBuilder(appendableAdapter, prettyPrint)

    override fun finalize(): S = out

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
        delegate.onTagAttributeChange(tag, attribute, value)
    }

    override fun onTagContent(content: CharSequence) {
        delegate.onTagContent(content)
    }

    override fun onTagContentEntity(entity: Entities) {
        delegate.onTagContentEntity(entity)
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        delegate.onTagContentUnsafe(block)
    }

    override fun onTagEnd(tag: Tag) {
        delegate.onTagEnd(tag)
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
        delegate.onTagEvent(tag, event, value)
    }

    override fun onTagStart(tag: Tag) {
        delegate.onTagStart(tag)
    }
}

fun WriteStream<Buffer>.writeHTML(prettyPrint: Boolean = false) = writeHTML(prettyPrint) { Buffer.buffer(it.toString()) }

fun <T, S : WriteStream<T>> S.writeHTML(prettyPrint: Boolean = false, transform: (CharSequence) -> T): TagConsumer<S> = WriteStreamConsumer(this, transform, prettyPrint)

