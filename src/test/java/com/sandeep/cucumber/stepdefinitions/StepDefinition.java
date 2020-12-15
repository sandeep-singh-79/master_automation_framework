package com.sandeep.cucumber.stepdefinitions;

import com.sandeep.base.cucumber.BaseStepDefinition;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.pages.GoogleSearchPO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepDefinition extends BaseStepDefinition {

    @Given("Go to google page")
    public void given () {
        this.retrieve_landing_page_object();
        log.info("At Google Home Page \n\tPage Title: {}, \n\tPage URL: {}", driver.getTitle(), driver.getCurrentUrl());
    }

    @When("Enter search {string}")
    public void when (String search) {
        GoogleSearchPO gsPO = ghPO.performSearch(search);
        log.info("\nCount results for {} search is {}.\n", search, gsPO.searchResultCount());
    }

    public StepDefinition (TestContext context) {
        super(context);
    }
}
