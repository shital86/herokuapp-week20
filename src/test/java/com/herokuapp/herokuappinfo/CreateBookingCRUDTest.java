package com.herokuapp.herokuappinfo;

import com.herokuapp.testbase.TestBase;
import com.herokuapp.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasKey;

public class CreateBookingCRUDTest extends TestBase {
    public static String username = "admin";
    public static String password = "password123";
    public static String firstname = "Jim"+ TestUtils.getRandomValue();
    public static String lastname = "Brown"+TestUtils.getRandomValue();
    public static Integer totalprice = 145;
    public static Boolean depositpaid = true;
    public static String additionalneeds = "Breakfast";
    public static int bookingID;
    public static String token;

    @Steps
    CreateBookingSteps createBookingSteps;

    @Title("This will Auth user")
    @Test
    public void test001() {
        ValidatableResponse response = createBookingSteps.authUser(username, password);
        response.log().all().statusCode(200);
        HashMap<?,?> tokenMap= response.log().all().extract().path("");
        Assert.assertThat(tokenMap,hasKey("token"));
        System.out.println(token);
    }

    @Title("This will Create a booking user")
    @Test
    public void test002() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-01-01");
        bookingsDatesData.put("checkout", "2019-01-01");
        ValidatableResponse response = createBookingSteps.createBooking(firstname, lastname,totalprice,depositpaid,bookingsDatesData,additionalneeds);
        response.log().all().statusCode(200);
        bookingID= response.log().all().extract().path("bookingid");
        HashMap<?,?>bookingMap= response.log().all().extract().path("");
        Assert.assertThat(bookingMap,anything(firstname));
        System.out.println(token);
    }

    @Title("This will Update a booking")
    @Test
    public void test003() {
        HashMap<Object, Object> bookingsDatesData = new HashMap<>();
        bookingsDatesData.put("checkin", "2018-01-01");
        bookingsDatesData.put("checkout", "2019-01-01");
        firstname = firstname+"_updated";
        lastname = lastname+"_updated";
        additionalneeds = "wheelchairs";
        ValidatableResponse response = createBookingSteps.updateBooking(bookingID,firstname, lastname,totalprice,depositpaid,bookingsDatesData,additionalneeds);
        response.log().all().statusCode(200);
        HashMap<?,?>bookingMap= response.log().all().extract().path("");
        Assert.assertThat(bookingMap,anything(firstname));
        System.out.println(token);
    }

    @Title("This will Deleted a user")
    @Test
    public void test004() {

        ValidatableResponse response = createBookingSteps.deleteBooking(bookingID);
        response.log().all().statusCode(201);
        ValidatableResponse response1 = createBookingSteps.getBookingByID(bookingID);
        response1.log().all().statusCode(404);

    }

}
