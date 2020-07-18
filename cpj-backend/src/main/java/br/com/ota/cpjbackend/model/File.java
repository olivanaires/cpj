package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.helper.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "files")
@NoArgsConstructor
public class File extends BaseEntity {

    @JsonView(View.FileInfo.class)
    private String name;

    private String mimetype;

    @Lob
    private byte[] content;

    public File(String name, String mimetype, byte[] content){
        this.name = name;
        this.mimetype = mimetype;
        this.content = content;
    }

}
