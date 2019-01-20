package pl.manufacturer.object.example.entity.manytooneandonetomany;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }
}
