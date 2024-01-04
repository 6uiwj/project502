package org.choongang.admin.sersvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

//저장하는 서비스? (있으면 수정, 없으면 추가)
@Service
@RequiredArgsConstructor
public class ConfigSaveService {
    private final ConfigsRepository repository;

    public void save(String code, Object data) {
        Configs configs = repository.findById(code).orElseGet(Configs::new);
                //조회시 데이터가 있으면(영속성상태) 가져오고 없으면 비어있는 엔티티를 하나 만듦
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        try {
            String jsonString = om.writeValueAsString(data);
            configs.setData(jsonString);
            configs.setCode(code); //처음만들어지는 entity는 코드값이 없으므로 넣어줘야 한다.
            repository.saveAndFlush(configs);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
