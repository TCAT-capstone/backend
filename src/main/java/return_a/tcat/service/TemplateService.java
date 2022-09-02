package return_a.tcat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import return_a.tcat.domain.Template;
import return_a.tcat.dto.Template.TemplateListResDto;
import return_a.tcat.dto.Template.TemplateSimpleDto;
import return_a.tcat.repository.TemplateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateListResDto findAll() {
        List<Template> findAllTemplates = templateRepository.findAll();
        List<TemplateSimpleDto> collect = findAllTemplates.stream()
                .map(t -> new TemplateSimpleDto(t))
                .collect(Collectors.toList());

        TemplateListResDto templateListResDto = new TemplateListResDto(collect);
        return templateListResDto;
    }

}
