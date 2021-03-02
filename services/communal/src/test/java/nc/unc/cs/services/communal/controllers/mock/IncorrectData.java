package nc.unc.cs.services.communal.controllers.mock;

public interface IncorrectData {
    void blankData() throws Exception;
    void smallestSizeData() throws Exception;
    void oversizeData() throws Exception;
}
