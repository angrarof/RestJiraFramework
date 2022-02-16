package settings;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class Utils {
    private static RequestSpecification req;
    private static SessionFilter sessionFilter;
    private static String baseURI = new PropertiesFile().getProperty("baseURI");

    public static RequestSpecification getRequestSpecification() throws FileNotFoundException {
        if (req==null){
            PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));
            req = new RequestSpecBuilder()
                    .setBaseUri(baseURI)
                    .addFilter(getFilter())
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();
        }
        return req;
    }

    public static SessionFilter getFilter(){
        sessionFilter = new SessionFilter();
        given().baseUri(baseURI).relaxedHTTPSValidation().header("Content-Type","application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin\" }").filter(sessionFilter)
        .when().post("/rest/auth/1/session")
        .then().assertThat().statusCode(200);
        return sessionFilter;
    }

    public static String getStringBody(Response response, String key){
        JsonPath js = new JsonPath(response.asString());
        return js.getString(key);
    }
}
