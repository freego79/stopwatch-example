package cz.freego.tutorial.stopwatchexample.data.datasource

class GreetingsDataSource {

    private val greetings = listOf(
        "Hello!",
        "Hi!",
        "Hey!",
        "Greetings!",
        "Good morning!",
        "Good afternoon!",
        "Good evening!",
        "Good night!",
        "What's up!",
        "Howdy!",
        "Yo!",
        "What's going on!",
        "Hi there!",
        "Hey there!",
        "How’s it going!",
        "What's new!",
        "Sup!",
        "Salutations!",
        "Holla!",
        "Cheers!",
        "Peace!",
        "Welcome!",
        "How do you do!",
        "Good to see you!"
    )
    private var index = 0

    fun nextGreetings(): String {
        if (index >= greetings.size) index = 0
        return greetings[index++]
    }
}