package nt.tshape.automation.reportmanager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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
    private static HTMLReporter htmlReporter;
    private static ExtentReports extentReports;
    private static ExtentSparkReporter extentSparkReporter;
    private static ExtentTest currentReportClass;
    private static ExtentTest currentReportNode;
    private static String reportOutPutFolderName;

    public HTMLReporter(String reportName, String reportOutPutFolderPrefixName) {
        reportOutPutFolderName = reportOutPutFolderPrefixName + getReportCurrentDateTime();
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(getCurrentRunningReportLocation() + reportOutPutFolderName + "\\" + reportName);
        extentReports.attachReporter(extentSparkReporter);
    }

    public static void initHTMLReporter(String reporterName, String reportOutPutFolderPrefixName) {
        htmlReporter = new HTMLReporter(reporterName, reportOutPutFolderPrefixName);
        htmlReporter.createReportCaptureScreenShotFolder();
    }

    public static HTMLReporter getHtmlReporter() {
        return htmlReporter;
    }

    public static ExtentTest getCurrentReportNode() {
        return currentReportNode;
    }

    private static String getCurrentRunningReportLocation() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        return currentWorkingDir.normalize() + "\\src\\test\\java\\ReportsOutput\\";
    }

    private static String getCaptureImageLocation() {
        return getCurrentRunningReportLocation() + reportOutPutFolderName + "\\CapturedImage\\";
    }

    public String takesScreenshot(WebDriver driver, String fileName) throws IOException {
        String imageName = fileName.replaceAll("[^a-zA-Z0-9]+", "");
        imageName = imageName + "_" + getReportCurrentDateTime();

        File captureLocation = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File saveLocation = new File(getCaptureImageLocation() + imageName + ".png");
        Files.copy(captureLocation.toPath(), saveLocation.toPath());

        return saveLocation.getAbsolutePath();
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

    private String getReportCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timeAndDate = dtf.format(now);
        timeAndDate = timeAndDate.replace("/", "_");
        timeAndDate = timeAndDate.replace(":", "_");
        timeAndDate = timeAndDate.replace(" ", "_");
        return timeAndDate;
    }

    private void createReportCaptureScreenShotFolder() {
        File directory = new File(getCaptureImageLocation());
        boolean isCreated = directory.mkdir();
        if (isCreated) {
            System.out.println("Report capture image output folder created successfully!");
        } else {
            createReportCaptureScreenShotFolder();
        }
    }

    public Markup markupTextWithColor(String text, ExtentColor color) {
        return MarkupHelper.createLabel(text, color);
    }

    public Markup markupJSONCodeBlock(String jsonObject) {
        return MarkupHelper.createCodeBlock(jsonObject, CodeLanguage.JSON);
    }

    public Markup markupRequestInfoTable(String requestType, String requestURL, String responseCode) {
        String[][] tableData = new String[2][3];
        tableData[0] = new String[]{"Request Type", "Request URL", "Response Code"};
        tableData[1] = new String[]{requestType, requestURL, responseCode};
        return MarkupHelper.createTable(tableData);
    }
}
