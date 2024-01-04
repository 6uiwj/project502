package org.choongang.admin.sersvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

//조회
@Service
@RequiredArgsConstructor
public class ConfigInfoService {
    //조회할 때에는 원래 형태로 가져와야 하기 때문에 지네릭 형태가 필요함

    private final ConfigsRepository repository;

    public <T> Optional<T> get(String code, Class<T> clazz) {
        return get(code, clazz, null);
    }
    public <T> Optional<T> get(String code, TypeReference<T> typeReference) {
        return get(code, null, typeReference);
    }

    public <T> Optional<T> get(String code, Class<T> clazz, TypeReference<T> typeReference) {
        //오브젝트 매퍼로 가져올떄 클래스클래스가 필요"?
        Configs config = repository.findById(code).orElse(null);
        if(config == null || !StringUtils.hasText(config.getData())) {
            return Optional.ofNullable(null);
        }

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        //원래 형태로 변경
        String jsonString = config.getData();
        try {
            T data = null;
            if ( clazz == null) { //TypeReference로 처리
                data = om.readValue(jsonString, new TypeReference<T>() {});
            } else { //Class로 처리
                data = om.readValue(jsonString, clazz);
            }
            return Optional.ofNullable(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return Optional.ofNullable(null);
        }
    }

}
