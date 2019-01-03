package pl.manufacturer.object.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PostDetailsOneToOneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostOneToOneEntity post;
}
