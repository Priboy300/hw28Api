package api.steps;
import api.config.ProjectConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.*;
import io.restassured.builder.RequestSpecBuilder;
import org.aeonbits.owner.ConfigFactory;


public class Specification {

    public static final ProjectConfig config = ConfigFactory.create(ProjectConfig.class);

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(int httpStatus){
        return new ResponseSpecBuilder()
                .expectStatusCode(httpStatus)
                .log(LogDetail.ALL)
                .build();
    }
}











