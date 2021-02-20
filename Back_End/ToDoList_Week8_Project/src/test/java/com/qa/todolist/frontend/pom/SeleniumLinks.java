package com.qa.todolist.frontend.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeleniumLinks {

	@FindBy(xpath = "/html/body/div/div[1]/div/div/div[1]/a/button")
	private WebElement bagModifLink;
	@FindBy(xpath = "/html/body/div/div[1]/div/div/div[2]/a/button")
	private WebElement itemModifLink;
	@FindBy(xpath = "//*[@id=\"navbarNav\"]/ul/li/a/div")
	private WebElement homeLink;

	public SeleniumLinks(WebDriver driver) {
		super();
	}

	public void navigateBagModif() {
		bagModifLink.click();
	}

	public void navigateItemModif() {
		itemModifLink.click();
	}

	public void navigateHome() {
		homeLink.click();
	}

}
