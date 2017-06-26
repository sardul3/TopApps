package p.topapps;

/**
 * Created by Sardul on 5/22/2017.
 */

public class AppInfo {
    private String appName;
    private String author;
    private String dateCreated;


    public String getAppName() {
        return appName;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString(){
        return getAppName()/*+"\n"+getAuthor()+"\n"+getDateCreated()*/;
    }
}
