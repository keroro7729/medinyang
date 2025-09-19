package jinTeam.medinyangServer.database.user.medicalData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jinTeam.medinyangServer.common.dto.InDataDto;
import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.user.userBasicData.UserBasicData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicalDataService {

    private final MedicalDataRepository repository;
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // 생성은 UserService에서 담당
    public String createDefaultJson(UserBasicData basicData) {
        Map<String, InDataDto> result = new HashMap<>();

        int currentYear = LocalDateTime.now().getYear();
        int birthYear = currentYear - basicData.getAge();
        result.put("birthYear", new InDataDto(String.valueOf(birthYear), null));
        result.put("gender", new InDataDto(basicData.getGender(), null));

        LocalDateTime now = LocalDateTime.now();
        result.put("weight", new InDataDto(String.valueOf(basicData.getWeight()), now));
        result.put("height", new InDataDto(String.valueOf(basicData.getHeight()), now));

        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
    }

    @Transactional(readOnly = true)
    public String getMedicalData(Long userId) {
        Map<String, List<InDataDto>> multiMap = getToMap(userId);
        Map<String, String> latestValues = new LinkedHashMap<>();

        for (Map.Entry<String, List<InDataDto>> entry : multiMap.entrySet()) {
            List<InDataDto> values = entry.getValue();
            if (values == null || values.isEmpty()) continue;
            else if(values.size() == 1) {
                latestValues.put(entry.getKey(), values.get(0).getValue());
                continue;
            }

            InDataDto latest = values.stream()
                    .filter(v -> v.getTimestamp() != null)
                    .max(Comparator.comparing(InDataDto::getTimestamp))
                    .orElse(values.get(0));

            latestValues.put(entry.getKey(), latest.getValue());
        }

        try {
            return mapper.writeValueAsString(latestValues);
        } catch (Exception e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
    }

    @Transactional(readOnly = true)
    public Map<String, List<InDataDto>> getToMap(Long userId) {
        MedicalData medicalData = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalData(" + userId + ") not exist"));
        String jsonString = medicalData.getDetailsJson();

        try {
            // 키: 값(InDataDto 또는 단일 값) 으로 읽음
            JsonNode rootNode = mapper.readTree(jsonString);
            Map<String, List<InDataDto>> result = new HashMap<>();

            for (Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> field = it.next();
                String key = field.getKey();
                JsonNode valueNode = field.getValue();

                // InDataDto 타입일 경우
                if (valueNode.has("value") && valueNode.has("timestamp")) {
                    InDataDto dto = mapper.treeToValue(valueNode, InDataDto.class);
                    result.put(key, List.of(dto));
                } else {
                    // 단순 값이면 timestamp 없이 저장
                    InDataDto dto = new InDataDto(valueNode.asText(), null);
                    result.put(key, List.of(dto));
                }
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패", e);
        }
    }

    @Transactional
    public void addData(Long userId, Map<String, String> newData) {
        LocalDateTime now = LocalDateTime.now();
        Map<String, List<InDataDto>> current = getToMap(userId);

        for (Map.Entry<String, String> entry : newData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            InDataDto newEntry = new InDataDto(value, now);

            // 기존 키가 있다면 리스트에 추가, 없으면 새 리스트 생성
            current.computeIfAbsent(key, k -> new ArrayList<>()).add(newEntry);
        }

        // JSON 직렬화
        String updatedJson;
        try {
            updatedJson = mapper.writeValueAsString(current);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }

        // DB 저장
        MedicalData medicalData = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalData(" + userId + ") not exist"));
        medicalData.setDetailsJson(updatedJson);
        repository.save(medicalData);
    }
}
