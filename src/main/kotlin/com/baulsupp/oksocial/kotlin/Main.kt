package com.baulsupp.oksocial.kotlin

import com.baulsupp.oksocial.CommandLineClient
import com.baulsupp.oksocial.util.LoggingUtil
import io.airlift.airline.Command
import io.airlift.airline.SingleCommand
import java.io.File
import java.util.logging.Logger
import kotlin.system.exitProcess

@Command(name = Main.NAME, description = "Kotlin scripting for APIs")
class Main : CommandLineClient() {
  private val logger = Logger.getLogger(Main::class.java.name)

  fun executeScript(args: MutableList<String>) {
    val engine = KotlinAppScriptFactory().scriptEngine

    if (args.size < 1) {
      System.err.println("usage: okscript file.kts arguments")
      exitProcess(-2)
    }

    val script = args[0]
    val arguments = args.drop(1)

    engine.put("arguments", arguments)

    engine.eval(File(script).readText())
  }

  fun run(): Int {
    if (sslDebug) {
      System.setProperty("javax.net.debug", "ssl,handshake")
    }

    LoggingUtil.configureLogging(debug, showHttp2Frames)

    if (showHelpIfRequested()) {
      return 0
    }

    executeScript(arguments)

    return 0
  }

  companion object {
    const val NAME = "okscript"

    private fun fromArgs(vararg args: String): Main {
      return SingleCommand.singleCommand(Main::class.java).parse(*args)
    }

    @JvmStatic
    fun main(vararg args: String) {
      val result = fromArgs(*args).run()
      System.exit(result)
    }
  }
}