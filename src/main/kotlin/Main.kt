import com.example.chapter1.HelloLWJGL
import com.example.chapter1.Test
import com.example.chapter2.Chapter2Game
import com.example.test1.Boot
import com.example.tutorial.Introduction
import com.example.tutorial.core.FixedTimestepGame


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

//    Introduction.main(args)
//
//    val game = Chapter2Game()
//    game.start()

//    Test.main()

    Boot().run()
//    HelloLWJGL().run()
}

//fun getResourceTest(name: String): String? {
//    return object {}.javaClass.getResource("chapter2.vert")?.readText()
//}