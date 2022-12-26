package nt.tshape.automation.selenium.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class HTMLTestReport {
    public static void main(String[] args) {
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("C:\\Users\\linhpham\\Documents\\Git_Repository\\HTMLReport\\src\\test\\java\\Reports\\report.html");
        extentHtmlReporter.config().setTheme(Theme.STANDARD);
        extentHtmlReporter.config().setDocumentTitle("Automation Report");
        extentHtmlReporter.config().setEncoding("utf-8");
        extentHtmlReporter.config().setReportName("Automation Report");

        ExtentReports report = new ExtentReports();
        report.attachReporter(extentHtmlReporter);
        report.setSystemInfo("Application","Automation Test");

        ExtentTest testClass = report.createTest("Test Class", "Description");
        ExtentTest testCase = testClass.createNode("Test Case", "Description2");

        testCase.pass("This is information that we want to log into testcase");
        testCase.fail("This is failed reason");

        report.flush();
    }
}
