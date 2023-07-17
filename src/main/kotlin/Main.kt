import com.example.chapter2.Chapter2Game
import com.example.tutorial.core.FixedTimestepGame


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")


    val game = Chapter2Game()
    game.start()
}

//fun getResourceTest(name: String): String? {
//    return object {}.javaClass.getResource("chapter2.vert")?.readText()
//}