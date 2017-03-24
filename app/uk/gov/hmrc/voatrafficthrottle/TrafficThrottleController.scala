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
class TrafficThrottleController @Inject()(@Named("routes.ccaRegistration.throttle") ccaRegistrationThrottle: Integer,
                                          @Named("routes.ccaDashboard.throttle") ccaDashboardThrottle: Integer,
                                          @Named("routes.vmvSearch.throttle") vmvSearchThrottle: Integer)
                                         (implicit exec: ExecutionContext)
  extends Controller {

  val log = Logger(this.getClass)

  def next = scala.util.Random.nextInt(100)

  val ccaRegistrationThrottled: Action[AnyContent] = Action {
    implicit request => Ok(throttled(ccaRegistrationThrottle).toString)
  }

  val ccaDashboardThrottled: Action[AnyContent] = Action {
    implicit request => Ok(throttled(ccaDashboardThrottle).toString)
  }

  val vmvSearchThrottled: Action[AnyContent] = Action {
    implicit request => Ok(throttled(vmvSearchThrottle).toString)
  }

  def throttled(throttle: Integer) = !(next <= throttle)

}


