package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String itemName;
    private UploadedFile attachFile;
    private List<UploadedFile> imageFiles;


    public Item(String itemName, UploadedFile attachFile, List<UploadedFile> imageFiles) {
        this.itemName = itemName;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }
}
