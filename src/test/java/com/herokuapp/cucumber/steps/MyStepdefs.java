package com.herokuapp.cucumber.steps;

import com.herokuapp.herokuappinfo.CreateBookingSteps;
import com.herokuapp.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasKey;

public class MyStepdefs {
    public static String username = "admin";
    public static String password = "password123";
    public static String firstname = "Adam" + TestUtils.getRandomValue();
    public static String lastname = "Berry" + TestUtils.getRandomValue();
    public static Integer totalprice = 145;
    public static Boolean depositpaid = true;
    public static String additionalneeds = "Breakfast";
    public static int bookingID;
    public static String token;
    public static ValidatableResponse response;

    @Steps
    CreateBookingSteps createBookingSteps;

    @Given("^I am on homepage to authorize on the application$")
    public void iAmOnHomepageToAuthorizeOnTheApplication() {

    }

    @When("^I send a POST request using a valid payload to authorize application$")
    public void iSendAPOSTRequestUsingAValidPayloadToAuthorizeApplication() {
        response = createBookingSteps.authUser(username, password);
        response.log().all().statusCode(200);

    }

    @Then("^I get a valid response code (\\d+)$")
    public void iGetAValidResponseCode(int code) {
        response.assertThat().statusCode(code);
    }

    @And("^I verify if I have a token$")
    public void iVerifyIfIHaveAToken() {
        HashMap<?, ?> tokenMap = response.log().all().extract().path("");
        Assert.assertThat(tokenMap, hasKey("token"));
        System.out.println(token);
    }

    @Given("^I am on the homepage to create booking on the application$")
    public void iAmOnTheHomepageToCreateBookingOnTheApplication() {

    }

    @When("^I send a POST request using a valid payload to booking application$")
    public void iSendAPOSTRequestUsingAValidPayloadToBookingApplication() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-01-01");
        bookingsDatesData.put("checkout", "2019-01-01");
        response = createBookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingsDatesData, additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        HashMap<?, ?> bookingMap = response.log().all().extract().path("");
        Assert.assertThat(bookingMap, anything(firstname));
    }

    @When("^I send GET request to booking application$")
    public void iSendGETRequestToBookingApplication() {
        response = createBookingSteps.getBookingByID(bookingID);

    }

    @And("^I verify if booking created with correct details$")
    public void iVerifyIfBookingCreatedWithCorrectDetails() {
        response.log().all().extract().path("bookingid");
        HashMap<?, ?> bookingMap = response.log().all().extract().path("");
        Assert.assertThat(bookingMap, anything(firstname));
    }

    @When("^I send PUT request to booking application$")
    public void iSendPUTRequestToBookingApplication() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-01-01");
        bookingsDatesData.put("checkout", "2019-01-01");
        firstname = firstname+"_updated";
        lastname = lastname+"_updated";
        additionalneeds = "wheelchairs";
        response = createBookingSteps.updateBooking(bookingID,firstname, lastname,totalprice,depositpaid,bookingsDatesData,additionalneeds);

    }

    @And("^I verify if booking updated with correct details$")
    public void iVerifyIfBookingUpdatedWithCorrectDetails() {
        response.log().all().statusCode(200);
        HashMap<?,?>bookingMap= response.log().all().extract().path("");
        Assert.assertThat(bookingMap,anything(firstname));
    }

    @When("^I send DELETE request to booking application$")
    public void iSendDELETERequestToBookingApplication() {
        response = createBookingSteps.deleteBooking(bookingID);
        response.log().all().statusCode(201);

    }

    @And("^I verify if booking deleted from the application$")
    public void iVerifyIfBookingDeletedFromTheApplication() {
        response = createBookingSteps.getBookingByID(bookingID);
        response.log().all().statusCode(404);
    }
}
