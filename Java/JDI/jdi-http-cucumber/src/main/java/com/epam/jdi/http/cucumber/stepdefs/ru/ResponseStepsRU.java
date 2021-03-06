package com.epam.jdi.http.cucumber.stepdefs.ru;

import com.epam.http.response.ResponseStatusType;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Тогда;
import org.testng.Assert;

import static com.epam.jdi.http.cucumber.Utils.performanceResult;
import static com.epam.jdi.http.cucumber.Utils.restResponse;
import static java.lang.String.format;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class ResponseStepsRU {

    @Тогда("^Результаты не содержат ошибок$")
    public void performanceResultsDonTHaveAnyFails() {
        Assert.assertTrue(performanceResult.get().NoFails());
    }

    @И("^Я проверяю число запросов$")
    public void iCheckNumberOfRequests() {
        System.out.println("There were " + performanceResult.get().NumberOfRequests + " requests.");
    }

    @Тогда("^Код ответа равен (\\d+)$")
    public void responseStatusCodeEquals(int statusCode){
        assertEquals(restResponse.get().status.code, statusCode);
    }

    @И("^Тело ответа пустое")
    public void responseBodyIs() {
        assertEquals(restResponse.get().body, "");
    }

    @И("^Статус ответа это ([^\"]*)$")
    public void responseStatusTypeIs(String type) {
        Boolean typeMatches = false;
        for (ResponseStatusType responseStatusType : ResponseStatusType.values()) {
            if(type.equalsIgnoreCase(responseStatusType.name()))
                typeMatches = true;
        }
        Assert.assertTrue(typeMatches);
    }

    @И("^Параметр ответа \"([^\"]*)\" равен \"([^\"]*)\"$")
    public void responseParameterIsValue(String parameter, String value) {
        restResponse.get().assertThat().body(parameter, equalTo(value));
    }

    @Тогда("^Проверяю, содержат ли результаты ошибки$")
    public void iCheckIfPerformanceResultsContainAnyFails() {
        long numberOfFails = performanceResult.get().NumberOfFails;
        Assert.assertEquals(numberOfFails, 0,
                format("Всего было обнаружено %s ошибок", numberOfFails));
    }

    @И("^Срднее время ответа не превышает (\\d+) сек$")
    public void averageResponseTime(long seconds) {
        Assert.assertTrue(performanceResult.get().AverageResponseTime < seconds);
    }

    @И("^Параметр заголовка ответа \"([^\"]*)\" равен \"([^\"]*)\"$")
    public void responseHeaderIs(String parameter, String value) {
        restResponse.get().assertThat().header(parameter, value);
    }

    @И("^Параметр json ответа \"([^\"]*)\" равен \"([^\"]*)\"$")
    public void jsonResponseIs(String parameter, String value){
        restResponse.get().assertThat().body(parameter, equalTo(value));
    }

    @И("^Я печатаю ответ на запрос$")
    public void iPrintResponse() {
        restResponse.get();
    }
}
