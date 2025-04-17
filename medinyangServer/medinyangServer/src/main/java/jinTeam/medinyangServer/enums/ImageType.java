package jinTeam.medinyangServer.enums;

public enum ImageType {
    PRESCRIPTION("처방전"),
    HEALTH_REPORT("건강검진결과지");

    private final String label;

    ImageType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
