import com.example.thin_matrix.ThinMatrixGame


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

//    Introduction.main(args)
//
    val game = ThinMatrixGame()
    game.start()

//    Test.main()

//    Boot().run()
}

//fun getResourceTest(name: String): String? {
//    return object {}.javaClass.getResource("chapter2.vert")?.readText()
//}