/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{Configuration, Environment}

class Module(environment: Environment,
             configuration: Configuration) extends AbstractModule {

  override def configure() = {
    bindConstant().annotatedWith(Names.named("routes.cca.url")).to(configuration.getString(s"microservice.routes.cca.url").get)
    bindConstant().annotatedWith(Names.named("routes.cca.rate")).to(configuration.getString(s"microservice.routes.cca.rate").get)

    bindConstant().annotatedWith(Names.named("routes.vmv.url")).to(configuration.getString(s"microservice.routes.vmv.url").get)
    bindConstant().annotatedWith(Names.named("routes.vmv.rate")).to(configuration.getString(s"microservice.routes.vmv.rate").get)
  }

}
