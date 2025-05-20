package jinTeam.medinyangServer.common.enums;

public enum MedicalType {
    PRESCRIPTION("처방전"),
    HEALTH_REPORT("건강검진결과지");

    private final String label;

    MedicalType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
