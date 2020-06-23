package bbc.dpub.PassportOfflineCompleteness

import dispatch.Http
import javax.net.ssl.SSLContext
import org.asynchttpclient.Dsl
import org.asynchttpclient.netty.ssl.JsseSslEngineFactory

trait HttpConfig {
  protected def createExecutor: Http = Http.withConfiguration(_ => Dsl.config()
    .setSslEngineFactory(new JsseSslEngineFactory(SSLContext.getDefault)))
}