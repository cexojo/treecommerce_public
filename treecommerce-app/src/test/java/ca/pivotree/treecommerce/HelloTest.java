package ca.pivotree.treecommerce;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * Created by cexojo on 30/03/2020
 */

//@QuarkusTest
public class HelloTest {

	//@Test
	public void testHelloEndpoint() {
		given()
			.when().get("/hello/howmany")
			.then()
			.statusCode(200);
	}

	//@Test
	public void testFire() {
		given()
			.when().delete("/hello/fire")
			.then()
			.statusCode(200).body(is("All fired"));
	}
}
