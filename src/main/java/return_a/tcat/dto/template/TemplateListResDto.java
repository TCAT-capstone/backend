package return_a.tcat.dto.template;

import lombok.Getter;

import java.util.List;

@Getter
public class TemplateListResDto {

    List<TemplateSimpleDto> templates;

    public TemplateListResDto(List<TemplateSimpleDto> templates) {
        this.templates = templates;

    }
}
