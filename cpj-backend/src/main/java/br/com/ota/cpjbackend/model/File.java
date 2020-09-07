package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.helper.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class File extends BaseEntity {

    @JsonView(View.FileInfo.class)
    private String name;

    private String mimetype;

    @Lob
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;

    public File(String name, String mimetype, byte[] content){
        this.name = name;
        this.mimetype = mimetype;
        this.content = content;
    }

}
