package ehu.isad;

public class CMSTaulaModel {

    private String url;
    private String cms;
    private String version;
    private String date;

    public CMSTaulaModel(String url, String cms, String version, String date) {
        this.url = url;
        this.cms = cms;
        this.version = version;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getCms() {
        return cms;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCms(String cms) {
        this.cms = cms;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CMSTaulaModel{" +
                "url='" + url + '\'' +
                ", cms='" + cms + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
