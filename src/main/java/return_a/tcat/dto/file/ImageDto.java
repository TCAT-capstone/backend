package return_a.tcat.dto.file;

import lombok.Getter;


@Getter
public class ImageDto {

    private final String imageUrl;

    public ImageDto(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
