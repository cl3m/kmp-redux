package kmp.redux

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}

sealed class Test {
    data class Test1(val test: Int): Test()
    object  Test2: Test()
}