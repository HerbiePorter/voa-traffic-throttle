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

package uk.gov.hmrc.voatrafficthrottle

import javax.inject.{Inject, Singleton}

import com.google.inject.name.Named
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TrafficThrottleController @Inject()(@Named("routes.cca.url") ccaUrl: String,
                                          @Named("routes.cca.rate") ccaRate: Integer,
                                          @Named("routes.vmv.url") vmvUrl: String,
                                          @Named("routes.vmv.rate") vmvRate: Integer)(implicit exec: ExecutionContext)
  extends Controller {

  val log = Logger(this.getClass)

  def next = scala.util.Random.nextInt(100)

  val ccaRoute = Action.async { implicit request =>
    if(next < ccaRate){
      Future.successful(Ok("false"))
    } else {
      Future.successful(Ok("true"))
    }
  }

  val vmvRoute = Action.async { implicit request =>
    if(next < vmvRate){
      Future.successful(Ok("false"))
    } else {
      Future.successful(Ok("true"))
    }
  }
}


