package ru.networks

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.*
import java.io.File

fun Application.module() {
    routing {
        get("/") {
            val feedCountFile = File("../Cat-As-A-Service/data/userFeedCount.txt")
            val feedCountLines = feedCountFile.readLines()
            val petCountFile = File("../Cat-As-A-Service/data/userPetCount.txt")
            val petCountLines = petCountFile.readLines()
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {
                        +"MEPhI Networks 2023"
                    }
                    style {
                        +"div { width: 50% } section { display: flex; flex-direction: row; justify-content: center }"
                    }
                }
                body {
                    h1 {
                        +"MEPhI Networks 2023 External"
                    }
                    img {
                        src = "/var/www/html/kotik.jpg"
                        width = "640px"
                    }
                    h2 {
                        +"Allowed food: Fish, Meat, Milk, Bread, Carrot, Beer"
                    }
                    section {
                        div {
                            feedCountLines.map {
                                h2 {
                                    +"User ${it.split(" ")[0]} has fed the cat ${it.split(" ")[1]} ${getTimeValue(it.split(" ")[1].toInt())}"
                                }
                            }
                        }
                        div {
                            petCountLines.map {
                                h2 {
                                    +"User ${it.split(" ")[0]} has pet the cat ${it.split(" ")[1]} ${getTimeValue(it.split(" ")[1].toInt())}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getTimeValue(count: Int): String {
    return if (count == 1) "time" else "times"
}

fun main() {
    embeddedServer(Netty, port = 80, module = Application::module).start(wait = true)
}