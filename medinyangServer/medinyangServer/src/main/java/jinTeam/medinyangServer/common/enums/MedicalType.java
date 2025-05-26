package jinTeam.medinyangServer.common.enums;

import lombok.Getter;

import java.util.regex.Pattern;

public enum MedicalType {
    PRESCRIPTION("처방전", Pattern.compile(".*처방전.*")),
    HEALTH_REPORT("건강검진결과지", Pattern.compile(".*(건강\\s*검진).*")),

    TYPE_NONE("error", Pattern.compile(""));

    @Getter
    private final String label;
    private final Pattern pattern;

    MedicalType(String label, Pattern pattern) {
        this.label = label;
        this.pattern = pattern;
    }

    public boolean matches(String input) {
        return pattern.matcher(input).matches();
    }

    public static MedicalType fromString(String input) {
        for (MedicalType type : values()) {
            if (type.matches(input.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 문서 유형입니다: " + input);
    }
}

