package model;



public class Overview {
    private int overviewId;
    private String overviewName;
    private String overviewDescription;
    //private List<Project> overviewList;

    public Overview(int overviewId, String overviewName, String overviewDescription) {
        this.overviewId = overviewId;
        this.overviewName = overviewName;
        this.overviewDescription = overviewDescription;
    }

    public int getOverviewId() {
        return overviewId;
    }

    public void setOverviewId(int overviewId) {
        this.overviewId = overviewId;
    }

    public String getOverviewName() {
        return overviewName;
    }

    public void setOverviewName(String overviewName) {
        this.overviewName = overviewName;
    }

    public String getOverviewDescription() {
        return overviewDescription;
    }

    public void setOverviewDescription(String overviewDescription) {
        this.overviewDescription = overviewDescription;
    }

    // getter for List
}
