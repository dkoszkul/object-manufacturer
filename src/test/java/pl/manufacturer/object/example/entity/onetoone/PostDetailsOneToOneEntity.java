package pl.manufacturer.object.example.entity.onetoone;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public PostOneToOneEntity getPost() {
        return post;
    }

    public void setPost(PostOneToOneEntity post) {
        this.post = post;
    }
}
