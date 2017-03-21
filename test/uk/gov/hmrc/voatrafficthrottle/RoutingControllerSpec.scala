
package uk.gov.hmrc.voatrafficthrottle

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.http.Status
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{FakeHeaders, FakeRequest}
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.test.Helpers._


class RoutingControllerSpec extends UnitSpec with WithFakeApplication {

  implicit val system = ActorSystem("test-system")
  implicit val materializer = ActorMaterializer()

  "GET /cca/throttled" should {
    "return true when the rate is exceeded" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.cca.url" -> "/test-url")
        .configure("microservice.routes.cca.rate" -> 0)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val fakeRequest = new FakeRequest(
        "GET",
        "http://localhost:9000/voa-traffic-throttle/cca/throttled",
        FakeHeaders(Seq()),
        "RequestBody"
      )

      val result = Await.result(routingController.ccaRoute()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "true"
    }

    "return false when the rate is not exceeded" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.cca.url" -> "/test-url")
        .configure("microservice.routes.cca.rate" -> 101)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val fakeRequest = new FakeRequest(
        "GET",
        "http://localhost:9000/voa-traffic-throttle/cca/throttled",
        FakeHeaders(Seq()),
        "RequestBody"
      )

      val result = Await.result(routingController.ccaRoute()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "false"
    }

  }

  "GET /vmv/throttled" should {
    "return true when the rate is exceeded" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.vmv.url" -> "/test-url")
        .configure("microservice.routes.vmv.rate" -> 0)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val fakeRequest = new FakeRequest(
        "GET",
        "http://localhost:9000/voa-traffic-throttle/vmv/throttled",
        FakeHeaders(Seq()),
        "RequestBody"
      )

      val result = Await.result(routingController.vmvRoute()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "true"
    }

    "return false when the rate is not exceeded" in {
      val application = new GuiceApplicationBuilder()
        .configure("microservice.routes.vmv.url" -> "/test-url")
        .configure("microservice.routes.vmv.rate" -> 101)
        .build

      val routingController = application.injector.instanceOf[TrafficThrottleController]

      val fakeRequest = new FakeRequest(
        "GET",
        "http://localhost:9000/voa-traffic-throttle/vmv/throttled",
        FakeHeaders(Seq()),
        "RequestBody"
      )

      val result = Await.result(routingController.vmvRoute()(fakeRequest).run(), 60 seconds)

      contentAsString(result) shouldBe "false"
    }
  }

}
