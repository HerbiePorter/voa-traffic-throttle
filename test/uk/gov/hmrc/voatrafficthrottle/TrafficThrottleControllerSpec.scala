
package uk.gov.hmrc.voatrafficthrottle

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

import scala.concurrent.Await
import scala.concurrent.duration._


class TrafficThrottleControllerSpec extends UnitSpec with WithFakeApplication {

  implicit val system = ActorSystem("test-system")
  implicit val materializer = ActorMaterializer()

  "GET /cca/registration/throttled" should {
    def fakeRequest = new FakeRequest(
      "GET",
      "http://localhost:9000/voa-traffic-throttle/cca/registration/throttled",
      FakeHeaders(Seq()),
      "RequestBody"
    )

    "return true when the throttle is set to 0%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.ccaRegistration.throttle" -> 0)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.ccaRegistrationThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "true"
    }

    "return false when the throttle is set to 100%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.ccaRegistration.throttle" -> 100)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.ccaRegistrationThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "false"
    }
  }

  "GET /cca/dashboard/throttled" should {

    val fakeRequest = new FakeRequest(
      "GET",
      "http://localhost:9000/voa-traffic-throttle/cca/dashboard/throttled",
      FakeHeaders(Seq()),
      "RequestBody"
    )

    "return true when the throttle is set to 0%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.ccaDashboard.throttle" -> 0)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.ccaDashboardThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "true"
    }

    "return false when the throttle is set to 100%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.ccaDashboard.throttle" -> 101)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.ccaDashboardThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "false"
    }
  }

  "GET /vmv/search/throttled" should {

    val fakeRequest = new FakeRequest(
      "GET",
      "http://localhost:9000/voa-traffic-throttle/vmv/search/throttled",
      FakeHeaders(Seq()),
      "RequestBody"
    )

    "return true when the throttle is set to 0%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.vmvSearch.throttle" -> 0)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.vmvSearchThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "true"
    }

    "return false when the throttle is set to 100%" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.vmvSearch.throttle" -> 101)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val result = Await.result(routingController.vmvSearchThrottled()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "false"
    }
  }

}
