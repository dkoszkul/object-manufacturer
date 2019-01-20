package pl.manufacturer.object.example.entity.onetoone;

import javax.persistence.*;

@Entity
public class PostOneToOneEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private PostDetailsOneToOneEntity details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostDetailsOneToOneEntity getDetails() {
        return details;
    }

    public void setDetails(PostDetailsOneToOneEntity details) {
        this.details = details;
    }
}
