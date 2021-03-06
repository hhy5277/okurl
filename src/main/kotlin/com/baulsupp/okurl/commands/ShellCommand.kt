package com.baulsupp.okurl.commands

import com.baulsupp.okurl.completion.ArgumentCompleter
import okhttp3.OkHttpClient
import okhttp3.Request

interface ShellCommand {
  fun name(): String

  fun buildRequests(client: OkHttpClient, arguments: List<String>): List<Request>

  fun authenticator(): String? {
    return null
  }

  fun handlesRequests(): Boolean {
    return false
  }

  fun completer(): ArgumentCompleter? = null
}
