import com.example.thin_matrix.ThinMatrixGame


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    val game = ThinMatrixGame()
    game.start()

}