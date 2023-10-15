import java.lang.NullPointerException
import java.util.Scanner

fun main() {
    val archiveList = mutableListOf<Archive>()
    val scanner = Scanner(System.`in`)
    val menu = Menu()
    var menuState = MenuState.ARCHIVE
    var selectedArchive = -1
    var selectedNote = -1
    while (true) {
        when (menuState) {
            MenuState.ARCHIVE -> menu.render(archiveList, "Архив")
            MenuState.NOTES -> menu.render(archiveList[selectedArchive].notes, "Заметку")
            MenuState.VIEW -> {
                if (selectedArchive >= 0 && selectedNote >= 0) {
                    println(archiveList[selectedArchive].notes[selectedNote])
                }
                scanner.nextLine()
                menuState = MenuState.NOTES
                continue
            }
            MenuState.CREATE -> {
                when {
                    selectedArchive < 0 -> {
                        println("Введите название архива")
                        val name = scanner.nextLine()
                        if (name.isEmpty()) {
                            println("Название архива не может быть пустым")
                            continue
                        }
                        archiveList.add(Archive(name, mutableListOf()))
                        menuState = MenuState.ARCHIVE
                        continue
                    }
                    selectedArchive >= 0 -> {
                        println("Введите имя заметки")
                        val name = scanner.nextLine()
                        if (name.isEmpty()) {
                            println("Название заметки не может быть пустым")
                            continue
                        }
                        println("Введите текс заметки")
                        val body = scanner.nextLine()
                        if (body.isEmpty()) {
                            println("Вы не ввели текст. Ну и ладно =(")
                        }
                        archiveList[selectedArchive].notes.add(Note(name, body))
                        menuState = MenuState.NOTES
                        continue
                    }
                }
            }
        }
        val temp = scanner.nextLine()
        var selectedIndex: Int = 99
                for (i in temp) {
                    if (!Character.isDigit(i) or temp.equals("")) {
                        println("Вы не то ввели. Введите число.")
                        continue
                    }
                    else selectedIndex = temp.toInt()
                }
        when {
            selectedIndex == 0 -> menuState = MenuState.CREATE
            selectedIndex < menu.menuItems.lastIndex -> {
                when (menuState) {
                    MenuState.ARCHIVE -> {
                        selectedArchive = selectedIndex - 1
                        menuState = MenuState.NOTES
                    }
                    MenuState.NOTES -> {
                        selectedNote = selectedIndex - 1
                        menuState = MenuState.VIEW
                    }
                    else -> { /* Ничего не делаем*/
                    }
                }
            }
            selectedIndex == menu.menuItems.lastIndex -> {
                when (menuState) {
                    MenuState.NOTES -> menuState = MenuState.ARCHIVE
                    else -> return
                }
            }
        }
    }
}