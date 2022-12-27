package nt.tshape.automation.reportmanager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HTMLReporter {
    private static ExtentReports extentReports;
    private static ExtentSparkReporter extentSparkReporter;
    private static ExtentTest currentReportClass;
    private static ExtentTest currentReportNode;

    public HTMLReporter(String reportName) {
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(getCurrentRunningLocation() + "\\src\\test\\java\\ReportsOutput\\" + reportName);
        extentReports.attachReporter(extentSparkReporter);
    }

    public static ExtentTest getCurrentReportNode() {
        return currentReportNode;
    }

    private String getCurrentRunningLocation() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        return currentWorkingDir.normalize().toString();
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
