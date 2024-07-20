package com.tymoshenko.eurowings.test;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class CommonTest {
    protected InputStream doScreenshot() {
        return new ByteArrayInputStream(Selenide.screenshot(OutputType.BYTES));
    }
}
