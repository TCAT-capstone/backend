package return_a.tcat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import return_a.tcat.dto.template.TemplateListResDto;
import return_a.tcat.service.TemplateService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/template")
    public ResponseEntity<TemplateListResDto> getTemplateList() {
        TemplateListResDto templateListResDto = templateService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(templateListResDto);
    }
}
