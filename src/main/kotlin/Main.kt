package org.example
import java.io.File
import java.util.*

val filename = "Todo.txt"
val taskFile = File(filename)
val scanner = Scanner(System.`in`)

// Load data yang udh disimpan
val tasks = if(taskFile.exists()) {
    taskFile.readLines().toMutableList()
} else {
    mutableListOf()
}

fun main() {

    while(true) {
        println("\n=== TO-DO LIST ===")
        println("1. Lihat Semua Tugas")
        println("2. Tambah Tugas")
        println("3. Hapus Tugas")
        println("4. Keluar")
        print("Pilih opsi (1-4): ")

        try {
            when(scanner.nextInt()) {
                1 -> {
                    viewList(true)
                }
                2 -> {
                    println("\nMenambah To-Do")
                    print("Masukan tugas baru: ")
                    scanner.nextLine()
                    val newList = scanner.nextLine()
                    addList(newList)
                }
                3 -> {
                    viewList(false)
                    if(tasks.isEmpty()){
                        println("Tidak ada data yang bisa dihapus!")
                    } else {
                        println("Menghapus To-Do")
                        print("Masukan nomor yang ingin dihapus: ")
                        scanner.nextLine() // Menangkap newline dari input sebelumnya
                        val indexList = scanner.nextInt() - 1
                        val listText = tasks.get(indexList)
                        if(deleteList(indexList)) {
                            println("Tugas '$listText' berhasil dihapus!")
                            saveTasksToFile(tasks, filename)
                        } else {
                            println("Nomor tugas tidak valid.")
                        }
                    }
                }
                4 -> {
                    print("Akan keluar dari program")
                    Thread.sleep(1000)
                    print(". ")
                    Thread.sleep(1000)
                    print(". ")
                    Thread.sleep(1000)
                    print(". ")
                    Thread.sleep(1000)
                    println("\nSampai Jumpa!")
                    break
                }
                else -> {
                    println("Opsi tidak valid. Silakan pilih antara 1-4.")
                    println("Klik enter untuk lanjut.")
                    readLine()
                }
            }
        } catch (e: InputMismatchException){
            // output ketika inputan tidak valid
            println("Input tidak valid. Harap masukkan angka yang benar.")
            scanner.nextLine() // membersihkan input yang tidak valid
            print("Klik enter untuk lanjut.")
            readLine()
        }

    }

}

fun viewList(input: Boolean) {
    println("\nDaftar Tugas:")
    if (tasks.isEmpty()) {
        println("Belum ada tugas.")
    } else {
        tasks.forEachIndexed { index, task ->
            println("${index + 1 }. $task")
        }
    }
    if (input) {
        print("\nEnter untuk melanjutkan.")
        readLine()
    }
}

fun addList(newList: String)
{
    tasks.add(newList)
    println("Tugas '${newList}' berhasil ditambahkan!")
    saveTasksToFile(tasks, filename)
}

fun deleteList(numberIndex: Int): Boolean
{
    if (numberIndex in tasks.indices) {
        tasks.removeAt(numberIndex)
        return true
    } else {
        return false
    }

}

fun saveTasksToFile(tasks: List<String>, filename: String)
{
    File(filename).writeText(tasks.joinToString("\n"))
}