package pl.manufacturer.object.example.entity;

import javax.persistence.*;

@Entity
public class PostOneToOneEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private PostDetailsOneToOneEntity details;
}
