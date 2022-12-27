package nt.tshape.automation.reportmanager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HTMLReporter {
    private static ExtentReports extentReports;
    private static ExtentSparkReporter extentSparkReporter;
    private static ExtentTest currentReportClass;
    private static ExtentTest currentReportNode;

    public HTMLReporter(String reportName) {
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(getCurrentRunningLocation() + reportName);
        extentReports.attachReporter(extentSparkReporter);
    }

    public static ExtentTest getCurrentReportNode() {
        return currentReportNode;
    }

    private static String getCurrentRunningLocation() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        return currentWorkingDir.normalize() + "\\src\\test\\java\\ReportsOutput\\";
    }

    private static String getCaptureImageLocation() {
        return getCurrentRunningLocation() + "CapturedImage\\";
    }

    public static void takesScreenshot(WebDriver driver, String fileName) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timeAndDate = dtf.format(now);
        timeAndDate = timeAndDate.replace("/", "_");
        timeAndDate = timeAndDate.replace(":", "_");
        timeAndDate = timeAndDate.replace(" ", "_");

        String imageName = fileName + timeAndDate;
        File captureLocation = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File saveLocation = new File(getCaptureImageLocation() + imageName + ".png");
        Files.copy(captureLocation.toPath(), saveLocation.toPath());

        currentReportNode.addScreenCaptureFromPath(getCaptureImageLocation() + imageName + ".png");
    }

    public ExtentReports getExtentReports() {
        return extentReports;
    }

    public void createReportClass(String className, String classDescription) {
        currentReportClass = extentReports.createTest(cleanClassName(className), classDescription);
    }

    public void createReportNode(String className, String classDescription) {
        currentReportNode = currentReportClass.createNode(className, classDescription);
    }

    private String cleanClassName(String fullClassName) {
        int lastIndexOfDot = fullClassName.lastIndexOf(".");
        return fullClassName.substring(lastIndexOfDot + 1, fullClassName.length() - 1);
    }
}
