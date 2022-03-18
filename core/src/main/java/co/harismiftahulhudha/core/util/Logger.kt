package co.harismiftahulhudha.core.util

class Logger(
    private val tag: String,
    private val isDebug: Boolean = true
) {
    fun log(msg: String) {
        if (!isDebug) {
            // print some message for release version
        }
        else {
            printLogd(tag, msg)
        }
    }

    companion object Factory {
        fun  buildDebug(tag: String): Logger {
            return Logger(
                tag = tag, isDebug = true
            )
        }
        fun  buildRelease(tag: String): Logger {
            return Logger(
                tag = tag, isDebug = false
            )
        }
    }
}

fun printLogd(tag: String, message: String) {
    println("$tag: $message")
}