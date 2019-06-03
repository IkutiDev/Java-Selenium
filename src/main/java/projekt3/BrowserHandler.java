package projekt3;

class BrowserHandler {
    static void setBrowser(Browser browser){
        if(System.getProperty("os.name").contains("Windows")){
            setWindowsBrowser(browser);
        }
        else if(System.getProperty("os.name").contains("Mac os")){
            setMacBrowser(browser);
        }
        else{
            setLinuxBrowser(browser);
        }
    }
    private static void setWindowsBrowser(Browser browser){
        switch (browser)
        {
            case Chrome:
                System.setProperty("webdriver.chrome.driver", "resources/Windows/chromedriver.exe");
                break;

            case Firefox:
                System.setProperty("webdriver.gecko.driver", "resources/Windows/geckodriver.exe");
                break;

        }
    }
    private static void setMacBrowser(Browser browser){
        switch (browser)
        {
            case Chrome:
                System.setProperty("webdriver.chrome.driver", "resources/Mac/chromedriver");
                break;

            case Firefox:
                System.setProperty("webdriver.gecko.driver", "resources/Mac/geckodriver");
                break;
        }
    }
    private static void setLinuxBrowser(Browser browser){
        switch (browser)
        {
            case Chrome:
                System.setProperty("webdriver.chrome.driver", "resources/Linux/chromedriver");
                break;

            case Firefox:
                System.setProperty("webdriver.gecko.driver", "resources/Linux/geckodriver");
                break;
        }
    }

}
