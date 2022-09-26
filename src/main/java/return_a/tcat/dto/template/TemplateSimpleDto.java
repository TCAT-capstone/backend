package return_a.tcat.dto.template;

import lombok.Getter;
import return_a.tcat.domain.Template;

@Getter
public class TemplateSimpleDto {

    private final Long templateId;
    private final String templateImg;

    public TemplateSimpleDto(Template template) {
        this.templateId = template.getId();
        this.templateImg = template.getTemplateImg();
    }
}
