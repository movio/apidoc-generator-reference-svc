
package modules

import com.google.inject.AbstractModule
import com.typesafe.config.Config
import play.api.{ Configuration, Environment }

class ApplicationModule(
    environment: Environment,
    configuration: Configuration
) extends AbstractModule {

  def configure() = {
    import services._
    bind(classOf[Config]).toInstance(configuration.underlying)

    bind(classOf[HealthchecksService])
    bind(classOf[PeopleService])
  }
}